
/**
 * File: ParkingChargeStrategyTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment3.strategies.test;

import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.domain.model.classes.Money;
import edu.university.parking.assignment3.strategies.ParkingChargeStrategy;
import edu.university.parking.assignment3.strategies.WeekdayPrimeStrategy;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ParkingChargeStrategyTest {
    
    private Money baseRate;
    private ParkingPermit permit;
    private LocalDateTime weekdayDateTime;

    @BeforeEach
    public void setUp() {
        // Standard setup for all strategy tests
        baseRate = new Money(1000, "USD"); // $10.00
        Customer customer = new Customer("C-123", "Tester", "Address", "555-0000");
        Car suv = new Car("SUV-PLATE", CarType.SUV, customer);
        permit = new ParkingPermit("P-555", suv);

        // Define specific test times
        weekdayDateTime = LocalDateTime.of(2026, 4, 14, 10, 0); // Tuesday 10 AM
        LocalDateTime.of(2026, 4, 19, 10, 0); // Sunday 10 AM
    }

    @Test
    public void testWeekdayPrimeStrategyBehavior() {
        // Programming to the interface
        ParkingChargeStrategy strategy = new WeekdayPrimeStrategy();
        
        Money result = strategy.calculateCharge(baseRate, weekdayDateTime, permit);
        
        // Assert based on your rules (e.g., $10 base + 20% SUV + 50% Prime = $17.00)
        assertNotNull(result);
        assertEquals(1700, result.getAmountInCents(), "WeekdayPrimeStrategy should apply surcharges correctly.");
}}
