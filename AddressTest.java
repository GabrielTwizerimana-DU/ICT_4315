
/**
 * File: AddressTest.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking1.domain.model.classes.test;

import edu.du.ict4315.parking1.domain.model.classes.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Address value object.
 * Validates that the location data required for potential region-based
 * parking strategies is correctly stored and formatted.
 */
public class AddressTest {
private Address address;
    private final String STREET = "2199 S University Blvd";
    private final String CITY = "Denver";
    private final String STATE = "CO";
    private final String ZIP = "80208";

    @BeforeEach
    public void setUp() {
        // Matches the constructor: (Street, City, State, Zip)
        address = new Address(STREET, CITY, STATE, ZIP);
    }

    @Test
    public void testGetters() {
        assertEquals(STREET, address.getStreetAddress1());
        assertEquals(CITY, address.getCity());
        assertEquals(STATE, address.getState());
        assertEquals(ZIP, address.getZipCode());
    }

    @Test
    public void testSetters() {
        address.setCity("Aurora");
        address.setZipCode("80012");
        
        assertEquals("Aurora", address.getCity());
        assertEquals("80012", address.getZipCode());
    }

    @Test
    public void testToString() {
        String output = address.toString();
        // Verifies the formatted string: "2199 S University Blvd, Denver, CO 80208"
        assertTrue(output.contains(STREET));
        assertTrue(output.contains(CITY));
        assertTrue(output.contains(ZIP));
    }
}
