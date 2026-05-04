/**
 * File: ParkingObserverTest.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking5.observer.pattern.test;

import edu.du.ict4315.parking1.controller.commands.ParkingLot;
import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.controller.commands.ParkingTransaction;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.management.layers.TransactionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the core Observer Pattern handshake between ParkingLot and
 * TransactionManager. Verifies that events are broadcast and received correctly
 * without using mocks.
 */
public class ParkingObserverTest {

    private ParkingLot lot;
    private TransactionManager manager;
    private ParkingPermit permit;

    @BeforeEach
    public void setUp() {
        // Initialize the Subject (Lot) and the Observer (Manager)
        lot = new ParkingLot("LOT-OBS", "Observer Test Lot", 10);
        manager = new TransactionManager();

        // Setup real data payload
        Customer customer = new Customer("C-OBS", "Observer User", "101 Signal St");
        Car car = new Car("SYNC-1", CarType.SUV, customer);
        permit = new ParkingPermit("P-SYNC", car);
    }

    @Test
    public void testObserverRegistrationAndNotification() {
        // Act: Register the observer and trigger an event
        lot.addObserver(manager);
        lot.enter(permit);

        // Assert: The manager (Observer) should have reacted to the event 
        // by creating a real transaction in its internal map.
        ParkingTransaction tx = manager.getActiveTransaction("P-SYNC");
        assertNotNull(tx, "The Observer should have created a transaction upon notification.");
        assertEquals(lot, tx.getLot(), "The notification should contain the correct Subject reference.");
    }

    @Test
    public void testObserverRemoval() {
        // Setup: Add and then remove the observer
        lot.addObserver(manager);
        lot.removeObserver(manager);

        // Act: Trigger an event
        lot.enter(permit);

        // Assert: Manager should not have any record because it was detached
        assertNull(manager.getActiveTransaction("P-SYNC"), "The Manager should not receive events after being removed.");
    }

    @Test
    public void testFullLifecycleNotification() {
        lot.addObserver(manager);

        // 1. Notify Entry
        lot.enter(permit);
        assertNotNull(manager.getActiveTransaction("P-SYNC"));

        // 2. Notify Exit
        lot.exit(permit);

        // Assert: The Observer's update method should have moved the transaction to completed
        assertNull(manager.getActiveTransaction("P-SYNC"), "Transaction should be finalized in the Observer.");
        assertEquals(1, manager.getCompletedTransactions().size());

        // Verify Strategy execution (Information Expert logic triggered via Observer)
        assertNotNull(manager.getCompletedTransactions().get(0).getFee(), "Fee should be calculated upon exit notification.");
    }
}
