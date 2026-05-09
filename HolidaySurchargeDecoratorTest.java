
/**
 * File: HolidaySurchargeDecoratorTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern.test;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking6.decorator.pattern.BaseStayCalculator;
import edu.du.ict4315.parking6.decorator.pattern.HolidaySurchargeDecorator;
import edu.du.ict4315.parking6.decorator.pattern.ParkingChargeCalculator;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the HolidaySurchargeDecorator
 * Verifies that the holiday multiplier is applied accurately to the base fee
 */
public class HolidaySurchargeDecoratorTest {

    private ParkingChargeCalculator baseCalculator;
    private final double HOURLY_RATE = 10.0;
    private final double HOLIDAY_MULTIPLIER = 2.0;

    @BeforeEach
    public void setUp() {
        // We use a real BaseStayCalculator as the component to be decorated
        baseCalculator = new BaseStayCalculator(HOURLY_RATE);
    }

    @Test
    public void testHolidaySurchargeApplication() {
        // Arrange: Wrap the base with the holiday decorator
        ParkingChargeCalculator holidayCalculator = new HolidaySurchargeDecorator(baseCalculator, HOLIDAY_MULTIPLIER);
        
        // Act: 2 hours @ $10/hr = $20.00 base. $20.00 * 2.0 = $40.00
        Duration stay = Duration.ofHours(2);
        Money result = holidayCalculator.calculateCharge(stay);
        
        // Assert
        assertNotNull(result);
        assertEquals(40.0, result.getAmount(), "The holiday decorator should double the base charge.");
    }

    @Test
    public void testHolidaySurchargeWithPartialHour() {
        // Arrange
        ParkingChargeCalculator holidayCalculator = new HolidaySurchargeDecorator(baseCalculator, HOLIDAY_MULTIPLIER);
        
        // Act: 1 hour 15 mins rounds to 2 hours ($20.00) * 2.0 = $40.00
        Duration stay = Duration.ofHours(1).plusMinutes(15);
        Money result = holidayCalculator.calculateCharge(stay);
        
        // Assert
        assertEquals(40.0, result.getAmount(), "The decorator must apply the multiplier to the rounded base amount.");
    }

    @Test
    public void testFlatHolidayRate() {
        // Arrange: Test with a different multiplier (e.g., a 50% increase)
        double surcharge = 1.5;
        ParkingChargeCalculator holidayCalculator = new HolidaySurchargeDecorator(baseCalculator, surcharge);
        
        // Act: 1 hour @ $10/hr = $10.00 base. $10.00 * 1.5 = $15.00
        Money result = holidayCalculator.calculateCharge(Duration.ofHours(1));
        
        // Assert
        assertEquals(15.0, result.getAmount(), "The decorator should handle variable holiday multipliers correctly.");
    }
}