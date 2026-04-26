
/**
 * File: TypeBasedStrategyTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies.test;

import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.Address;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking3.strategies.TypeBasedStrategy;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the TypeBasedStrategy implementation.
 * Verifies that different CarTypes result in the correct fee amounts
 * as defined in the strategy's switch logic.
 */
public class TypeBasedStrategyTest {
 private TypeBasedStrategy strategy;
    private Customer testCustomer;

    @BeforeEach
    public void setUp() {
        strategy = new TypeBasedStrategy();
        Address address = new Address("2199 S University Blvd", "Denver", "CO", "80208");
        testCustomer = new Customer("C1", "Gaby", address);
    }

    @Test
    public void testCalculateFeeForCompact() {
        // Setup: Compact Car
        Car compactCar = new Car("ABC-123", CarType.COMPACT, testCustomer);
        ParkingPermit permit = new ParkingPermit("P-COMP", compactCar, LocalDateTime.now().minusDays(1));

        // Execute
        Money fee = strategy.calculateFee(permit);

        // Verify: Compact should be 10.00
        assertNotNull(fee, "Fee should not be null.");
        assertEquals(10.00, fee.getAmount(), 0.001, "Compact fee should be 10.00");
        assertEquals("USD", fee.getCurrency());
    }

    @Test
    public void testCalculateFeeForSUV() {
        // Setup: SUV Car
        Car suvCar = new Car("SUV-999", CarType.SUV, testCustomer);
        ParkingPermit permit = new ParkingPermit("P-SUV", suvCar, LocalDateTime.now().minusDays(1));

        // Execute
        Money fee = strategy.calculateFee(permit);

        // Verify: SUV should be 15.00
        assertEquals(15.00, fee.getAmount(), 0.001, "SUV fee should be 15.00");
    }

    @Test
    public void testCalculateFeeForTruck() {
        // Setup: Truck Car
        Car truckCar = new Car("TRK-777", CarType.TRUCK, testCustomer);
        ParkingPermit permit = new ParkingPermit("P-TRK", truckCar, LocalDateTime.now().minusDays(1));

        // Execute
        Money fee = strategy.calculateFee(permit);

        // Verify: Truck should be 20.00
        assertEquals(20.00, fee.getAmount(), 0.001, "Truck fee should be 20.00");
    }

    @Test
    public void testCalculateFeeForDefault() {
        // Setup: A car type not explicitly handled in a specific way if applicable
        // (Assuming VAN or similar might hit the 'default' case)
        Car vanCar = new Car("VAN-444", CarType.VAN, testCustomer);
        ParkingPermit permit = new ParkingPermit("P-VAN", vanCar, LocalDateTime.now().minusDays(1));

        // Execute
        Money fee = strategy.calculateFee(permit);

        // Verify: Default should be 12.00
        assertEquals(12.00, fee.getAmount(), 0.001, "Default/Unknown type fee should be 12.00");
    }
    
}
