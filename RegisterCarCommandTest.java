
/**
 * File: RegisterCarCommandTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking8.shared.test;

import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import edu.du.ict4315.parking8.shared.RegisterCarCommand;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterCarCommandTest {

    private final Gson gson = new Gson();
    private ParkingOffice fakeOffice;
    private ParkingObserver dummyObserver;

    @BeforeEach
    public void setUp() {
        // Instantiate a quick inline observer lambda to satisfy the updated ParkingOffice constructor
        dummyObserver = event -> { /* no-op test spy */ };
        fakeOffice = new ParkingOffice("TEST_OFFICE_01", dummyObserver);
    }

    @Test
    public void testCommandNetworkSerializationLifecycle() {
        // Arrange - Create a command from the client's perspective (no office reference passed)
        RegisterCarCommand commandOut = new RegisterCarCommand(null, "CUST-101", "ROB-4-CO", CarType.COMPACT);

        // Act - Marshall the object directly down to a JSON string payload
        String jsonOutput = gson.toJson(commandOut);

        // Assert - Verify raw primitives match up, but structural context variables are absent
        assertNotNull(jsonOutput, "Serialized JSON command stream should not be null.");
        assertTrue(jsonOutput.contains("\"customerId\":\"CUST-101\""), "JSON should capture the target customer ID string.");
        assertTrue(jsonOutput.contains("\"licensePlate\":\"ROB-4-CO\""), "JSON should capture the raw vehicle license plate.");
        assertTrue(jsonOutput.contains("\"carType\":\"COMPACT\""), "JSON should cleanly map the target CarType indicator.");
        
        // CRITICAL CHECK: Verify that the transient modifier completely dropped the server context
        assertFalse(jsonOutput.contains("\"office\""), "Stateful manager engines should never cross socket streams.");
    }

    @Test
    public void testServerSideReconstructionAndExecution() {
        // Arrange - Simulate an incoming raw command packet received by the Server line-reader loop
        String incomingJson = "{\"customerId\":\"CUST-101\",\"licensePlate\":\"ROB-4-CO\",\"carType\":\"COMPACT\"}";
        
        // Pre-populate our server-side fake office engine with a valid customer record to satisfy the execution path
        Customer registeredCustomer = new Customer("CUST-101", "Gabriel Twizerimana", "555-0101");
        fakeOffice.registerCustomer(registeredCustomer);

        // Act - Reconstruct the object graph using Gson reflection via the blank default constructor
        RegisterCarCommand commandIn = gson.fromJson(incomingJson, RegisterCarCommand.class);

        // Assert - Confirm data variables mapped seamlessly
        assertNotNull(commandIn);
        assertEquals("CUST-101", commandIn.getCustomerId());
        assertEquals("ROB-4-CO", commandIn.getLicensePlate());
        assertEquals(CarType.COMPACT, commandIn.getCarType());
        assertNull(commandIn.getOffice(), "The internal transient office variable must start out null upon deserialization.");

        // Act - Inject the local server-side data context before triggering polymorphic invocation
        commandIn.setOffice(fakeOffice);

        // Assert - Trigger the execution loop and evaluate structural system changes
        assertNotNull(commandIn.getOffice(), "The command execution context must be populated.");
        String executionResult = commandIn.execute(null);
        
        assertTrue(executionResult.contains("Success"), "Command should finish running with a success signal confirmation.");
        assertTrue(executionResult.contains("P-ROB-4-CO"), "The calculation engine must yield the correct calculated permit id format.");
    }

    @Test
    public void testNoArgConstructorState() {
        // Arrange & Act - Execute blank instance setup used by reflection libraries
        RegisterCarCommand command = new RegisterCarCommand();

        // Assert - Ensure standard default JVM pointer defaults match up
        assertNull(command.getCustomerId());
        assertNull(command.getLicensePlate());
        assertNull(command.getCarType());
        assertNull(command.getOffice());
    }
}