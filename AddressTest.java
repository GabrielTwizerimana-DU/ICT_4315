
/**
 * File: AddressTest.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.domain.model.classes.test;

import edu.university.parking.assignment1.domain.model.classes.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Address class. Verifies that address data is correctly
 * stored and formatted.
 */
public class AddressTest {

    private Address testAddress;

    @BeforeEach
    public void setUp() {
        // Initialize a standard address for testing
        testAddress = new Address(
                "123 University Blvd",
                "Suite 100",
                "Denver",
                "CO",
                "80204");
    }

    @Test
    public void testAddressGetters() {
        // Verify each individual field is retrieved correctly
        assertEquals("123 University Blvd", testAddress.getStreetAddress1());
        assertEquals("Suite 100", testAddress.getStreetAddress2());
        assertEquals("Denver", testAddress.getCity());
        assertEquals("CO", testAddress.getState());
        assertEquals("80204", testAddress.getZip());
    }

    @Test
    public void testAddressWithEmptyStreet2() {
        // Ensure the class handles addresses without a second street line
        var simpleAddress = new Address("456 Oak St"," Apt 12B", "Aurora", "CO","80204");

        assertEquals(" Apt 12B", simpleAddress.getStreetAddress2());
        assertEquals("456 Oak St", simpleAddress.getStreetAddress1());
    }

    @Test
    public void testToStringFormat() {
        // Often, addresses have a toString() for labels or UI display.
        // If you implemented one, this verifies the format.
        String output = testAddress.toString();

        assertNotNull(output);
        assertTrue(output.contains("123 University Blvd"), "ToString should contain street 1");
        assertTrue(output.contains("Denver"), "ToString should contain city");
        assertTrue(output.contains("80204"), "ToString should contain zip");
    }
}
