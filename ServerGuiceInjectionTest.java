
/**
 * File: ServerGuiceInjectionTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking10.dependencyinjection.test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking10.dependencyinjection.Server;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class ServerGuiceInjectionTest {

    private Injector testInjector;

    @BeforeEach
    public void setUp() {
        // Define a customized test module configuration block
        testInjector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                // Inject a quiet mock spy observer that ignores standard console logs
                bind(ParkingObserver.class).toInstance(event -> {});
                bind(ParkingOffice.class).toInstance(new ParkingOffice("TEST_BOUNDS", event -> {}));
                bind(ExecutorService.class).toInstance(Executors.newSingleThreadExecutor());
            }
        });
    }

    @Test
    public void testFrameworkWiringIntegrity() {
        // Request an instance of Server from Guice to confirm the dependency graph is valid
        Server serverInstance = testInjector.getInstance(Server.class);
        
        assertNotNull(serverInstance, "Guice should resolve the constructor dependency tree and instantiate the Server.");
    }
}