
/**
 * File: TypeBasedStrategyTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment3.strategies.test;

import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.domain.model.classes.Money;
import edu.university.parking.assignment3.strategies.TypeBasedStrategy;
import java.time.LocalDateTime;
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
public class TypeBasedStrategyTest {
    
    public TypeBasedStrategyTest() {
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

    /**
     * Test of calculateCharge method, of class TypeBasedStrategy.
     */
   private TypeBasedStrategy strategy;
    private Money baseRate;
    private LocalDateTime testTime;

    @BeforeEach
    public void setUp() {
        strategy = new TypeBasedStrategy();
        // INITIALIZE baseRate here to prevent the NullPointerException
        baseRate = new Money(1000, "USD"); // $10.00
        testTime = LocalDateTime.of(2026, 4, 17, 10, 0); // Friday morning
    }

    @Test
    public void testCalculateChargeForSUV() {
        Customer owner = new Customer("C-1", "Jim", "Scranton", "555-1212");
        Car suv = new Car("SUV-123", CarType.SUV, owner);
        ParkingPermit permit = new ParkingPermit("P-SUV", suv);

        // Act
        Money result = strategy.calculateCharge(baseRate, testTime, permit);

        // Assert: Assuming SUV has a 1.2x multiplier ($10.00 -> $12.00)
        assertNotNull(result, "Resulting money object should not be null");
        assertEquals(1200, result.getAmountInCents(), "SUV should be charged at 1.2x base rate.");
    }

    @Test
    public void testCalculateChargeForCompact() {
        Customer owner = new Customer("C-2", "Dwight", "Beet Farm", "555-0000");
        Car compact = new Car("MINI-01", CarType.COMPACT, owner);
        ParkingPermit permit = new ParkingPermit("P-MINI", compact);

        // Act
        Money result = strategy.calculateCharge(baseRate, testTime, permit);

        // Assert: Assuming Compact has a 0.8x or 1.0x multiplier
        // If 1.0x, cents should be 1000
        assertEquals(1000, result.getAmountInCents(), "Compact should be charged at base rate.");
    }

    @Test
public void testNullBaseRateThrowsException() {
    // Verification of defensive programming
    Customer owner = new Customer("C-1", "Jim", "Scranton", "555-1212");
    Car suv = new Car("SUV-123", CarType.SUV, owner);
    ParkingPermit permit = new ParkingPermit("P-SUV", suv);

    // Simplified: No need to store the exception in a variable unless checking the message
    assertThrows(IllegalArgumentException.class, () -> {
        strategy.calculateCharge(null, testTime, permit);
    }, "Strategy should throw an exception if baseRate is null.");
}
    
}
