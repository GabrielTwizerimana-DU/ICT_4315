
/**
 * File: ServerClientTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking8.clients.test;

import edu.du.ict4315.parking8.shared.RegisterCustomerCommand;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;
import edu.du.ict4315.parking8.shared.ParkingRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import com.google.gson.Gson;
import edu.du.ict4315.parking8.clients.ServerClient;

import static org.junit.jupiter.api.Assertions.*;

public class ServerClientTest {

    private static final int TEST_PORT = 12346; // Isolated test port out-of-band
    private ServerSocket fakeServerSocket;
    private Thread serverThread;
    private volatile String receivedJsonFromClient;
    private final Gson gson = new Gson();

    @BeforeEach
    public void setUp() throws Exception {
        fakeServerSocket = new ServerSocket(TEST_PORT);
        receivedJsonFromClient = null;

        // Spin up a simple programmatic background socket to mimic the server host loop
        serverThread = new Thread(() -> {
            try (Socket clientSocket = fakeServerSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                // Capture the marshaled Command string sent over the wire
                receivedJsonFromClient = in.readLine();

                // Reply back with a uniform mock success response envelope
                String fakeResponse = "{\"statusCode\":200,\"message\":\"Success: Customer Gabriel (CUST-99) has been registered.\"}";
                out.println(fakeResponse);

            } catch (Exception e) {
                // Handle socket closure gracefully during tearDown lifecycle step
            }
        });
        serverThread.start();
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (fakeServerSocket != null && !fakeServerSocket.isClosed()) {
            fakeServerSocket.close();
        }
        if (serverThread != null) {
            serverThread.join(1000); // Give background worker 1 second max to terminate cleanly
        }
    }

    @Test
    public void testCommandEnvelopeTransmissionLifecycle() throws Exception {
        // 1. Arrange: Instantiate a fast inline observer spy to satisfy constructor rules
        ParkingObserver spyObserver = event -> { /* no-op test stub */ };
        ParkingOffice fakeClientSideOffice = new ParkingOffice("TEST_OFFICE", spyObserver);

        // Assemble the command payload (office context is transient and won't cross the wire)
        RegisterCustomerCommand commandPayload = new RegisterCustomerCommand(
            fakeClientSideOffice, "CUST-99", "Gabriel", "555-0199"
        );
        
        ParkingRequest requestEnvelope = new ParkingRequest("REGISTER_CUSTOMER", commandPayload);
        String expectedPayloadJson = gson.toJson(requestEnvelope);

        // 2. Act: Dispatch transmission targeting our isolated test socket background thread
        ServerClient.sendRequest(requestEnvelope, TEST_PORT);

        // Give the background thread an instant to completely flush the stream buffer
        Thread.sleep(100);

        // 3. Assert: Verify the stream data caught by our test server matches our object format
        assertNotNull(receivedJsonFromClient, "The server should have captured a network stream transmission.");
        assertEquals(expectedPayloadJson, receivedJsonFromClient, "The output JSON layout did not match the expected format.");
        
        // Assert the internal structural integrity of the command serialization loop
        assertTrue(receivedJsonFromClient.contains("\"command\":\"REGISTER_CUSTOMER\""));
        assertTrue(receivedJsonFromClient.contains("\"id\":\"CUST-99\""));
        assertTrue(receivedJsonFromClient.contains("\"name\":\"Gabriel\""));
        
        // CRITICAL CHECK: Verify the 'office' data object structure was omitted entirely by the transient modifier
        assertFalse(receivedJsonFromClient.contains("\"office\""), "The stateful server office context must never leak over sockets.");
    }
}