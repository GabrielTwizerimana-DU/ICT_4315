
/**
 * File: PermitManagerTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.management.layers.test;

import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.management.layers.PermitManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PermitManager class. Verifies the lifecycle of a parking
 * permit from registration to lookup.
 */
public class PermitManagerTest {

    private PermitManager permitManager;
    private Car testCar;
    private Customer testOwner;

    @BeforeEach
    public void setUp() {
        permitManager = new PermitManager();

        // Setup a car and owner for the permit association
        testOwner = new Customer("Jim", "Halpert", "C-102", "555-1111");
        testCar = new Car("SCRANTON-1", CarType.COMPACT, testOwner);
    }

    @Test
    public void testRegisterCarGeneratesPermit() {
        // Act: Register the car and get the permit
        ParkingPermit permit = permitManager.register(testCar);

        // Assert: Ensure permit is created with correct data
        assertNotNull(permit, "Permit should not be null after registration");
        assertEquals(testCar, permit.getCar(), "Permit must be linked to the correct car");
        assertNotNull(permit.getId(), "Permit must have a unique ID string");
    }


    @Test
    public void testGetPermitByLicensePlate() {
        // Register the car first
        permitManager.register(testCar);

        // Act: Look it up by the license plate string
        ParkingPermit foundPermit = permitManager.getPermitForCar("SCRANTON-1");

        // Assert
        assertNotNull(foundPermit, "Should find the permit using the license plate");
        assertEquals("SCRANTON-1", foundPermit.getCar().getLicensePlate());
    }

    @Test
    public void testGetPermitForUnknownCar() {
        // Attempt to find a permit that hasn't been registered
        ParkingPermit result = permitManager.getPermitForCar("GHOST-PLATE");

        // Should return null (or handle gracefully) rather than crashing
        assertNull(result, "Should return null for an unregistered license plate");
    }
}
