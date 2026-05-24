
/**
 * File: ServerClient.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking8.clients;

import com.google.gson.Gson;
import edu.du.ict4315.parking8.shared.ParkingRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Network client capable of dispatching command-driven ParkingRequests
 * over TCP sockets and waiting for standard execution responses
 */
public class ServerClient {

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 12345;

    /**
     * Standard production request transmitter targeting the default server port.
     * * @param request The envelope containing the transaction token and command payload.
     * @param request
     */
    public static void sendRequest(ParkingRequest request) {
        sendRequest(request, DEFAULT_PORT); // Routes cleanly to production environment
    }

    /**
     * Overloaded request transmitter allowing dynamic port redirection 
     * for hermetic out-of-band unit testing.
     * * @param request The envelope containing the transaction token and command payload.
     * @param request
     * @param port    The specific network port destination to hit.
     */
    public static void sendRequest(ParkingRequest request, int port) {
        try (
            Socket socket = new Socket(DEFAULT_HOST, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            // 1. Marshall the entire command envelope down to a clean JSON text stream
            String jsonRequest = new Gson().toJson(request);
            
            // 2. Transmit the packet over the socket connection
            out.println(jsonRequest);
            
            // 3. Block and read the incoming execution status response from the server loop
            String rawResponse = in.readLine();
            if (rawResponse != null) {
                System.out.println("Server Response Received: " + rawResponse);
            } else {
                System.err.println("Warning: Server closed the connection stream without responding.");
            }
            
        } catch (Exception e) {
            System.err.println("Client communication error on port " + port + ": " + e.getMessage());
        }
    }
}