

/**
 * File: ParkingChargeStrategyFactoryTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking4.charges.factory.test;

import edu.du.ict4315.parking3.strategies.ParkingChargeStrategy;
import edu.du.ict4315.parking3.strategies.TypeBasedStrategy;
import edu.du.ict4315.parking3.strategies.WeekdayPrimeStrategy;
import edu.du.ict4315.parking3.strategies.WeekendDiscountStrategy;
import edu.du.ict4315.parking4.charges.factory.ParkingChargeStrategyFactory;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the ParkingChargeStrategyFactory.
 * Verifies that the factory returns the correct concrete implementation
 * based on the provided strategy name.
 */
public class ParkingChargeStrategyFactoryTest {
 private ParkingChargeStrategyFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new ParkingChargeStrategyFactory();
    }

    @Test
    public void testGetStrategyTypeBased() {
        ParkingChargeStrategy strategy = factory.getStrategy("TYPE_BASED");
        
        assertNotNull(strategy, "Factory should not return null for 'TYPE_BASED'");
        assertTrue(strategy instanceof TypeBasedStrategy, 
            "Should return an instance of TypeBasedStrategy");
    }

    @Test
    public void testGetStrategyWeekdayPrime() {
        ParkingChargeStrategy strategy = factory.getStrategy("WEEKDAY_PRIME");
        
        assertNotNull(strategy);
        assertTrue(strategy instanceof WeekdayPrimeStrategy, 
            "Should return an instance of WeekdayPrimeStrategy");
    }

    @Test
    public void testGetStrategyWeekendDiscount() {
        // Testing case-insensitivity and space handling if implemented in factory
        ParkingChargeStrategy strategy = factory.getStrategy("weekend discount");
        
        assertNotNull(strategy);
        assertTrue(strategy instanceof WeekendDiscountStrategy, 
            "Should handle lowercase and spaces to return WeekendDiscountStrategy");
    }

    @Test
    public void testGetStrategyDefaultOnInvalid() {
        // Testing how the factory handles unknown strings
        ParkingChargeStrategy strategy = factory.getStrategy("INVALID_NAME");
        
        assertNotNull(strategy, "Factory should return a default strategy instead of null");
        assertTrue(strategy instanceof TypeBasedStrategy, 
            "Factory should default to TypeBasedStrategy for unknown inputs");
    }

    @Test
    public void testGetStrategyDefaultOnNull() {
        // Testing null safety
        ParkingChargeStrategy strategy = factory.getStrategy(null);
        
        assertNotNull(strategy, "Factory should return a default strategy when input is null");
        assertTrue(strategy instanceof TypeBasedStrategy, 
            "Factory should default to TypeBasedStrategy for null inputs");
    }
}


