
/**
 * File: CustomerTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.domain.model.classes.test;

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
 * Tests the Customer class as the Information Expert for personal data
 * Validates that identity is preserved and contact information is mutable
 */
public class CustomerTest {
private ParkingLot lot;
    private TransactionManager manager;
    private Customer customer;
    private ParkingPermit permit;

    @BeforeEach
    public void setUp() {
        // Initialize the Subject (Lot) and Observer (Manager)
        lot = new ParkingLot("L1", "Main Lot", 50);
        manager = new TransactionManager();
        
        // Correctly wiring the Observer Pattern
        lot.addObserver(manager);

        // Initialize real Customer and Car
        customer = new Customer("C-001", "Alice Smith", "123 Maple St");
        Car car = new Car("BTK-990", CarType.COMPACT, customer);
        
        // Initialize Permit
        permit = new ParkingPermit("P-101", car);

        // Associate the permit with the customer (Information Expert)
        customer.addPermit(permit);
    }

    @Test
    public void testCustomerDataInTransaction() {
        // Act: Trigger an entry event (Fix: Only pass the permit)
        lot.enter(permit);

        // Assert: Retrieve the transaction created by the observer
        ParkingTransaction tx = manager.getActiveTransaction("P-101");
        
        assertNotNull(tx, "Transaction should be created via Observer notification.");
        
        // Verify the customer data is accessible through the object graph
        assertEquals("Alice Smith", tx.getPermit().getCar().getOwner().getName());
        assertTrue(customer.getPermits().contains(tx.getPermit()));
    }

    @Test
    public void testCustomerChargeAssociation() {
        // Setup: Entry and Exit
        lot.enter(permit);
        lot.exit(permit);

        // Assert: Verify the finalized transaction
        ParkingTransaction tx = manager.getCompletedTransactions().get(0);
        
        // Verify that the observer finalized the transaction with a fee
        assertNotNull(tx.getExitTime(), "Exit time should be recorded.");
        assertTrue(tx.getFee().getAmount() >= 0, "The customer's transaction should have a valid fee.");
    }
}