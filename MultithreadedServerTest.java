
/**
 * File: MultithreadedServerTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking9.server.test;

import edu.du.ict4315.parking8.shared.RegisterCustomerCommand;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;
import edu.du.ict4315.parking8.shared.ParkingRequest;
import com.google.gson.Gson;
import edu.du.ict4315.parking9.server.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class MultithreadedServerTest {

    private static final int PORT = 12347;
    private Server server;
    private Thread serverThread;
    private ParkingOffice office;

    @BeforeEach
    public void startServer() throws Exception {
        ParkingObserver spy = event -> {};
        office = new ParkingOffice("TEST_HUB", spy);
        server = new Server(office);
        
        serverThread = new Thread(() -> server.start(PORT));
        serverThread.start();
        Thread.sleep(200); // Allow server to boot up safely
    }

    @AfterEach
    public void stopServer() throws Exception {
        server.stop();
        serverThread.join(1000);
    }

    @Test
    public void testSimultaneousClientFlood() throws Exception {
        int concurrentClients = 5;
        ExecutorService clientSimPool = Executors.newFixedThreadPool(concurrentClients);
        CountDownLatch signalTrigger = new CountDownLatch(1);
        CountDownLatch executionTracker = new CountDownLatch(concurrentClients);

        for (int i = 0; i < concurrentClients; i++) {
            final int idSuffix = i;
            clientSimPool.submit(() -> {
                try {
                    // Block execution until all threads are spun up and ready
                    signalTrigger.await();

                    // Assemble distinct payload parameters
                    RegisterCustomerCommand cmd = new RegisterCustomerCommand(
                        null, "ID-" + idSuffix, "ClientName-" + idSuffix, "555-000" + idSuffix
                    );
                    ParkingRequest req = new ParkingRequest("REGISTER_CUSTOMER", cmd);

                    // Execute communication loop
                    try (Socket socket = new Socket("localhost", PORT);
                         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        
                        out.println(new Gson().toJson(req));
                        String response = in.readLine();
                        assertNotNull(response);
                        assertTrue(response.contains("200"));
                    }

                } catch (IOException | InterruptedException e) {
                    fail("Client processing task dropped with exception: " + e.getMessage());
                } finally {
                    executionTracker.countDown();
                }
            });
        }

        // Release the latch to fire all requests simultaneously
        long startTime = System.nanoTime();
        signalTrigger.countDown();

        // Block and wait up to 5 seconds for all threads to finish
        boolean safeExit = executionTracker.await(5, TimeUnit.SECONDS);
        long endTime = System.nanoTime();

        assertTrue(safeExit, "Clients timed out; the server might be blocking requests sequentially.");
        
        double durationMs = (endTime - startTime) / 1_000_000.0;
        System.out.printf("[Benchmark Summary] Successfully ran %d concurrent requests in %.2f ms\n", 
                concurrentClients, durationMs);

        // Verify all customers were correctly registered inside the office map
        for (int i = 0; i < concurrentClients; i++) {
            assertNotNull(office.getCustomer("ID-" + i), "Data record lost or overwritten due to a race condition!");
        }

        clientSimPool.shutdown();
    }
}