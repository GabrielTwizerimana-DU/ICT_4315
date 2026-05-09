
/**
 * File: ParkingChargeCalculatorFactoryTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern.test;

import edu.du.ict4315.parking1.controller.commands.ParkingLot;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking6.decorator.pattern.ParkingChargeCalculator;
import edu.du.ict4315.parking6.decorator.pattern.ParkingChargeCalculatorFactory;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the Factory. 
 * Verifies that the correct Decorator "stack" is built based on domain state.
 */
public class ParkingChargeCalculatorFactoryTest {

    private ParkingLot lot;
    private Customer customer;
    private final double BASE_RATE = 10.0;

    @BeforeEach
    public void setUp() {
        // Initialize real objects
        lot = new ParkingLot("L1", "University Garage", 100);
        lot.setBaseHourlyRate(BASE_RATE);
        
        // Car requires a Customer per your updated constructor
        customer = new Customer("C-100", "Developer User", "123 Java Lane");
    }

    @Test
    public void testCreateBaseCalculator() {
        // Scenario: Standard car, no holiday, no loyalty
        Car car = new Car("REG-123", CarType.COMPACT, customer);
        ParkingChargeCalculator calc = ParkingChargeCalculatorFactory.createCalculator(lot, car);
        
        // Should be exactly $10.00 for 1 hour
        assertEquals(10.0, calc.calculateCharge(Duration.ofHours(1)).getAmount());
    }

    @Test
    public void testCreateSUVHolidayStack() {
        // Scenario: SUV during a Holiday
        lot.setHolidayRateActive(true);
        Car suv = new Car("SUV-999", CarType.SUV, customer);
        
        ParkingChargeCalculator calc = ParkingChargeCalculatorFactory.createCalculator(lot, suv);
        
        // Calculation: $10 (Base) * 1.5 (SUV) * 2.0 (Holiday) = $30.00
        assertEquals(30.0, calc.calculateCharge(Duration.ofHours(1)).getAmount(), 
            "Factory should stack SUV and Holiday decorators.");
    }

    @Test
    public void testCreateFullStackWithLoyalty() {
        // Scenario: SUV, Holiday, and Loyalty Member
        lot.setHolidayRateActive(true);
        customer.setLoyaltyMember(true);
        Car suv = new Car("LOYAL-1", CarType.SUV, customer);
        
        ParkingChargeCalculator calc = ParkingChargeCalculatorFactory.createCalculator(lot, suv);
        
        // Calculation: ($10 * 1.5 * 2.0) * 0.8 (20% discount) = $24.00
        assertEquals(24.0, calc.calculateCharge(Duration.ofHours(1)).getAmount(),
            "Factory should apply loyalty discount as the final layer.");
    }

    @Test
    public void testRoundingPersistenceInFactoryStack() {
        // Ensure rounding from BaseStayCalculator survives the whole decorator chain
        Car car = new Car("REG-123", CarType.COMPACT, customer);
        lot.setHolidayRateActive(true); // 2.0x multiplier
        
        ParkingChargeCalculator calc = ParkingChargeCalculatorFactory.createCalculator(lot, car);
        
        // 1 hr 5 min rounds to 2 hrs ($20.00) * 2.0 (Holiday) = $40.00
        Duration stay = Duration.ofHours(1).plusMinutes(5);
        assertEquals(40.0, calc.calculateCharge(stay).getAmount());
    }
}