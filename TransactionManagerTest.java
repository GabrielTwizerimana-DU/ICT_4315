/**
 * File: TransactionManagerTest.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking1.management.layers.test;

import edu.du.ict4315.parking1.controller.commands.ParkingLot;
import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.controller.commands.ParkingTransaction;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.management.layers.TransactionManager;
import edu.du.ict4315.parking5.observer.pattern.ParkingAction;
import edu.du.ict4315.parking5.observer.pattern.ParkingEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for TransactionManager using real object instances Verifies
 * that the manager correctly reacts to Observer events
 */
public class TransactionManagerTest {

    private TransactionManager manager;
    private ParkingLot lot;
    private ParkingPermit permit;

    @BeforeEach
    public void setUp() {
        manager = new TransactionManager();
        lot = new ParkingLot("L1", "South Lot", 100);

        // Initialize real domain objects (No Mocks)
        Customer customer = new Customer("C-202", "Jane Doe", "321 Pine St");
        Car car = new Car("GHY-456", CarType.SUV, customer);
        permit = new ParkingPermit("P-202", car);

        // Register the manager as an observer to the lot
        lot.addObserver(manager);
    }

    @Test
    public void testUpdateOnEntry() {
        // Create the event that a Subject (Lot) would broadcast
        ParkingEvent event = new ParkingEvent(lot, permit, ParkingAction.ENTER);

        // FIX: The manager is updated via update(), not park()
        manager.update(event);

        ParkingTransaction activeTx = manager.getActiveTransaction("P-202");
        assertNotNull(activeTx, "Transaction should be active after entry event.");
        assertEquals(lot, activeTx.getLot(), "Transaction must record the correct lot.");
    }

    @Test
    public void testFullParkingLifecycle() {
        // 1. Simulate Entry via Observer update
        manager.update(new ParkingEvent(lot, permit, ParkingAction.ENTER));

        // 2. Simulate Exit via Observer update
        manager.update(new ParkingEvent(lot, permit, ParkingAction.EXIT));

        // Verify the transaction moved from active to completed
        assertNull(manager.getActiveTransaction("P-202"), "Transaction should be removed from active map.");
        assertEquals(1, manager.getCompletedTransactions().size());

        // 3. Verify the Fee Calculation (Information Expert logic)
        ParkingTransaction completedTx = manager.getCompletedTransactions().get(0);
        assertNotNull(completedTx.getFee(), "Finalized transaction must have a fee.");
        assertTrue(completedTx.getFee().getAmount() >= 0, "Fee should be a non-negative Money amount.");
    }
}
