
/**
 * File: ParkingLotTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.controller.commands.test;

import edu.du.ict4315.parking1.controller.commands.ParkingLot;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 * Tests the ParkingLot class to ensure it correctly stores and exposes 
 * the strategy identifier required by the Factory Pattern.
 */
public class ParkingLotTest {
private ParkingLot lot;
    private final String LOT_ID = "LOT-123";
    private final String STRATEGY_TYPE = "WEEKDAY_PRIME";

    @BeforeEach
    public void setUp() {
        // Updated: The constructor now takes the lot ID and the Strategy String
        lot = new ParkingLot(LOT_ID, STRATEGY_TYPE);
    }

    @Test
    public void testGetLotId() {
        // This fixes the error: getLotId() is not public
        assertEquals(LOT_ID, lot.getLotId(), "The lot ID should be correctly retrieved.");
    }

    @Test
    public void testGetStrategyType() {
        // This is the key "Factory Hook" verification
        assertNotNull(lot.getStrategyType(), "Strategy type should not be null.");
        assertEquals(STRATEGY_TYPE, lot.getStrategyType(), 
            "The lot should return the correct string for the Factory to use.");
    }

    @Test
    public void testSetStrategyType() {
        // Verify that we can change the strategy at runtime (Dynamic Factory Selection)
        lot.setStrategyType("WEEKEND_DISCOUNT");
        assertEquals("WEEKEND_DISCOUNT", lot.getStrategyType(), 
            "The lot should be able to update its strategy identifier.");
    }
}
