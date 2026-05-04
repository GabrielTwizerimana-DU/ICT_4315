
/**
 * File: CarTypeTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.domain.model.classes.test;

import edu.du.ict4315.parking1.domain.model.classes.CarType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the CarType enum.
 * Ensures all required vehicle categories are present for the 
 * Strategy Factory to perform type-based fee calculations.
 */
public class CarTypeTest {
@Test
    public void testEnumConstants() {
        // Verify the presence of all required types for the Strategy Factory
        assertNotNull(CarType.valueOf("COMPACT"));
        assertNotNull(CarType.valueOf("SUV"));
        assertNotNull(CarType.valueOf("TRUCK"));
        assertNotNull(CarType.valueOf("VAN"));
    }

    @Test
    public void testEnumEquality() {
        // Ensure the constants are unique and comparable
        assertSame(CarType.COMPACT, CarType.valueOf("COMPACT"));
        assertNotSame(CarType.SUV, CarType.COMPACT);
    }

    @Test
    public void testEnumLength() {
        // Ensures no unexpected types have been added or removed
        assertEquals(4, CarType.values().length, "CarType enum should have exactly 4 types.");
    }
}