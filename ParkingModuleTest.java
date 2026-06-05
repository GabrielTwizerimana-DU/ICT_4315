
/**
 * File: ParkingResponseTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking10.dependencyinjection.test;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking10.dependencyinjection.ParkingModule;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test suite verifying framework binding configurations, object scopes, 
 * and singleton preservation rules within the Google Guice injector module
 */
public class ParkingModuleTest {

    private Injector injector;

    @BeforeEach
    public void setUp() {
        // Initialize the actual production module configuration kernel before each test
        injector = Guice.createInjector(new ParkingModule());
    }

    @Test
    public void testObserverBindingResolution() {
        // Act: Request the framework to resolve our core interface contract
        ParkingObserver observerInstance = injector.getInstance(ParkingObserver.class);

        // Assert: Ensure the concrete instance exists and is correctly bound
        assertNotNull(observerInstance, "The Guice injector must successfully resolve and bind a ParkingObserver instance.");
    }

    @Test
    public void testParkingOfficeSingletonScope() {
        // Act: Request two distinct instances of ParkingOffice from the injector kernel
        ParkingOffice officeInstance1 = injector.getInstance(ParkingOffice.class);
        ParkingOffice officeInstance2 = injector.getInstance(ParkingOffice.class);

        // Assert: Verify both references point to the exact same memory address (Singleton pattern)
        assertNotNull(officeInstance1, "First resolved office reference should not be null.");
        assertNotNull(officeInstance2, "Second resolved office reference should not be null.");
        assertSame(officeInstance1, officeInstance2, "ParkingOffice must be scoped as a strict @Singleton across the application context.");
    }

    @Test
    public void testThreadPoolSingletonScopeAndAvailability() {
        // Act: Resolve the underlying concurrency executor resource
        ExecutorService poolInstance1 = injector.getInstance(ExecutorService.class);
        ExecutorService poolInstance2 = injector.getInstance(ExecutorService.class);

        // Assert: Ensure it's managed as a shared singleton so we aren't leaking thread resources
        assertNotNull(poolInstance1);
        assertSame(poolInstance1, poolInstance2, "ExecutorService thread pool must be a shared @Singleton resource.");
        assertFalse(poolInstance1.isShutdown(), "The injected thread pool must arrive in an active, usable state.");
    }

    @Test
    public void testGsonUtilityBinding() {
        // Act: Resolve the JSON parsing engine utility
        Gson gsonInstance1 = injector.getInstance(Gson.class);
        Gson gsonInstance2 = injector.getInstance(Gson.class);

        // Assert: Verify the utility instance is successfully managed by the framework
        assertNotNull(gsonInstance1);
        assertSame(gsonInstance1, gsonInstance2, "Gson instance should be cached and reused as a Singleton provider.");
    }
}