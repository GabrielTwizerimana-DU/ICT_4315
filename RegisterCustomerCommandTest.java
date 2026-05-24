
/**
 * File: RegisterCustomerCommandTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking8.shared.test;

import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import edu.du.ict4315.parking8.shared.RegisterCustomerCommand;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterCustomerCommandTest {

    private final Gson gson = new Gson();
    private ParkingOffice fakeOffice;
    private ParkingObserver dummyObserver;

    @BeforeEach
    public void setUp() {
        // Instantiate a quick inline observer lambda to satisfy the updated ParkingOffice constructor
        dummyObserver = event -> {
            /* no-op test spy */ };
        fakeOffice = new ParkingOffice("TEST_OFFICE_01", dummyObserver);
    }

    @Test
    public void testCommandNetworkSerializationLifecycle() {
        // Arrange - Create command from client perspective (no office object available on client)
        RegisterCustomerCommand commandOut = new RegisterCustomerCommand(null, "CUST-99", "Gabriel", "555-0199");

        // Act - Turn the command itself into the JSON stream payload
        String jsonPayload = gson.toJson(commandOut);

        // Assert - Verify that properties are packed, but the context engine is completely omitted
        assertNotNull(jsonPayload, "Serialized JSON command stream should not be null.");
        assertTrue(jsonPayload.contains("\"id\":\"CUST-99\""), "JSON should capture the target customer ID.");
        assertTrue(jsonPayload.contains("\"name\":\"Gabriel\""), "JSON should capture the customer name.");
        assertTrue(jsonPayload.contains("\"phoneNumber\":\"555-0199\""), "JSON should capture the phone number string.");

        // CRITICAL CHECK: Ensure transient field is skipped entirely
        assertFalse(jsonPayload.contains("\"office\""), "The stateful 'office' context must never leak across the socket network.");
    }

    @Test
    public void testServerSideDeserializationAndContextInjection() {
        // Arrange - Simulate a clean raw payload reaching the Server loop
        String incomingJson = "{\"id\":\"CUST-99\",\"name\":\"Gabriel\",\"phoneNumber\":\"555-0199\"}";

        // Act - Reconstruct the exact command directly using reflection via blank constructor
        RegisterCustomerCommand commandIn = gson.fromJson(incomingJson, RegisterCustomerCommand.class);

        // Assert - Verify data properties restored successfully
        assertNotNull(commandIn);
        assertEquals("CUST-99", commandIn.getId());
        assertEquals("Gabriel", commandIn.getName());
        assertEquals("555-0199", commandIn.getPhoneNumber());
        assertNull(commandIn.getOffice(), "The office field must start out null upon deserialization.");

        // Act - Inject the server's local office context before triggering execution
        commandIn.setOffice(fakeOffice);

        // Assert - The command is fully re-hydrated and runs successfully against domain logic
        assertNotNull(commandIn.getOffice());
        String executionResult = commandIn.execute(null);

        assertTrue(executionResult.contains("Success"), "Command should finish running with a success signal confirmation.");
        assertTrue(executionResult.contains("Gabriel"), "The return string should confirm the registered name.");

        // Secondary verification: Verify domain model state actually mutated inside the office engine
        Customer verifiedCustomer = fakeOffice.getCustomer("CUST-99");
        assertNotNull(verifiedCustomer, "Customer should be saved in the ParkingOffice memory space.");
        assertEquals("Gabriel", verifiedCustomer.getName());
    }

    @Test
    public void testNoArgConstructorState() {
        // Arrange & Act - Execute blank instance setup used by reflection libraries
        RegisterCustomerCommand command = new RegisterCustomerCommand();

        // Assert - Ensure standard default JVM pointer defaults match up
        assertNull(command.getId());
        assertNull(command.getName());
        assertNull(command.getPhoneNumber());
        assertNull(command.getOffice());

    }
}
