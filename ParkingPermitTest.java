
/**
 * File: ParkingPermitTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.controller.commands.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;


/**
 * Unit tests for the ParkingPermit class.
 * Ensures the permit correctly associates with a Car and tracks expiration.
 */

public class ParkingPermitTest {
private Car suv;
private Customer owner;

    @BeforeEach
    public void setUp() {
        owner = new Customer("C-123", "Alice Smith", "123 Maple St", "555-0123");
        suv = new Car("SUV-2026", CarType.SUV, owner);
    }

    @Test
    public void testPermitInitialization() {
        String customId = "PERMIT-001";
        // Matching your 2-argument constructor
        ParkingPermit permit = new ParkingPermit(customId, suv);

        assertEquals(customId, permit.getId(), "Permit should store the provided ID.");
        assertEquals(suv, permit.getCar(), "Permit should be correctly linked to the car.");
    }

    @Test
    public void testAutomaticIdGeneration() {
        // Testing the UUID constructor
        ParkingPermit permit = new ParkingPermit(suv);

        assertNotNull(permit.getId(), "Permit should automatically generate an ID.");
        assertEquals(8, permit.getId().length(), "Generated ID should be truncated to 8 characters per implementation.");
    }

    @Test
    public void testGetVehicleTypeDelegation() {
        ParkingPermit permit = new ParkingPermit(suv);

        // This is the critical method for the Strategy Pattern
        assertEquals(CarType.SUV, permit.getVehicleType(), 
            "Permit must correctly delegate to the car to return the vehicle type.");
    }
}
