/**
 * File: ParkingChargeStrategyFactoryTest.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking4.factory.pattern.test;

import edu.du.ict4315.parking1.controller.commands.ParkingLot;
import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.controller.commands.ParkingTransaction;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking3.strategies.ParkingChargeStrategy;
import edu.du.ict4315.parking3.strategies.TypeBasedStrategy;
import edu.du.ict4315.parking3.strategies.WeekdayWeekendStrategy;
import edu.du.ict4315.parking4.factory.pattern.ParkingChargeStrategyFactory;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the Factory returns the correct concrete strategy
 * implementations and that they integrate with real domain objects.
 */
public class ParkingChargeStrategyFactoryTest {

    private ParkingTransaction transaction;

    @BeforeEach
    public void setUp() {
        // Setup real domain graph for testing strategy logic
        Customer customer = new Customer("C-FACTORY", "Factory User", "500 Industrial Way");
        Car car = new Car("FACT-2026", CarType.SUV, customer);
        ParkingPermit permit = new ParkingPermit("P-FACT", car);
        ParkingLot lot = new ParkingLot("L-FACT", "Factory Test Lot", 100);

        transaction = new ParkingTransaction(permit, lot, LocalDateTime.now());
    }

    @Test
    public void testTypeBasedStrategyInstantiation() {
        ParkingChargeStrategy strategy = ParkingChargeStrategyFactory.getStrategy("TYPE_BASED");

        assertNotNull(strategy);
        assertTrue(strategy instanceof TypeBasedStrategy, "Should return a TypeBasedStrategy instance.");

        // Verify execution: SUV should result in 20.0
        Money fee = strategy.calculateFee(transaction);
        assertEquals(20.0, fee.getAmount());
    }

    @Test
    public void testWeekdayWeekendStrategyInstantiation() {
        ParkingChargeStrategy strategy = ParkingChargeStrategyFactory.getStrategy("WEEKDAY_WEEKEND");

        assertNotNull(strategy);
        assertTrue(strategy instanceof WeekdayWeekendStrategy, "Should return a WeekdayWeekendStrategy instance.");
    }

    @Test
    public void testDefaultStrategyFallback() {
        // Test behavior for null or unknown keys
        ParkingChargeStrategy strategy = ParkingChargeStrategyFactory.getStrategy("UNKNOWN_KEY");

        assertNotNull(strategy, "Factory should provide a default strategy rather than returning null.");
        // Usually defaults to TypeBased or a base strategy
        assertTrue(strategy instanceof TypeBasedStrategy,
                "Expected TypeBasedStrategy as the default fallback, but got: "
                + strategy.getClass().getSimpleName());
    }
}
