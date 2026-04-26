
/**
 * File: ParkingChargeStrategyTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies.test;

import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.Address;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking3.strategies.ParkingChargeStrategy;
import edu.du.ict4315.parking3.strategies.WeekdayPrimeStrategy;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests the ParkingChargeStrategy implementations.
 * Ensures the strategy can access CarType through the Permit 
 * and return the correct Money amount.
 */
public class ParkingChargeStrategyTest {
   private ParkingChargeStrategy strategy;
    private ParkingPermit compactPermit;
    private ParkingPermit suvPermit;

    @BeforeEach
    public void setUp() {
        // We test the interface using a concrete implementation
        strategy = new WeekdayPrimeStrategy();

        // Setup common entities
        Address address = new Address("2199 S University Blvd", "Denver", "CO", "80208");
        Customer customer = new Customer("C1", "Gaby", address);

        // Create a Compact Car and Permit
        Car compactCar = new Car("TNY-123", CarType.COMPACT, customer);
        compactPermit = new ParkingPermit("P-101", compactCar, LocalDateTime.now().minusDays(1));

        // Create an SUV Car and Permit
        Car suvCar = new Car("BIG-999", CarType.SUV, customer);
        suvPermit = new ParkingPermit("P-102", suvCar, LocalDateTime.now().minusDays(1));
    }

    @Test
    public void testCalculateFeeCompact() {
        Money fee = strategy.calculateFee(compactPermit);
        
        assertNotNull(fee, "Strategy should return a Money object.");
        // Assuming WeekdayStrategy charges 10.00 for Compact
        assertEquals(10.00, fee.getAmount(), 0.001);
        assertEquals("USD", fee.getCurrency());
    }

    @Test
    public void testCalculateFeeSUV() {
        Money fee = strategy.calculateFee(suvPermit);
        
        assertNotNull(fee, "Strategy should return a Money object.");
        // Assuming WeekdayStrategy charges 15.00 for SUV
        assertEquals(15.00, fee.getAmount(), 0.001);
    }

    @Test
    public void testStrategyIndependence() {
        // Verify that the strategy relies on the CarType, not the Permit object itself
        Customer anotherCustomer = new Customer("C2", "Alex", null);
        Car anotherCompact = new Car("LIT-555", CarType.COMPACT, anotherCustomer);
        ParkingPermit newPermit = new ParkingPermit("P-103", anotherCompact, LocalDateTime.now().minusDays(1));
        
        Money fee = strategy.calculateFee(newPermit);
        assertEquals(10.00, fee.getAmount(), "All compact cars should result in the same fee.");
    }
 }
