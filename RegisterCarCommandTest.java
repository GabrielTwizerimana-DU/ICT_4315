
/**
 * File: RegisterCarCommand Test.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.controller.commands.test;


import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.controller.commands.ParkingOffice;
import edu.university.parking.assignment1.controller.commands.RegisterCarCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RegisterCarCommand.
 * verifies car registration and association with existing customers.
 */

public class RegisterCarCommandTest {

    private ParkingOffice parkingOffice;
    private RegisterCarCommand command;
    private Customer testCustomer;

    @BeforeEach
    public void setUp() {
        parkingOffice = new ParkingOffice("Main Office", null);
        command = new RegisterCarCommand(parkingOffice);

        // Pre-register a customer so the car has an owner to link to
        testCustomer = new Customer("Alice", "Smith", "C-101", null, "555-1234");
        parkingOffice.register(testCustomer);
    }

    @Test
    public void testGetCommandName() {
        assertEquals("CAR", command.getCommandName());
    }

    @Test
    public void testExecuteSuccessfulCarRegistration() {
        // 1. Prepare properties for a COMPACT car
        Properties props = new Properties();
        props.setProperty("licensePlate", "COMPACT-1");
        props.setProperty("type", "COMPACT");
        props.setProperty("ownerId", "C-101");

        // 2. Execute
        String result = command.execute(props);

        // 3. Assertions
        // The result should be a Permit ID (e.g., "PERMIT-COMPACT-1")
        assertNotNull(result);
        assertTrue(result.contains("COMPACT-1"), "Result should contain the license plate");
        
        // Verify the car is actually known to the office now
        assertNotNull(parkingOffice.getPermitForCar("COMPACT-1"), "Office should have a permit for this car");
    }

    @Test
    public void testExecuteWithInvalidCustomer() {
        Properties props = new Properties();
        props.setProperty("licensePlate", "GHOST-1");
        props.setProperty("type", "SUV");
        props.setProperty("ownerId", "NON-EXISTENT-ID");

        String result = command.execute(props);

        // Should return an error message rather than crashing
        assertTrue(result.contains("Error"), "Should return error for invalid customer ID");
    }

    @Test
    public void testExecuteWithInvalidCarType() {
        Properties props = new Properties();
        props.setProperty("licensePlate", "ERROR-1");
        props.setProperty("type", "BICYCLE"); // Not a valid CarType enum
        props.setProperty("ownerId", "C-101");

        String result = command.execute(props);

        assertTrue(result.contains("Error"), "Should return error for invalid car type");
    }

    @Test
    public void testExecuteWithNullProperties() {
        String result = command.execute(null);
        assertTrue(result.contains("Error"), "Should handle null properties gracefully");
    }
}