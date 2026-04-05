/**
 * File: ParkingLotTest.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.controller.commands.test;

import edu.university.parking.assignment1.controller.commands.ParkingLot;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.domain.model.classes.Money;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    @Test
    public void testDailyRateCalculations() {
        ParkingLot lot = new ParkingLot("L1", "Test Lot", 1000);

        // Test SUV (Full Price: 1000)
        Money suvRate = lot.getDailyRate(CarType.SUV);
        assertEquals(1000, suvRate.getAmountInCents(), "SUV should be full price");

        // Test COMPACT (Discounted: 800)
        Money compactRate = lot.getDailyRate(CarType.COMPACT);
        assertEquals(800, compactRate.getAmountInCents(), "Compact should have 20% discount");
    }
}
