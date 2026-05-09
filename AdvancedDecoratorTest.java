

/**
 * File: AdvancedDecoratorTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern.test;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking6.decorator.pattern.BaseStayCalculator;
import edu.du.ict4315.parking6.decorator.pattern.HolidaySurchargeDecorator;
import edu.du.ict4315.parking6.decorator.pattern.LoyaltyDiscountDecorator;
import edu.du.ict4315.parking6.decorator.pattern.ParkingChargeCalculator;
import edu.du.ict4315.parking6.decorator.pattern.VehicleSizeSurchargeDecorator;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* These tests verify that the "chaining" of decorators works correctly and maintains financial accuracy
 */
public class AdvancedDecoratorTest {
    
    public AdvancedDecoratorTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
@Test
    public void testStackedHolidayAndSUV() {
        // Base $10/hr
        ParkingChargeCalculator calc = new BaseStayCalculator(10.0);
        
        // Wrap with SUV (1.5x) -> $15.00
        calc = new VehicleSizeSurchargeDecorator(calc, 1.5);
        
        // Wrap with Holiday (2.0x) -> $30.00
        calc = new HolidaySurchargeDecorator(calc, 2.0);
        
        Money result = calc.calculateCharge(Duration.ofHours(1));
        assertEquals(30.0, result.getAmount(), "Should apply both multipliers cumulatively.");
    }

    @Test
    public void testDiscountOnSurcharge() {
        // Base $10/hr
        ParkingChargeCalculator calc = new BaseStayCalculator(10.0);
        
        // SUV Surcharge (1.5x) -> $15.00
        calc = new VehicleSizeSurchargeDecorator(calc, 1.5);
        
        // 20% Loyalty Discount (0.80) -> $12.00
        calc = new LoyaltyDiscountDecorator(calc, 0.80);
        
        Money result = calc.calculateCharge(Duration.ofHours(1));
        assertEquals(12.0, result.getAmount(), "Discount should be applied to the already-surcharged amount.");
    }
}
