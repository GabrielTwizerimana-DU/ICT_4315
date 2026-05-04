/**
 * File: ParkingManagerTest.java
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
 * Verifies the TransactionManager's role as an Observer. Resolves "cannot find
 * symbol method park" and constructor mismatch errors.
 */
public class ParkingManagerTest {

    private TransactionManager manager;
    private ParkingLot lot;
    private ParkingPermit permit;

    @BeforeEach
    public void setUp() {
        manager = new TransactionManager();
        lot = new ParkingLot("L1", "East Lot", 100);

        // Setup real domain objects
        Customer customer = new Customer("C-99", "Tester", "123 Blvd");
        Car car = new Car("TEST-1", CarType.COMPACT, customer);
        permit = new ParkingPermit("P-1", car);

        // Standard Observer registration
        lot.addObserver(manager);
    }

    @Test
    public void testUpdateOnEntry() {
        // Create the event that the Subject (Lot) would broadcast
        ParkingEvent event = new ParkingEvent(lot, permit, ParkingAction.ENTER);

        // FIX: The manager is updated via update(), not park()
        manager.update(event);

        ParkingTransaction tx = manager.getActiveTransaction("P-1");
        assertNotNull(tx, "Manager should create a transaction upon receiving an ENTER event.");
        assertEquals(lot, tx.getLot(), "Transaction must record the correct lot.");
    }

    @Test
    public void testUpdateOnExit() {
        // 1. Setup an active transaction
        manager.update(new ParkingEvent(lot, permit, ParkingAction.ENTER));

        // 2. Trigger the exit event
        manager.update(new ParkingEvent(lot, permit, ParkingAction.EXIT));

        // Assertions
        assertNull(manager.getActiveTransaction("P-1"), "Transaction should no longer be active.");
        assertEquals(1, manager.getCompletedTransactions().size(), "Transaction should be moved to completed.");

        // Verify Fee Calculation logic was triggered
        assertNotNull(manager.getCompletedTransactions().get(0).getFee());
        assertTrue(manager.getCompletedTransactions().get(0).getFee().getAmount() >= 0);
    }
}
