
/**
 * File: Server.java 
 * Author: Gabriel Twizerimana 
 */

package edu.du.ict4315.parking8.server;

import edu.du.ict4315.parking8.shared.RegisterCarCommand;
import edu.du.ict4315.parking8.shared.RegisterCustomerCommand;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Core network execution server
 * that receives polymorphic requests, extracts serialized command structures,
 * injects local execution contexts, and triggers core business operations
 */
public class Server {

    private final ParkingOffice office; // Local stateful context manager
    private final Gson gson;
    private ServerSocket serverSocket;
    private volatile boolean running = false;

    public Server(ParkingOffice office) {
        this.office = office;
        this.gson = new Gson();
    }

    /**
     * Spawns the socket listener accept-loop on the designated TCP port.
     * @param port
     */
    public void start(int port) {
        running = true;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server actively running and listening on port " + port + "...");

            while (running) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            if (running) {
                System.err.println("Server network pipeline exception: " + e.getMessage());
            }
        }
    }

    /**
     * Shuts down the active socket listener thread cleanly.
     */
    public void stop() {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                System.out.println("Server listener socket shut down successfully.");
            } catch (IOException e) {
                System.err.println("Error encountered while terminating server socket: " + e.getMessage());
            }
        }
    }

    /**
     * Evaluates text stream transmissions, injects data dependencies, and
     * processes logic.
     */
    private void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String rawJsonRequest = in.readLine();
            if (rawJsonRequest == null) {
                return;
            }

            // 1. Unpack envelope to inspect the instruction routing token
            JsonObject rawObject = JsonParser.parseString(rawJsonRequest).getAsJsonObject();
            String commandToken = rawObject.has("command") ? rawObject.get("command").getAsString() : "";

            String executionOutcome;

            // 2. Map payload dynamically into target executable commands
            if ("REGISTER_CUSTOMER".equalsIgnoreCase(commandToken)) {
                RegisterCustomerCommand customerCmd = gson.fromJson(rawObject.get("payload"), RegisterCustomerCommand.class);

                // RE-HYDRATION LAYER: Safely bridge the transient field barrier
                customerCmd.setOffice(this.office);
                executionOutcome = customerCmd.execute(null);

            } else if ("REGISTER_CAR".equalsIgnoreCase(commandToken)) {
                RegisterCarCommand carCmd = gson.fromJson(rawObject.get("payload"), RegisterCarCommand.class);

                // RE-HYDRATION LAYER: Safely bridge the transient field barrier
                carCmd.setOffice(this.office);
                executionOutcome = carCmd.execute(null);

            } else {
                executionOutcome = "Error: Unrecognized or malformed command request routing token.";
            }

            // 3. Construct a standard response transaction token and return it across the wire
            ParkingResponse response = new ParkingResponse(executionOutcome.contains("Success") ? 200 : 400, executionOutcome);
            out.println(gson.toJson(response));

        } catch (Exception e) {
            System.err.println("Exception intercepted while handling request stream: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // 1. Create a simple inline observer to satisfy the constructor dependency
        ParkingObserver basicObserver = event -> {
            System.out.println("Notification received: " + event);
        };

        // 2. Pass both the required String ID and the Observer instance
        ParkingOffice productionOffice = new ParkingOffice("Main_Office_01", basicObserver);

        // 3. Boot the server
        new Server(productionOffice).start(12345);
    }
}
