
/**
 * File: RegisterCarCommandTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.controller.commands.test;

import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.controller.commands.ParkingOffice;
import edu.university.parking.assignment1.controller.commands.RegisterCarCommand;
import edu.university.parking.assignment1.domain.model.classes.Address;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RegisterCarCommand.
 * verifies car registration and association with existing customers.
 */

public class RegisterCarCommandTest {

    private ParkingOffice office;
    private Customer testCustomer;

    @BeforeEach
    public void setUp() {
        // Initialize the office with a name and a physical address
        Address officeAddr = new Address("100 University Ave", "", "College Town", "ST", "12345");
        office = new ParkingOffice("Main Campus Office", officeAddr);
        
        // Register a customer so we have a valid Information Expert to link the car to
        testCustomer = new Customer("C-101", "Jim", "Halpert", officeAddr, "555-1212");
        office.register(testCustomer);
    }

    /**
     * Requirement: Success Path
     * Verifies that the command correctly orchestrates car registration and returns a Permit ID.
     */
    @Test
    public void testExecuteRegistration() {
        // Arrange: Prepare the command and data
        String licensePlate = "SCRANTON-1";
        CarType type = CarType.COMPACT;
        String customerId = "C-101";
        RegisterCarCommand command = new RegisterCarCommand(office);
        
        // Act: Execute the command
        String permitId = command.execute(licensePlate, type, customerId);

        // Assert: Verify the delegation chain returned a valid state
        assertNotNull(permitId, "Command should return a valid Permit ID string.");
        
        // Verify the Facade can retrieve the result of the command
        ParkingPermit permit = office.getPermitForCar(licensePlate);
        assertNotNull(permit, "Permit should be persisted in the office registry.");
        assertEquals(licensePlate, permit.getCar().getLicensePlate(), "License plate mismatch.");
        assertEquals(testCustomer.getId(), permit.getCar().getOwner().getId(), "Car owner mismatch.");
    }

    /**
     * Requirement: Error Handling
     * Verifies that the command handles non-existent customers gracefully by returning null.
     */
    @Test
    public void testRegistrationWithInvalidCustomer() {
        // Arrange
        RegisterCarCommand command = new RegisterCarCommand(office);
        
        // Act: Attempt to register a car to a non-existent customer ID
        String result = command.execute("GHOST-99", CarType.SUV, "NON-EXISTENT-ID");
        
        // Assert: The command should fail gracefully
        assertNull(result, "Command should return null if the customer is not found in the registry.");
    }
}