
/**
 * File: ClientHandler.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking9.server;

import edu.du.ict4315.parking8.shared.RegisterCarCommand;
import edu.du.ict4315.parking8.shared.RegisterCustomerCommand;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.du.ict4315.parking8.server.ParkingResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Decoupled client worker task capable of tracking latency and 
 * executing polymorphic request commands concurrently
 */
public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final ParkingOffice office;
    private final Gson gson;

    public ClientHandler(Socket clientSocket, ParkingOffice office) {
        this.clientSocket = clientSocket;
        this.office = office;
        this.gson = new Gson();
    }

    @Override
    public void run() {
        // Start benchmark timer
        long startTime = System.nanoTime();
        
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String rawJsonRequest = in.readLine();
            if (rawJsonRequest == null) return;

            JsonObject rawObject = JsonParser.parseString(rawJsonRequest).getAsJsonObject();
            String commandToken = rawObject.has("command") ? rawObject.get("command").getAsString() : "";
            String executionOutcome;

            // Route command text to concrete class mapping
            if ("REGISTER_CUSTOMER".equalsIgnoreCase(commandToken)) {
                RegisterCustomerCommand customerCmd = gson.fromJson(rawObject.get("payload"), RegisterCustomerCommand.class);
                customerCmd.setOffice(this.office);
                executionOutcome = customerCmd.execute(null);

            } else if ("REGISTER_CAR".equalsIgnoreCase(commandToken)) {
                RegisterCarCommand carCmd = gson.fromJson(rawObject.get("payload"), RegisterCarCommand.class);
                carCmd.setOffice(this.office);
                executionOutcome = carCmd.execute(null);

            } else {
                executionOutcome = "Error: Unrecognized command token.";
            }

            // Build response
            ParkingResponse response = new ParkingResponse(executionOutcome.contains("Success") ? 200 : 400, executionOutcome);
            out.println(gson.toJson(response));

        } catch (Exception e) {
            System.err.println("Exception processing client task: " + e.getMessage());
        } finally {
            // Calculate total execution latency
            long durationNs = System.nanoTime() - startTime;
            double durationMs = durationNs / 1_000_000.0;
            System.out.printf("[Diagnostic] Client %s fully processed in %.3f ms\n", 
                    clientSocket.getRemoteSocketAddress(), durationMs);
            
            // Clean up socket resource boundary
            try {
                if (!clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException ex) {
                System.err.println("Failed closing socket connection: " + ex.getMessage());
            }
        }
    }
}