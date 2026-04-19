
/**
 * File: TransactionManagerTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.management.layers.test;

import edu.university.parking.assignment1.controller.commands.ParkingLot;
import edu.university.parking.assignment1.controller.commands.ParkingTransaction;
import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.domain.model.classes.Money;
import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.management.layers.TransactionManager;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gabby
 */
public class TransactionManagerTest {
    
    public TransactionManagerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    

    @AfterEach
    public void tearDown() {
    }

   private TransactionManager transactionManager;
    private Customer testCustomer;
    private ParkingPermit testPermit;
    private ParkingLot testLot;

    @BeforeEach
    public void setUp() {
        transactionManager = new TransactionManager();
        
        // Setup dependencies
        testCustomer = new Customer("C-101", "Jim Halpert", "123 Paper St", "555-1212");
        Car testCar = new Car("LVN-828", CarType.SUV, testCustomer);
        testPermit = new ParkingPermit("P-101", testCar);
        
        // For testing, we don't necessarily need a real strategy, just the object
        testLot = new ParkingLot("North Lot", new Money(1000, "USD"), null);
    }

    @Test
    public void testParkRecordsTransaction() {
        LocalDateTime now = LocalDateTime.now();
        Money fee = new Money(1500, "USD");

        // Act
        ParkingTransaction tx = transactionManager.park(now, testPermit, testLot, fee);

        // Assert
        assertNotNull(tx);
        assertEquals(fee, tx.getChargedAmount(), "Manager must store the exact fee provided.");
        assertEquals(1, transactionManager.getTransactions().size(), "Ledger should contain exactly one transaction.");
    }

    @Test
    public void testGetTransactionsByCustomer() {
        LocalDateTime now = LocalDateTime.now();
        transactionManager.park(now, testPermit, testLot, new Money(1000, "USD"));
        
        // Create a second customer/car to ensure filtering works
        Customer otherCustomer = new Customer("C-999", "Dwight Schrute", "Beet Farm", "555-0000");
        Car otherCar = new Car("GHOST-1", CarType.COMPACT, otherCustomer);
        ParkingPermit otherPermit = new ParkingPermit("P-999", otherCar);
        
        transactionManager.park(now, otherPermit, testLot, new Money(500, "USD"));

        // Act: Get transactions only for Jim
        List<ParkingTransaction> jimTx = transactionManager.getTransactions(testCustomer);

        // Assert
        assertEquals(1, jimTx.size(), "Should only return transactions for the specific customer.");
        assertEquals(testCustomer, jimTx.get(0).getPermit().getCar().getOwner());
    }
}
