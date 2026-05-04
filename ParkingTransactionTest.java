/**
 * File: ParkingTransactionTest.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking1.controller.commands;

import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ParkingTransaction ensuring data integrity using real domain
 * objects and the Money class
 */
public class ParkingTransactionTest {

    private ParkingTransaction transaction;
    private ParkingPermit permit;
    private ParkingLot lot;
    private LocalDateTime entryTime;

    @BeforeEach
    public void setUp() {
        // Initialize real objects to satisfy dependencies
        Customer customer = new Customer("C-123", "John Doe", "555 Main St");
        Car car = new Car("GHY-456", CarType.SUV, customer);
        permit = new ParkingPermit("P-202", car);
        lot = new ParkingLot("L1", "South Lot", 100);
        entryTime = LocalDateTime.now().minusHours(2);

        // FIX: Constructor now requires (Permit, Lot, EntryTime)
        // Resolves "actual and formal argument lists differ in length"
        transaction = new ParkingTransaction(permit, lot, entryTime);
    }

    @Test
    public void testInitialState() {
        assertEquals(permit, transaction.getPermit());
        assertEquals(lot, transaction.getLot());
        assertEquals(entryTime, transaction.getEntryTime());

        // Verify Fee initializes as a Money object with 0 amount
        assertNotNull(transaction.getFee());
        assertEquals(0.0, transaction.getFee().getAmount());
    }

    @Test
    public void testFinalizeTransaction() {
        LocalDateTime exitTime = LocalDateTime.now();
        Money calculatedFee = new Money(15.0);

        // Act: Finalize the transaction
        transaction.setExitTime(exitTime);
        transaction.setFee(calculatedFee);

        // Assert: Verify state changes
        assertEquals(exitTime, transaction.getExitTime());
        assertEquals(calculatedFee, transaction.getFee());
        // FIX: Use getAmount() for numeric comparison
        assertEquals(15.0, transaction.getFee().getAmount());
    }

    @Test
    public void testDataTraceability() {
        // Verify we can navigate the object graph through the transaction
        assertEquals(CarType.SUV, transaction.getPermit().getCar().getType());
        assertEquals("John Doe", transaction.getPermit().getCar().getOwner().getName());
    }
}
