
/**
 * File: ParkingTransactionTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.controller.commands.test;

import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.controller.commands.ParkingLot;
import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.controller.commands.ParkingTransaction;
import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.domain.model.classes.Money;
import edu.university.parking.assignment3.strategies.WeekdayPrimeStrategy;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;



/**
 * Unit tests for the ParkingTransaction class. Verifies that transaction
 * records accurately store permit, lot, and fee data.
 */

public class ParkingTransactionTest {

    private ParkingPermit permit;
    private ParkingLot lot;
    private LocalDateTime testDate;
    private Money baseRate;

    @BeforeEach
    public void setUp() {
        // 1. Setup Domain Objects
        Customer customer = new Customer("C-999", "John Doe", "123 Lane", "555-1234");
        Car suv = new Car("TEST-PLATE", CarType.SUV, customer);
        permit = new ParkingPermit("P-100", suv);
        baseRate = new Money(1000, "USD"); // $10.00
        
        // 2. Setup a Lot with a specific Strategy
        // We'll use the WeekdayPrimeStrategy which has an SUV surcharge
        lot = new ParkingLot("North Lot", baseRate, new WeekdayPrimeStrategy());
        
        // 3. Set a specific date (Tuesday at 10:00 AM - Weekday Prime Time)
        testDate = LocalDateTime.of(2026, 4, 14, 10, 0);
    }

    @Test
    public void testTransactionStoresStrategyCalculatedAmount() {
        // The "Park" action usually happens in the ParkingOffice or TransactionManager
        // Here we test if the Transaction object correctly holds the Strategy's result
        
        // Strategy Math: $10 base + 20% SUV ($2) + 50% Prime ($5) = $17.00
        Money calculatedFee = lot.getCharge(testDate, permit);
        
        ParkingTransaction tx = new ParkingTransaction(testDate, permit, lot, calculatedFee);

        assertNotNull(tx, "Transaction should be successfully created.");
        assertEquals(calculatedFee, tx.getChargedAmount(), "Transaction must store the fee provided by the strategy.");
        assertEquals(1700, tx.getChargedAmount().getAmountInCents(), "The stored amount should reflect the SUV/Prime Time rules.");
    }

    @Test
    public void testTransactionReferentialIntegrity() {
        Money fee = new Money(500, "USD");
        ParkingTransaction tx = new ParkingTransaction(testDate, permit, lot, fee);

        // Ensure all components of the transaction are linked correctly
        assertEquals(permit, tx.getPermit(), "Transaction should be linked to the correct permit.");
        assertEquals(lot, tx.getParkingLot(), "Transaction should be linked to the correct lot.");
        assertEquals(testDate, tx.getTransactionDate(), "Transaction should store the correct timestamp.");
    }
}
