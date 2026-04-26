
/**
 * File: TransactionManagerTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking.management.layers.test;

import edu.du.ict4315.parking1.controller.commands.ParkingTransaction;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.Address;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking4.charges.factory.TransactionManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionManagerTest {
  private TransactionManager transactionManager;
    private ParkingPermit permit;
    private final String LOT_NAME = "North Garage";

    @BeforeEach
    public void setUp() {
        transactionManager = new TransactionManager();

        // Build the required object graph for the Strategy Factory to use
        Address address = new Address("2199 S University Blvd", "Denver", "CO", "80208");
        Customer customer = new Customer("C1", "Gaby", address);
        Car car = new Car("BTK-555", CarType.SUV, customer);
        permit = new ParkingPermit("P-101", car,null );
    }

    @Test
    public void testParkRecordsTransaction() {
        // We use "WEEKDAY" as the strategy name, assuming it's implemented in your Factory
        String strategyName = "WEEKDAY";
        ParkingTransaction transaction = transactionManager.park(permit, LOT_NAME, strategyName);

        // 1. Verify transaction was created and returned
        assertNotNull(transaction, "The park method should return a valid transaction object.");
        
        // 2. Verify lot information (Fixes the "lot not found" context)
        assertEquals(LOT_NAME, transaction.getLot(), "The transaction should record the correct lot name.");

        // 3. Verify permit linkage
        assertEquals(permit, transaction.getPermit(), "The transaction must be linked to the provided permit.");

        // 4. Verify financial data (Money object)
        assertNotNull(transaction.getAmount(), "The transaction must contain a calculated fee.");
        assertTrue(transaction.getAmount().getAmount() > 0, "The fee amount should be greater than zero.");
        
        // 5. Verify storage in manager
        List<ParkingTransaction> allTransactions = transactionManager.getTransactions();
        assertEquals(1, allTransactions.size(), "Manager should have one recorded transaction.");
        assertEquals(transaction, allTransactions.get(0));
    }

    @Test
    public void testParkTimestampIsRecent() {
        ParkingTransaction transaction = transactionManager.park(permit, LOT_NAME, "WEEKDAY");
        
        assertNotNull(transaction.getDate(), "Transaction should have a timestamp.");
        // Basic check to ensure it wasn't initialized to a default like LocalDateTime.MIN
        assertTrue(transaction.getDate().isAfter(java.time.LocalDateTime.now().minusMinutes(1)), 
            "Timestamp should reflect the current time of parking.");
    }
}
