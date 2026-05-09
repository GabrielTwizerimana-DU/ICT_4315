
/**
 * File: ParkingChargeCalculatorTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern.test;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking6.decorator.pattern.BaseStayCalculator;
import edu.du.ict4315.parking6.decorator.pattern.ParkingChargeCalculator;
import edu.du.ict4315.parking6.decorator.pattern.ParkingChargeDecorator;
import edu.du.ict4315.parking6.decorator.pattern.VehicleSizeSurchargeDecorator;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ParkingChargeCalculator Decorator hierarchy.
 * Verifies that decorators correctly wrap and augment base calculations.
 */
public class ParkingChargeCalculatorTest {

    private ParkingChargeCalculator baseCalculator;
    private final double HOURLY_RATE = 10.0;

    @BeforeEach
    public void setUp() {
        // Concrete Component
        baseCalculator = new BaseStayCalculator(HOURLY_RATE);
    }

    @Test
    public void testBaseCalculationWithoutDecorators() {
        // 2 hours * $10.00 = $20.00
        Money result = baseCalculator.calculateCharge(Duration.ofHours(2));
        assertEquals(20.0, result.getAmount(), "Base calculator should provide accurate un-decorated costs.");
    }

    @Test
    public void testSingleDecoratorApplication() {
        // Wrap the base with an SUV surcharge (1.5x)
        ParkingChargeCalculator suvDecorator = new VehicleSizeSurchargeDecorator(baseCalculator, 1.5);
        
        // 2 hours ($20.00) * 1.5 = $30.00
        Money result = suvDecorator.calculateCharge(Duration.ofHours(2));
        assertEquals(30.0, result.getAmount(), "Decorator should apply the 1.5x multiplier to the base amount.");
    }

    @Test
    public void testStackedDecorators() {
        // Chain: HolidaySurcharge(SUVDecorator(BaseCalculator))
        // Note: Assuming a simple HolidayDecorator for the purpose of the pattern test
        ParkingChargeCalculator suvDecorator = new VehicleSizeSurchargeDecorator(baseCalculator, 1.5);
        
        // Example of adding a second layer (Double rate for holidays)
        ParkingChargeCalculator holidayDecorator = new ParkingChargeDecorator(suvDecorator) {
            @Override
            public Money calculateCharge(Duration stayDuration) {
                Money amount = super.calculateCharge(stayDuration);
                return new Money(amount.getAmount() * 2.0);
            }
        };

        // 1 hour ($10.00) * 1.5 (SUV) * 2.0 (Holiday) = $30.00
        Money result = holidayDecorator.calculateCharge(Duration.ofHours(1));
        assertEquals(30.0, result.getAmount(), "Multiple decorators should correctly chain their calculations.");
    }

    @Test
    public void testDecoratorRoundingPersistence() {
        // Verify that rounding logic from BaseStayCalculator flows through the decorator
        ParkingChargeCalculator decorated = new VehicleSizeSurchargeDecorator(baseCalculator, 1.5);
        
        // 1 hour and 1 minute rounds up to 2 hours ($20.00) * 1.5 = $30.00
        Duration partialStay = Duration.ofHours(1).plusMinutes(1);
        Money result = decorated.calculateCharge(partialStay);
        
        assertEquals(30.0, result.getAmount(), "Decorators should operate on the rounded values provided by the base component.");
    }
}