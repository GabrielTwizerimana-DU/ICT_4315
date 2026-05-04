
/**
 * File: ParkingPermitTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.controller.commands;

import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ParkingPermit using real Car and Customer instances
 */
public class ParkingPermitTest {
    private ParkingPermit permit;
    private Car car;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        // 1. Create the owner (Customer)
        customer = new Customer("C-505", "Bob Jones", "789 Oak St");

        // 2. Create the Car (Fix: Pass the customer to resolve constructor error)
        car = new Car("XYZ-987", CarType.COMPACT, customer);

        // 3. Create the ParkingPermit
        permit = new ParkingPermit("P-202", car);
    }

    @Test
    public void testPermitInitialization() {
        // Verify that the permit ID is correctly assigned
        assertEquals("P-202", permit.getId());
        
        // Verify the association with the real Car object
        assertEquals(car, permit.getCar());
        assertEquals("XYZ-987", permit.getCar().getLicensePlate());
    }

    @Test
    public void testOwnerTraceability() {
        // Verify we can navigate from Permit -> Car -> Customer
        // This confirms the object graph is correctly linked for billing
        assertNotNull(permit.getCar().getOwner());
        assertEquals("Bob Jones", permit.getCar().getOwner().getName());
    }

    @Test
    public void testCarTypeConsistency() {
        // Ensure the permit correctly reports the car's type for strategy logic
        assertEquals(CarType.COMPACT, permit.getCar().getType());
    }
}