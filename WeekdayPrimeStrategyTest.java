
/**
 * File: WeekdayPrimeStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies.test;

import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.Address;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking3.strategies.WeekdayPrimeStrategy;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;


/**
 * Tests the WeekdayPrimeStrategy implementation.
 * Verifies that the premium pricing tier is correctly applied 
 * based on vehicle types.
 */
public class WeekdayPrimeStrategyTest {
    
  private WeekdayPrimeStrategy strategy;
    private Customer testCustomer;

    @BeforeEach
    public void setUp() {
        strategy = new WeekdayPrimeStrategy();
        Address address = new Address("2199 S University Blvd", "Denver", "CO", "80208");
        testCustomer = new Customer("C-PRIME", "Prime User", address);
    }

    @Test
    public void testCalculateFeeForCompactPrime() {
        Car compactCar = new Car("PRM-101", CarType.COMPACT, testCustomer);
        ParkingPermit permit = new ParkingPermit("P-PRM-C", compactCar, LocalDateTime.now().minusDays(1));

        Money fee = strategy.calculateFee(permit);

        // Verify: Compact Prime should be 15.00
        assertNotNull(fee, "Fee should not be null.");
        assertEquals(15.00, fee.getAmount(), 0.001, "Compact Prime fee should be 15.00");
        assertEquals("USD", fee.getCurrency());
    }

    @Test
    public void testCalculateFeeForSUVPrime() {
        Car suvCar = new Car("PRM-999", CarType.SUV, testCustomer);
        ParkingPermit permit = new ParkingPermit("P-PRM-S", suvCar, LocalDateTime.now().minusDays(1));

        Money fee = strategy.calculateFee(permit);

        // Verify: SUV Prime should be 25.00
        assertEquals(25.00, fee.getAmount(), 0.001, "SUV Prime fee should be 25.00");
    }

    @Test
    public void testCalculateFeeForTruckPrime() {
        Car truckCar = new Car("PRM-777", CarType.TRUCK, testCustomer);
        ParkingPermit permit = new ParkingPermit("P-PRM-T", truckCar, LocalDateTime.now().minusDays(1));

        Money fee = strategy.calculateFee(permit);

        // Verify: Truck Prime should be 35.00
        assertEquals(35.00, fee.getAmount(), 0.001, "Truck Prime fee should be 35.00");
    }

    @Test
    public void testCalculateFeeForDefaultPrime() {
        // Testing a type not explicitly mapped in the switch (using VAN as an example)
        Car vanCar = new Car("PRM-444", CarType.VAN, testCustomer);
        ParkingPermit permit = new ParkingPermit("P-PRM-V", vanCar, LocalDateTime.now().minusDays(1));

        Money fee = strategy.calculateFee(permit);

        // Verify: Default Prime should be 20.00
        assertEquals(20.00, fee.getAmount(), 0.001, "Default/Other type Prime fee should be 20.00");
    }
    }
