
/**
 * File: WeekdayWeekendStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies.test;

import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.Address;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking3.strategies.ParkingChargeStrategy;
import edu.du.ict4315.parking3.strategies.WeekdayWeekendStrategy;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests the WeekdayWeekendStrategy implementation.
 * Validates that the fee changes based on the current day of the week.
 */
public class WeekdayWeekendStrategyTest {
    
    public WeekdayWeekendStrategyTest() {
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

    private ParkingChargeStrategy strategy;
    private ParkingPermit compactPermit;
    private ParkingPermit suvPermit;

    @BeforeEach
    public void setUp() {
        strategy = new WeekdayWeekendStrategy();
        
        Address addr = new Address("2199 S University Blvd", "Denver", "CO", "80208");
        Customer customer = new Customer("C-WEND", "Calendar User", addr);

        Car compactCar = new Car("WKD-101", CarType.COMPACT, customer);
        compactPermit = new ParkingPermit("P-WKD-C", compactCar, LocalDateTime.now().minusDays(1));

        Car suvCar = new Car("WKD-999", CarType.SUV, customer);
        suvPermit = new ParkingPermit("P-WKD-S", suvCar, LocalDateTime.now().minusDays(1));
    }

    @Test
    public void testCalculateFeeBasedOnCurrentDay() {
        Money fee = strategy.calculateFee(compactPermit);
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        
        boolean isWeekend = (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY);

        if (isWeekend) {
            // If running on Sat/Sun, should be flat 5.00
            assertEquals(5.00, fee.getAmount(), 0.001, "Weekend fee should be flat 5.00");
        } else {
            // If running Mon-Fri, should be Compact rate 10.00
            assertEquals(10.00, fee.getAmount(), 0.001, "Weekday Compact fee should be 10.00");
        }
    }

    @Test
    public void testSUVWeekdayRate() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        boolean isWeekend = (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY);
        
        Money fee = strategy.calculateFee(suvPermit);

        if (!isWeekend) {
            assertEquals(15.00, fee.getAmount(), 0.001, "Weekday SUV fee should be 15.00");
        } else {
            assertEquals(5.00, fee.getAmount(), 0.001, "Weekend SUV fee should still be flat 5.00");
        }
    }
}
