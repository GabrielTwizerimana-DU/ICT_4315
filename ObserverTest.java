/**
 * File: ObserverTest.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking5.observer.pattern.test;

import edu.du.ict4315.parking1.controller.commands.ParkingLot;
import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.controller.commands.ParkingService;
import edu.du.ict4315.parking1.controller.commands.ParkingTransaction;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.management.layers.TransactionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the Observer pattern integration within the ParkingService. Focuses
 * on the automated registration and event delivery pipeline.
 */
public class ObserverTest {

    private ParkingService service;
    private TransactionManager manager;
    private ParkingLot lot;
    private ParkingPermit permit;

    @BeforeEach
    public void setUp() {
        // Use real objects to verify the handshake (No Mocks)
        manager = new TransactionManager();
        service = new ParkingService(manager);

        lot = new ParkingLot("OBS-1", "Observer Garage", 100);

        Customer customer = new Customer("C-1", "Observer Test", "123 Event Ave");
        Car car = new Car("TEST-OBS", CarType.SUV, customer);
        permit = new ParkingPermit("P-OBS", car);
    }

    @Test
    public void testServiceRegistersObserverAutomatically() {
        // Act: Registering a lot through the service should link it to the manager
        service.registerLot(lot);

        // Act: Trigger an entry directly on the lot
        lot.enter(permit);

        // Assert: If the service linked them correctly, the manager should have a transaction
        ParkingTransaction tx = manager.getActiveTransaction("P-OBS");
        assertNotNull(tx, "TransactionManager should have received the event via the service registration.");
    }

    @Test
    public void testEventFlowThroughService() {
        service.registerLot(lot);

        // Act: Use service methods to process entry/exit
        service.processEntry("OBS-1", permit);
        assertNotNull(manager.getActiveTransaction("P-OBS"));

        service.processExit("OBS-1", permit);

        // Assert: Verify full observer loop completed
        assertNull(manager.getActiveTransaction("P-OBS"));
        assertEquals(1, manager.getCompletedTransactions().size());
    }

    @Test
    public void testDetachObserver() {
        service.registerLot(lot);

        // Manual removal of the observer from the subject
        lot.removeObserver(manager);

        service.processEntry("OBS-1", permit);

        // Assert: No transaction should be created because the observer was detached
        assertNull(manager.getActiveTransaction("P-OBS"), "Manager should not receive events after removal.");
    }
}
