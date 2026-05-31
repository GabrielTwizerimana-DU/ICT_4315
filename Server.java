
/**
 * File: Server.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking9.server;

import edu.du.ict4315.parking9.server.ClientHandler;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Thread-pool-driven server capable of hosting concurrent socket connection paths
 */
public class Server {

    private final ParkingOffice office;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private volatile boolean running = false;
    
    // Define structural thread pool scaling limits
    private static final int POOL_SIZE = 10; 

    public Server(ParkingOffice office) {
        this.office = office;
    }

    /**
     * Instantiates the fixed thread pool executor and launches the accept processing loop.
     * @param port
     */
    public void start(int port) {
        running = true;
        threadPool = Executors.newFixedThreadPool(POOL_SIZE);
        
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Multithreaded Server listening on port " + port + " with a thread pool of " + POOL_SIZE);

            while (running) {
                Socket clientSocket = serverSocket.accept();
                
                // Construct a separate handler execution unit
                ClientHandler handler = new ClientHandler(clientSocket, this.office);
                
                // Submit the task to the pool to run concurrently
                threadPool.submit(handler);
            }
        } catch (IOException e) {
            if (running) {
                System.err.println("Server exception encountered: " + e.getMessage());
            }
        }
    }

    /**
     * Coordinates a clean termination sequence for active sockets and background thread jobs.
     */
    public void stop() {
        running = false;
        if (threadPool != null) {
            threadPool.shutdown(); // Stop accepting new tasks
            try {
                if (!threadPool.awaitTermination(3, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow(); // Force kill remaining tasks
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
            }
        }
        
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                System.out.println("Server socket terminated cleanly.");
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        ParkingObserver customObserver = event -> System.out.println("[Event Stream] " + event);
        ParkingOffice productionOffice = new ParkingOffice("Main_Office_01", customObserver); 
        new Server(productionOffice).start(12345);
    }
}