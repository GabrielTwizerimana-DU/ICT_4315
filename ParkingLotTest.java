
/**
 * File: ParkingLotTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.controller.commands.test;

import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.controller.commands.ParkingLot;
import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.domain.model.classes.Money;
import edu.university.parking.assignment3.strategies.WeekdayPrimeStrategy;
import edu.university.parking.assignment3.strategies.WeekendDiscountStrategy;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class ParkingLotTest {

    private ParkingLot northLot;
    private ParkingLot southLot;
    private Money baseRate;
    private ParkingPermit suvPermit;
    private ParkingPermit compactPermit;

    @BeforeEach
    public void setUp() {
        baseRate = new Money(1000, "USD"); // $10.00
        
        // Strategy A: Weekday/Prime rules
        northLot = new ParkingLot("North Lot", baseRate, new WeekdayPrimeStrategy());
        
        // Strategy B: Weekend/Compact rules
        southLot = new ParkingLot("South Lot", baseRate, new WeekendDiscountStrategy());

        // Setup Cars and Permits
        Customer alice = new Customer("C-101", "Alice", "Address", "555-0101");
        Car suv = new Car("SUV-123", CarType.SUV, alice);
        Car compact = new Car("MINI-123", CarType.COMPACT, alice);
        
        suvPermit = new ParkingPermit("P-SUV", suv);
        compactPermit = new ParkingPermit("P-MINI", compact);
    }

    @Test
    public void testWeekdayPrimeStrategyWithSUV() {
        // Tuesday at 10:00 AM (Weekday + Prime Time + SUV)
        LocalDateTime primeTime = LocalDateTime.of(2026, 4, 14, 10, 0);
        
        Money charge = northLot.getCharge(primeTime, suvPermit);
        
        // Math: $10 base + 20% SUV ($12) + 50% Prime ($5) = $17.00
        // (Note: Adjust based on your specific multiplier logic - additive vs compounding)
        assertEquals(1700, charge.getAmountInCents(), "SUV during prime time should include surcharges.");
    }

    @Test
    public void testWeekendDiscountStrategyWithCompact() {
        // Sunday at 2:00 PM (Weekend + Compact Discount)
        LocalDateTime sundayAfternoon = LocalDateTime.of(2026, 4, 19, 14, 0);
        
        Money charge = southLot.getCharge(sundayAfternoon, compactPermit);
        
        // Math: $10 base - 10% Compact ($9) - 50% Weekend ($5) = $4.00
        assertEquals(400, charge.getAmountInCents(), "Compact car on weekend should receive maximum discounts.");
    }

    @Test
    public void testStrategySwappingAtRuntime() {
        LocalDateTime weekday = LocalDateTime.of(2026, 4, 15, 12, 0);
        
        // Initially North Lot uses WeekdayPrime
        Money originalCharge = northLot.getCharge(weekday, suvPermit);
        
        // Swap to Weekend strategy mid-test
        northLot.setStrategy(new WeekendDiscountStrategy());
        Money newCharge = northLot.getCharge(weekday, suvPermit);
        
        assertNotEquals(originalCharge.getAmountInCents(), newCharge.getAmountInCents(), 
            "Changing the strategy should change the resulting charge.");
    }
}
