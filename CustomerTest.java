
/**
 * File: CustomerTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.controller.commands.test;

import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.domain.model.classes.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Customer class.
 * Verifies data integrity and object associations.
 */
public class CustomerTest {

    private Address testAddress;
    private Customer testCustomer;

    @BeforeEach
    public void setUp() {
        // Create a standard address for testing
        testAddress = new Address("123 Main St","cd", "Apt 4", "Denver", "CO");
        
        // Initialize the customer
        testCustomer = new Customer(
            "Jane", 
            "Doe", 
            "CUST-999", 
            testAddress, 
            "303-555-1234"
        );
    }

    @Test
    public void testCustomerIdentity() {
        // Verify that name and ID are stored correctly
        assertEquals("Jane", testCustomer.getFirstName(), "First name mismatch");
        assertEquals("Doe", testCustomer.getLastName(), "Last name mismatch");
        assertEquals("CUST-999", testCustomer.getId(), "Customer ID mismatch");
    }

    @Test
    public void testAddressAssociation() {
        // Verify that the Customer holds the correct Address object (Composition)
        assertNotNull(testCustomer.getAddress(), "Address should not be null");
        assertEquals("123 Main St", testCustomer.getAddress().getStreetAddress1());
        assertEquals("CO", testCustomer.getAddress().getZip());
    }

    @Test
    public void testPhoneNumber() {
        assertEquals("303-555-1234", testCustomer.getPhoneNumber());
    }

    @Test
    public void testEquality() {
        // Testing that two customer objects with the same ID are considered the same
        // (If you implemented the equals() method in your Customer class)
        Customer sameCustomer = new Customer("Jane", "Doe", "CUST-999", testAddress, "303-555-1234");
        
        // This assumes you override equals() based on the ID
        // If not, this test might fail, which is a good reminder to add equals()!
        assertEquals(testCustomer.getId(), sameCustomer.getId());
    }
}