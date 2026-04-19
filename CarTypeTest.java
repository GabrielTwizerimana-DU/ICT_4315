
/**
 * File: CarTypeTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.domain.model.classes.test;

import edu.university.parking.assignment1.domain.model.classes.CarType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CarType enum.
 * Ensures the required vehicle categories exist for pricing logic.
 */
public class CarTypeTest {

    @Test
    public void testEnumValuesExistence() {
        // Verifies that the required types for Release 2 exist in the Enum
        assertNotNull(CarType.valueOf("SUV"), "SUV type must be defined for strategy multipliers.");
        assertNotNull(CarType.valueOf("COMPACT"), "COMPACT type must be defined for base rate logic.");
    }

    @Test
    public void testEnumToString() {
        // Ensures the names are formatted correctly for UI or logging
        assertEquals("SUV", CarType.SUV.toString());
        assertEquals("COMPACT", CarType.COMPACT.toString());
    }

    @Test
    public void testEnumEquality() {
        // Confirms that Enum constants work with standard equality checks
        CarType type1 = CarType.SUV;
        CarType type2 = CarType.SUV;
        CarType type3 = CarType.COMPACT;

        assertSame(type1, type2, "Same enum constants must be referentially equal.");
        assertNotSame(type1, type3, "Different enum constants must not be equal.");
    }
}