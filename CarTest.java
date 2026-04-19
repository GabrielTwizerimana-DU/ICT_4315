/**
 * File: CarTest.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.domain.model.classes.test;

import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Car class. verifies vehicle data and its association with
 * a Customer.
 */
public class CarTest {

    private Customer testOwner;
    private Car testCar;

    @BeforeEach
    public void setUp() {
        // Create a owner first to associate with the car
        testOwner = new Customer("Jim", "Halpert", "C-102", null, "555-1111");
        testCar = new Car("SCRANTON-1", CarType.COMPACT, testOwner);
    }

    @Test
    public void testCarIdentity() {
        // Verify the license plate and type are stored correctly
        assertEquals("SCRANTON-1", testCar.getLicensePlate());
        assertEquals(CarType.COMPACT, testCar.getType());
    }

    @Test
    public void testOwnerAssociation() {
        // Ensure the car correctly points to its owner
        assertNotNull(testCar.getOwner());
        assertEquals("Jim", testCar.getOwner().getFirstName());
        assertEquals("C-102", testCar.getOwner().getId());
    }

    @Test
    public void testCarTypeEnum() {
        // Verify we can change or set different car types
        Car suvCar = new Car("SUV-99", CarType.SUV, testOwner);
        assertEquals(CarType.SUV, suvCar.getType(), "Should correctly identify as SUV");

        // Ensure it is specifically the Enum type and not just a String
        assertTrue(suvCar.getType() instanceof CarType);
    }

    @Test
    public void testToString() {
        // Verifies the readable output for logs or UI
        String output = testCar.toString();
        assertTrue(output.contains("SCRANTON-1"));
        assertTrue(output.contains("COMPACT"));
    }
}
