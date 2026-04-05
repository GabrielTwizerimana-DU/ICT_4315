/**
 * File: ParkingTransactionTest.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.controller.commands.test;

import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.controller.commands.ParkingLot;
import edu.university.parking.assignment1.controller.commands.ParkingPermit;
import edu.university.parking.assignment1.controller.commands.ParkingTransaction;
import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.domain.model.classes.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ParkingTransaction class. Verifies that transaction
 * records accurately store permit, lot, and fee data.
 */

public class ParkingTransactionTest {

    private ParkingPermit testPermit;
    private ParkingLot testLot;
    private Money testFee;
    private Date testDate;
    private ParkingTransaction transaction;

    @BeforeEach
    public void setUp() {
        // 1. Setup dependencies
        Customer owner = new Customer("Bob", "Miller", "C-777", null, "555-1212");
        Car testCar = new Car("TRX-999", CarType.COMPACT, owner);
        testPermit = new ParkingPermit("P-999", testCar, new Date());
        testLot = new ParkingLot("L-North", "North Garage", 1000);

        // 2. Create a specific fee (e.g., $8.00 discounted rate)
        testFee = new Money(800, "USD");
        testDate = new Date();

        // 3. Initialize the transaction
        transaction = new ParkingTransaction(testDate, testPermit, testLot, testFee);
    }

    @Test
    public void testTransactionDataIntegrity() {
        // Verify the date is captured
        assertEquals(testDate, transaction.getDate(), "Transaction date mismatch");

        // Verify the charged amount is correct
        assertEquals(800, transaction.getChargedAmount().getAmountInCents(), "Charged amount mismatch");
        assertEquals("USD", transaction.getChargedAmount().getCurrency());
    }

    @Test
    public void testObjectAssociations() {
        // Ensure we can navigate from the transaction back to the lot and permit
        assertEquals("North Garage", transaction.getParkingLot().getName());
        assertEquals("P-999", transaction.getPermit().getId());
    }

    @Test
    public void testOwnerNavigation() {
        // This is a "Deep" test: can we get to the customer from the transaction?
        // Transaction -> Permit -> Car -> Owner
        String ownerLastName = transaction.getPermit().getCar().getOwner().getLastName();
        assertEquals("Miller", ownerLastName, "Should be able to navigate to the car owner");
    }

    @Test
    public void testToStringFormat() {
        // Optional: Verifies your toString() method provides a readable log entry
        String log = transaction.toString();
        assertNotNull(log);
        assertTrue(log.contains("P-999"), "Log should contain the permit ID");
        assertTrue(log.contains("800"), "Log should contain the charged amount");
    }
}
