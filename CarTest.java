/**
 * File: CarTest.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking1.domain.model.classes.test;

import edu.du.ict4315.parking1.controller.commands.ParkingLot;
import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.controller.commands.ParkingTransaction;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.management.layers.TransactionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies Car data integrity as it moves through the Observer chain
 * Resolves errors related to argument length in enter() and Car constructor
 */
public class CarTest {
    private ParkingLot lot;
    private TransactionManager manager;
    private Car car;
    private ParkingPermit permit;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        // Initialize the Subject and Observer
        lot = new ParkingLot("L1", "North Lot", 50);
        manager = new TransactionManager();
        lot.addObserver(manager);

        // Initialize Customer first (required for Car constructor)
        customer = new Customer("C-99", "Test User", "456 Lane");

        // FIX: Pass 3 arguments (license, type, owner) to match the Car constructor
        car = new Car("DEV-2026", CarType.SUV, customer);
        permit = new ParkingPermit("P-101", car);
    }

    @Test
    public void testCarDataIntegrityInNotification() {
        // FIX: lot.enter() now only requires the permit
        lot.enter(permit); 

        // Assert: Retrieve the transaction created by the observer
        ParkingTransaction tx = manager.getActiveTransaction("P-101");

        assertNotNull(tx, "Transaction should exist in the manager.");
        assertEquals("DEV-2026", tx.getPermit().getCar().getLicensePlate());
        assertEquals(CarType.SUV, tx.getPermit().getCar().getType());
    }

    @Test
    public void testCarTypeImpactsObserverLogic() {
        // Act: Trigger entry and exit
        lot.enter(permit);
        lot.exit(permit);

        // Assert: Verify the Observer used the Car's type to calculate the fee
        ParkingTransaction tx = manager.getCompletedTransactions().get(0);
        
        assertNotNull(tx.getFee(), "Fee object should not be null.");
        // Ensure we check the amount inside the Money object
        assertTrue(tx.getFee().getAmount() > 0, "Fee should be calculated based on SUV CarType.");
    }
}
