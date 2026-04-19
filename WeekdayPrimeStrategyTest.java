
/**
 * File: WeekdayPrimeStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment3.strategies.test;

import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.domain.model.classes.Money;
import edu.university.parking.assignment3.strategies.ParkingChargeStrategy;
import edu.university.parking.assignment3.strategies.TypeBasedStrategy;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;



/**
 *
 * @author gabby
 */
public class WeekdayPrimeStrategyTest {
    
    private ParkingChargeStrategy strategy;
    private Money baseRate;
    private ParkingPermit suvPermit;
    private ParkingPermit compactPermit;

    @BeforeEach
    public void setUp() {
      
    strategy = new TypeBasedStrategy();
    
    // FIX: Ensure this is not null!
    baseRate = new Money(1000, "USD"); 

    Customer customer = new Customer("C-1", "Test User", "123 St", "555-1212");
    Car compact = new Car("COMP-1", CarType.COMPACT, customer);
    
    // Ensure the permit is also valid
    compactPermit = new ParkingPermit("P-1", compact);
    }

 @Test
public void testWeekdayPrimeWithSUV() {
    // 1. Setup the dependencies
    Customer owner = new Customer("C-1", "Jim", "Scranton", "555-1212");
    Car suv = new Car("SUV-123", CarType.SUV, owner);
    
    // 2. You MUST initialize this permit variable!
    ParkingPermit suvPermit = new ParkingPermit("P-SUV", suv); 
    
    Money baseRate = new Money(1000, "USD");
    LocalDateTime mondayNoon = LocalDateTime.of(2026, 4, 13, 12, 0); // A Monday

    // 3. Ensure suvPermit is passed as the third argument
    Money result = strategy.calculateCharge(baseRate, mondayNoon, suvPermit);

    assertNotNull(result);
}

    @Test
    public void testWeekdayOffPeakWithCompact() {
        // Wednesday (Weekday) at 9:00 PM (Off-Peak)
        LocalDateTime wednesdayNinePM = LocalDateTime.of(2026, 4, 15, 21, 0);
        
        Money result = strategy.calculateCharge(baseRate, wednesdayNinePM, compactPermit);
        
        // Base: $10.00
        // No SUV surcharge, No Prime Time surcharge
        // Total: $10.00
        assertEquals(1000, result.getAmountInCents(), "Off-peak weekday for compact should be base rate.");
    }

    @Test
    public void testWeekendPrimeTimeSurchargeExclusion() {
        // Saturday at 10:00 AM (Technically "Prime" hours, but it's a Weekend)
        LocalDateTime saturdayTenAM = LocalDateTime.of(2026, 4, 18, 10, 0);
        
        Money result = strategy.calculateCharge(baseRate, saturdayTenAM, compactPermit);
        
        // Strategy only applies Prime surcharge to Weekdays (Mon-Fri)
        // Total: $10.00
        assertEquals(1000, result.getAmountInCents(), "Prime surcharge should not apply on weekends.");
    }
    
    }
