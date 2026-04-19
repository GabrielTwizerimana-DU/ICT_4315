
package edu.university.parking.assignment1.controller.commands.test;

import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.controller.commands.ParkingOffice;
import edu.university.parking.assignment1.controller.commands.RegisterCustomerCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RegisterCustomerCommand class.
 * Verifies the mapping of input properties to the ParkingOffice data store.
 */
public class RegisterCustomerCommandTest {

    private ParkingOffice parkingOffice;
    private RegisterCustomerCommand command;

    @BeforeEach
    public void setUp() {
        // Initialize the receiver (Office) and the command
        parkingOffice = new ParkingOffice("Main Office", null);
        command = new RegisterCustomerCommand(parkingOffice) {};
    }

    @Test
    public void testGetCommandName() {
        assertEquals("CUSTOMER", command.getCommandName(), "Command name must be CUSTOMER");
    }

    @Test
    public void testExecuteSuccessfulRegistration() {
        // 1. Prepare simulated input properties
        Properties props = new Properties();
        props.setProperty("firstName", "John");
        props.setProperty("lastName", "Doe");
        props.setProperty("id", "CUST-001");
        props.setProperty("streetAddress1", "123 Uni Way");
        props.setProperty("city", "Denver");
        props.setProperty("state", "CO");
        props.setProperty("zip", "80204");
        props.setProperty("phone", "555-0101");

        // 2. Execute the command
        String resultId = command.execute(props);

        // 3. Assertions
        assertEquals("CUST-001", resultId, "The command should return the registered Customer ID");
        
        // Verify the customer was actually added to the ParkingOffice
        assertEquals(1, parkingOffice.getListOfCustomers().size(), "Office should have 1 customer registered");
        
        Customer savedCustomer = parkingOffice.getListOfCustomers().get(0);
        assertEquals("John", savedCustomer.getFirstName());
        assertEquals("123 Uni Way", savedCustomer.getAddress().getStreetAddress1());
    }

    @Test
    public void testExecuteWithMissingId() {
        // Test behavior when the mandatory 'id' is missing
        Properties incompleteProps = new Properties();
        incompleteProps.setProperty("firstName", "Jane");
        
        String result = command.execute(incompleteProps);
        
        assertTrue(result.contains("Error"), "Should return an error message when ID is missing");
        assertEquals(0, parkingOffice.getListOfCustomers().size(), "No customer should be registered on failure");
    }

    @Test
    public void testExecuteWithNullProperties() {
        // Safety check for null input
        String result = command.execute(null);
        assertTrue(result.contains("Error"), "Should handle null properties gracefully");
    }
}