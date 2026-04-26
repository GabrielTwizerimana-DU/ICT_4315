
/**
 * File: WeekendDiscountStrategyTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies.test;

import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.Address;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking3.strategies.WeekendDiscountStrategy;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests the WeekendDiscountStrategy.
 * Verifies that the 50% discount is applied correctly on weekends
 * and standard rates are applied on weekdays.
 */
public class WeekendDiscountStrategyTest {
  private WeekendDiscountStrategy strategy;
    private ParkingPermit compactPermit;
    private Customer testCustomer;

    @BeforeEach
    public void setUp() {
        strategy = new WeekendDiscountStrategy();
        
        Address address = new Address("2199 S University Blvd", "Denver", "CO", "80208");
        testCustomer = new Customer("C-DISC", "Discount Tester", address);

        // Setup a Compact car for standardized testing
        Car compactCar = new Car("DSC-101", CarType.COMPACT, testCustomer);
        compactPermit = new ParkingPermit("P-DSC-C", compactCar, LocalDateTime.now().minusDays(1));
    }

    @Test
    public void testCalculateFeeDynamic() {
        // Execute the strategy
        Money fee = strategy.calculateFee(compactPermit);
        
        // Determine current day state
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        boolean isWeekend = (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY);

        // Assertions based on the calendar
        if (isWeekend) {
            // Standard Compact (10.00) * 0.5 discount = 5.00
            assertEquals(5.00, fee.getAmount(), 0.001, "Weekend should apply 50% discount to Compact rate.");
        } else {
            // Standard Compact rate = 10.00
            assertEquals(10.00, fee.getAmount(), 0.001, "Weekday should apply standard Compact rate.");
        }
}}
