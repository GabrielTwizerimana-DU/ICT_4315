
/**
 * File: WeekendDiscountStrategyTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment3.strategies.test;

import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.domain.model.classes.Money;
import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment3.strategies.WeekendDiscountStrategy;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WeekendDiscountStrategyTest {
    
    public WeekendDiscountStrategyTest() {
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

   private WeekendDiscountStrategy strategy;
    private Money baseRate;

    @BeforeEach
    public void setUp() {
        strategy = new WeekendDiscountStrategy();
        baseRate = new Money(1000); // $10.00 base rate for easy math
    }

    @Test
    public void testFullPrice_SUVOnWeekday() {
        // Monday, April 20, 2026
        LocalDateTime weekday = LocalDateTime.of(2026, 4, 20, 10, 0);
        Car suv = new Car("BIG-1", CarType.SUV, null);
        ParkingPermit permit = new ParkingPermit("P-100", suv);

        Money result = strategy.calculateCharge(baseRate, weekday, permit);

        // Expected: $10.00 (No discounts apply)
        assertEquals(1000, result.getAmountInCents(), "SUV on a Monday should be full price.");
    }

    @Test
    public void testEcoDiscount_CompactOnWeekday() {
        // Wednesday, April 22, 2026
        LocalDateTime weekday = LocalDateTime.of(2026, 4, 22, 14, 0);
        Car compact = new Car("ECO-1", CarType.COMPACT, null);
        ParkingPermit permit = new ParkingPermit("P-101", compact);

        Money result = strategy.calculateCharge(baseRate, weekday, permit);

        // Expected: $10.00 - 10% = $9.00 (900 cents)
        assertEquals(900, result.getAmountInCents(), "Compact car on a weekday should receive 10% discount.");
    }

    @Test
    public void testWeekendDiscount_SUVOnSaturday() {
        // Saturday, April 18, 2026
        LocalDateTime weekend = LocalDateTime.of(2026, 4, 18, 10, 0);
        Car suv = new Car("BIG-2", CarType.SUV, null);
        ParkingPermit permit = new ParkingPermit("P-102", suv);

        Money result = strategy.calculateCharge(baseRate, weekend, permit);

        // Expected: $10.00 - 50% = $5.00 (500 cents)
        assertEquals(500, result.getAmountInCents(), "SUV on a Saturday should receive 50% discount.");
    }

    @Test
    public void testDoubleDiscount_CompactOnSunday() {
        // Sunday, April 19, 2026
        LocalDateTime weekend = LocalDateTime.of(2026, 4, 19, 15, 0);
        Car compact = new Car("ECO-2", CarType.COMPACT, null);
        ParkingPermit permit = new ParkingPermit("P-103", compact);

        Money result = strategy.calculateCharge(baseRate, weekend, permit);

        // Expected: 1.0 - 0.1 (Compact) - 0.5 (Weekend) = 0.4 multiplier
        // $10.00 * 0.4 = $4.00 (400 cents)
        assertEquals(400, result.getAmountInCents(), "Compact car on Sunday should receive 60% total discount.");
    }
}
