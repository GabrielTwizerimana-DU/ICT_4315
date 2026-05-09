
/**
 * File: PeakHoursMultiplierCalculatorTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern.test;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking6.decorator.pattern.ParkingChargeCalculator;
import edu.du.ict4315.parking6.decorator.pattern.PeakHoursMultiplierCalculator;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author gabby
 */
public class PeakHoursMultiplierCalculatorTest {
 
    private static class StubCalculator implements ParkingChargeCalculator {
        private final double fixedAmount;

        public StubCalculator(double fixedAmount) {
            this.fixedAmount = fixedAmount;
        }

        @Override
        public Money calculateCharge(Duration stayDuration) {
            return new Money(fixedAmount);
        }
    }

    @Test
    public void testCalculateChargeWithPeakLogic() {
        // Arrange
        double baseValue = 20.0;
        double multiplier = 2.0;
        
        // Wrap our Stub in the Decorator
        ParkingChargeCalculator stub = new StubCalculator(baseValue);
        PeakHoursMultiplierCalculator decorator = new PeakHoursMultiplierCalculator(stub, multiplier);

        // Act
        Duration duration = Duration.ofHours(2);
        Money result = decorator.calculateCharge(duration);

        // Assert
        double expected = baseValue;
        if (isCurrentTimePeak()) {
            expected = baseValue * multiplier;
        }

        assertEquals(expected, result.getAmount(), 0.001, 
            "The total should reflect the peak multiplier only during peak hours.");
    }

    /**
     * Mirroring the production logic to determine the expected result 
     * based on the actual time the test runs.
     */
    private boolean isCurrentTimePeak() {
        int hour = LocalDateTime.now().getHour();
        return (hour >= 7 && hour <= 9) || (hour >= 16 && hour <= 18);
    }
}
