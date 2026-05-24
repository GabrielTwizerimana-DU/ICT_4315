
/**
 * File: ParkingRequestTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking8.shared.test;

import edu.du.ict4315.parking8.shared.RegisterCarCommand;
import edu.du.ict4315.parking8.shared.RegisterCustomerCommand;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import edu.du.ict4315.parking8.shared.ParkingRequest;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingRequestTest {

    private final Gson gson = new Gson();
    private ParkingOffice fakeOffice;

    @BeforeEach
    public void setUp() {
        // Instantiate a quick inline observer lambda to satisfy the updated ParkingOffice constructor
        ParkingObserver dummyObserver = event -> { /* no-op test spy */ };
        fakeOffice = new ParkingOffice("TEST_OFFICE_01", dummyObserver);
    }

    @Test
    public void testRegisterCustomerCommandLifecycle() {
        // 1. Arrange: Create the command. The office parameter is passed but is transient
        RegisterCustomerCommand customerCmd = new RegisterCustomerCommand(
            fakeOffice, "CUST-77", "Gabriel Twizerimana", "555-0177"
        );
        ParkingRequest request = new ParkingRequest("REGISTER_CUSTOMER", customerCmd);

        // 2. Act: Serialize the envelope down to a JSON string
        String jsonOutput = gson.toJson(request);

        // 3. Assert: Verify routing tokens and primitives exist, but context is skipped
        assertNotNull(jsonOutput);
        assertTrue(jsonOutput.contains("\"command\":\"REGISTER_CUSTOMER\""));
        assertTrue(jsonOutput.contains("\"id\":\"CUST-77\""));
        assertTrue(jsonOutput.contains("\"name\":\"Gabriel Twizerimana\""));
        assertFalse(jsonOutput.contains("\"office\""), "The transient office variable must be stripped during serialization.");

        // 4. Act: Deserialize back to check structural restoration
        ParkingRequest deserializedRequest = gson.fromJson(jsonOutput, ParkingRequest.class);
        
        // 5. Assert: Envelope fields are successfully restored
        assertNotNull(deserializedRequest);
        assertEquals("REGISTER_CUSTOMER", deserializedRequest.getCommand());
        assertNotNull(deserializedRequest.getPayload());
    }

    @Test
    public void testRegisterCarCommandLifecycle() {
        // 1. Arrange: Create the car registration command
        RegisterCarCommand carCmd = new RegisterCarCommand(
            fakeOffice, "CUST-77", "GAB-2026", CarType.COMPACT
        );
        ParkingRequest request = new ParkingRequest("REGISTER_CAR", carCmd);

        // 2. Act: Serialize
        String jsonOutput = gson.toJson(request);

        // 3. Assert
        assertNotNull(jsonOutput);
        assertTrue(jsonOutput.contains("\"command\":\"REGISTER_CAR\""));
        assertTrue(jsonOutput.contains("\"customerId\":\"CUST-77\""));
        assertTrue(jsonOutput.contains("\"licensePlate\":\"GAB-2026\""));
        assertFalse(jsonOutput.contains("\"office\""), "The office state manager must not leak into the JSON output stream.");

        // 4. Act: Deserialize
        ParkingRequest deserializedRequest = gson.fromJson(jsonOutput, ParkingRequest.class);
        
        // 5. Assert
        assertNotNull(deserializedRequest);
        assertEquals("REGISTER_CAR", deserializedRequest.getCommand());
        assertNotNull(deserializedRequest.getPayload());
    }

    @Test
    public void testNoArgConstructorState() {
        // Arrange & Act
        ParkingRequest request = new ParkingRequest();

        // Assert
        assertNull(request.getCommand(), "Default constructor must initialize command as null.");
        assertNull(request.getPayload(), "Default constructor must initialize payload as null.");
    }
}