
/**
 * File: BaseStayCalculatorTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern.test;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking6.decorator.pattern.BaseStayCalculator;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BaseStayCalculator to ensure core 
 * pricing logic and rounding are accurate
 */
public class BaseStayCalculatorTest {

    private BaseStayCalculator calculator;
    private final double HOURLY_RATE = 5.0;

    @BeforeEach
    public void setUp() {
        // Initializing the concrete component of the Decorator pattern
        calculator = new BaseStayCalculator(HOURLY_RATE);
    }

    @Test
    public void testExactHourCalculation() {
        // 2 hours exactly: 2 * $5.00 = $10.00
        Duration stay = Duration.ofHours(2);
        Money result = calculator.calculateCharge(stay);
        
        assertNotNull(result);
        assertEquals(10.0, result.getAmount(), "Charge for 2 hours should be $10.00");
    }

    @Test
    public void testPartialHourRounding() {
        // 1 hour and 1 minute should round up to 2 hours: 2 * $5.00 = $10.00
        Duration stay = Duration.ofHours(1).plusMinutes(1);
        Money result = calculator.calculateCharge(stay);
        
        assertEquals(10.0, result.getAmount(), "Partial hours should round up to the next full hour");
    }

    @Test
    public void testUnderOneHourRounding() {
        // 15 minutes should round up to 1 hour: 1 * $5.00 = $5.00
        Duration stay = Duration.ofMinutes(15);
        Money result = calculator.calculateCharge(stay);
        
        assertEquals(5.0, result.getAmount(), "Stays under an hour should be charged for a full hour");
    }

    @Test
    public void testZeroDuration() {
        Duration stay = Duration.ZERO;
        Money result = calculator.calculateCharge(stay);
        
        assertEquals(0.0, result.getAmount(), "Zero duration should result in a zero charge");
    }
}