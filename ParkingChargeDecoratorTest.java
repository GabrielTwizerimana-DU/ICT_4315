

/**
 * File: ParkingChargeDecoratorTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern.test;

import edu.du.ict4315.parking1.controller.commands.ParkingLot;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking6.decorator.pattern.BaseStayCalculator;
import edu.du.ict4315.parking6.decorator.pattern.ParkingChargeCalculator;
import edu.du.ict4315.parking6.decorator.pattern.ParkingChargeCalculatorFactory;
import edu.du.ict4315.parking6.decorator.pattern.VehicleSizeSurchargeDecorator;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gabby
 */
public class ParkingChargeDecoratorTest {
    
    public ParkingChargeDecoratorTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {

    }
    
    @AfterEach
    public void tearDown() {
    }

   @Test
    public void testBaseStayCalculation() {
        ParkingChargeCalculator base = new BaseStayCalculator(5.0);
        // 2 hours at $5.00
        Money result = base.calculateCharge(Duration.ofHours(2));
        assertEquals(10.0, result.getAmount());
    }

    @Test
    public void testVehicleSizeDecorator() {
        ParkingChargeCalculator base = new BaseStayCalculator(10.0);
        // Wrap with a 1.5x SUV surcharge
        ParkingChargeCalculator decorated = new VehicleSizeSurchargeDecorator(base, 1.5);
        
        // 1 hour at $10.00 * 1.5 = $15.00
        Money result = decorated.calculateCharge(Duration.ofHours(1));
        assertEquals(15.0, result.getAmount());
    }

    @Test
    public void testFactoryCreationForSUV() {
        Customer customer = new Customer("C-001", "Alice Smith", "123 Maple St");
        ParkingLot lot = new ParkingLot("L1", "Main Lot", 100);
        lot.setBaseHourlyRate(6.0);
        Car suv = new Car("TEST-1", CarType.SUV, customer);

        ParkingChargeCalculator calc = ParkingChargeCalculatorFactory.createCalculator(lot, suv);
        
        // 1 hour: $6.00 (Base) * 1.5 (SUV Decorator) = $9.00
        Money result = calc.calculateCharge(Duration.ofHours(1));
        assertEquals(9.0, result.getAmount());
    }
}
