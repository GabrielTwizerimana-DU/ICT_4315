
/**
 * File: ParkingResponseTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking8.server.test;

import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import edu.du.ict4315.parking8.server.ParkingResponse;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingResponseTest {

    private final Gson gson = new Gson();

    @Test
    public void testSuccessResponseLifecycle() {
        // 1. Arrange: Create a success status response packet
        String successMsg = "Success: Customer Gabriel (CUST-99) has been registered.";
        ParkingResponse responseOut = new ParkingResponse(200, successMsg);

        // 2. Act: Marshall object graph into a JSON stream
        String jsonOutput = gson.toJson(responseOut);

        // 3. Assert: Verify stream keys match up
        assertNotNull(jsonOutput, "Serialized JSON response stream should not be null.");
        assertTrue(jsonOutput.contains("\"statusCode\":200"), "JSON should contain the correct success code.");
        assertTrue(jsonOutput.contains("\"message\":\"" + successMsg + "\""), "JSON should match the execution text payload.");

        // 4. Act: Unmarshall text back into a raw response instance
        ParkingResponse responseIn = gson.fromJson(jsonOutput, ParkingResponse.class);

        // 5. Assert: Re-verify structural integrity via getters
        assertNotNull(responseIn);
        assertEquals(200, responseIn.getStatusCode(), "Status code must remain intact post-transmission.");
        assertEquals(successMsg, responseIn.getMessage(), "Message contents must match original transaction outcome.");
    }

    @Test
    public void testFailureResponseLifecycle() {
        // 1. Arrange: Create an error/logic failure boundary state
        String errorMsg = "Error: Customer CUST-101 not found.";
        ParkingResponse responseOut = new ParkingResponse(400, errorMsg);

        // 2. Act: Marshall
        String jsonOutput = gson.toJson(responseOut);

        // 3. Assert
        assertTrue(jsonOutput.contains("\"statusCode\":400"));
        assertTrue(jsonOutput.contains("\"message\":\"" + errorMsg + "\""));

        // 4. Act: Unmarshall
        ParkingResponse responseIn = gson.fromJson(jsonOutput, ParkingResponse.class);

        // 5. Assert
        assertNotNull(responseIn);
        assertEquals(400, responseIn.getStatusCode());
        assertEquals(errorMsg, responseIn.getMessage());
    }

    @Test
    public void testNoArgConstructorState() {
        // Arrange & Act: Trigger empty reflection entry points
        ParkingResponse response = new ParkingResponse();

        // Assert: Ensure clean baseline initialization states
        assertEquals(0, response.getStatusCode(), "Default status code should initialize to 0.");
        assertNull(response.getMessage(), "Default message property should initialize to null.");
    }
}