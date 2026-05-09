
/**
 * File: LoyaltyDiscountDecoratorTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern.test;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking6.decorator.pattern.BaseStayCalculator;
import edu.du.ict4315.parking6.decorator.pattern.LoyaltyDiscountDecorator;
import edu.du.ict4315.parking6.decorator.pattern.ParkingChargeCalculator;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the LoyaltyDiscountDecorator
 * Verifies that the discount rate is applied correctly to the base charge
 */
public class LoyaltyDiscountDecoratorTest {

    private ParkingChargeCalculator baseCalculator;
    private final double HOURLY_RATE = 20.0;
    private final double DISCOUNT_RATE = 0.80; // 20% discount

    @BeforeEach
    public void setUp() {
        // Use the real base component
        baseCalculator = new BaseStayCalculator(HOURLY_RATE);
    }

    @Test
    public void testDiscountApplication() {
        // Arrange: Wrap base with the loyalty decorator
        ParkingChargeCalculator discountedCalc = new LoyaltyDiscountDecorator(baseCalculator, DISCOUNT_RATE);
        
        // Act: 1 hour @ $20/hr = $20.00 base. $20.00 * 0.80 = $16.00
        Money result = discountedCalc.calculateCharge(Duration.ofHours(1));
        
        // Assert
        assertNotNull(result);
        assertEquals(16.0, result.getAmount(), "The decorator should apply a 20% discount to the base charge.");
    }

    @Test
    public void testDiscountWithRounding() {
        // Arrange
        ParkingChargeCalculator discountedCalc = new LoyaltyDiscountDecorator(baseCalculator, DISCOUNT_RATE);
        
        // Act: 1 hour 30 mins rounds to 2 hours ($40.00) * 0.80 = $32.00
        Duration stay = Duration.ofHours(1).plusMinutes(30);
        Money result = discountedCalc.calculateCharge(stay);
        
        // Assert
        assertEquals(32.0, result.getAmount(), "The discount must be applied after the base hourly rounding.");
    }

    @Test
    public void testNoDiscountScenario() {
        // Arrange: Use a multiplier of 1.0 (no discount)
        ParkingChargeCalculator discountedCalc = new LoyaltyDiscountDecorator(baseCalculator, 1.0);
        
        // Act: 1 hour @ $20/hr
        Money result = discountedCalc.calculateCharge(Duration.ofHours(1));
        
        // Assert
        assertEquals(20.0, result.getAmount(), "A discount rate of 1.0 should not alter the price.");
    }
}