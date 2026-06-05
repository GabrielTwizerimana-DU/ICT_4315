
/**
 * File: Server.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking10.dependencyinjection;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking9.server.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Refactor your server entry-point to let Guice manage thread pools, object lifecycles
 * and connection handling
 */
public class Server {

    private final ParkingOffice office;
    private final ExecutorService threadPool;
    private ServerSocket serverSocket;
    private volatile boolean running = false;

    // The @Inject annotation tells Guice to resolve and provide these parameters automatically
    @Inject
    public Server(ParkingOffice office, ExecutorService threadPool) {
        this.office = office;
        this.threadPool = threadPool;
    }

    public void start(int port) {
        running = true;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Guice-Injected Server listening concurrently on port " + port);

            while (running) {
                Socket clientSocket = serverSocket.accept();
                // Pass the injected office context directly into the handler task
                threadPool.submit(new ClientHandler(clientSocket, this.office));
            }
        } catch (IOException e) {
            if (running) System.err.println("Server exception: " + e.getMessage());
        }
    }

    public void stop() {
        running = false;
        if (threadPool != null) {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(2, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
            }
        }
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Boot up the Guice kernel framework configuration module
        Injector injector = Guice.createInjector(new ParkingModule());
        
        // Let Guice instantiate and wire up the Server with all its dependencies
        Server server = injector.getInstance(Server.class);
        server.start(12345);
    }
}