
/**
 * File: VehicleSizeSurchargeDecoratorTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern.test;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking6.decorator.pattern.BaseStayCalculator;
import edu.du.ict4315.parking6.decorator.pattern.ParkingChargeCalculator;
import edu.du.ict4315.parking6.decorator.pattern.VehicleSizeSurchargeDecorator;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the VehicleSizeSurchargeDecorator.
 * Ensures the decorator correctly modifies the base charge.
 */
public class VehicleSizeSurchargeDecoratorTest {

    private ParkingChargeCalculator baseCalculator;
    private final double HOURLY_RATE = 10.0;
    private final double SUV_MULTIPLIER = 1.5;

    @BeforeEach
    public void setUp() {
        // We use the real BaseStayCalculator as the underlying component
        baseCalculator = new BaseStayCalculator(HOURLY_RATE);
    }

    @Test
    public void testSurchargeCalculation() {
        // Arrange: Wrap the base with the size decorator
        ParkingChargeCalculator decorated = new VehicleSizeSurchargeDecorator(baseCalculator, SUV_MULTIPLIER);
        
        // Act: 2 hours at $10/hr = $20 base. $20 * 1.5 = $30.00
        Money result = decorated.calculateCharge(Duration.ofHours(2));
        
        // Assert
        assertNotNull(result);
        assertEquals(30.0, result.getAmount(), "The decorator should apply the 1.5x multiplier to the $20.00 base.");
    }

    @Test
    public void testNoSurchargeWithMultiplierOfOne() {
        // Arrange: Use a multiplier of 1.0 (equivalent to no surcharge)
        ParkingChargeCalculator decorated = new VehicleSizeSurchargeDecorator(baseCalculator, 1.0);
        
        // Act: 1 hour at $10/hr = $10.00
        Money result = decorated.calculateCharge(Duration.ofHours(1));
        
        // Assert
        assertEquals(10.0, result.getAmount(), "A multiplier of 1.0 should return the base amount unchanged.");
    }

    @Test
    public void testRoundingBeforeSurcharge() {
        // Arrange: Wrap base with decorator
        ParkingChargeCalculator decorated = new VehicleSizeSurchargeDecorator(baseCalculator, SUV_MULTIPLIER);
        
        // Act: 1 hour and 10 minutes rounds to 2 hours ($20.00) * 1.5 = $30.00
        Duration stay = Duration.ofHours(1).plusMinutes(10);
        Money result = decorated.calculateCharge(stay);
        
        // Assert
        assertEquals(30.0, result.getAmount(), "The surcharge must be applied after the base rounding logic occurs.");
    }
}