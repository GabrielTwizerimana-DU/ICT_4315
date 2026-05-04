
/**
 * File: CommandTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.controller.commands.test;

import edu.du.ict4315.parking1.controller.commands.Command;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


/**
 * Contract test for the Command interface.
 * Ensures that any implementation follows the Map-based execution pattern
 * required for the Factory and Strategy architecture.
 */
public class CommandTest {

   /**
     * A Dummy implementation of Command to test the interface contract.
     */
    private static class DummyCommand implements Command {
        @Override
        public String execute(Map<String, String> parameters) {
            if (parameters.containsKey("triggerError")) {
                return "Error occurred";
            }
            return "Success: " + parameters.get("data");
        }
    }

    @Test
    public void testCommandInterfaceContract() {
        Command command = new DummyCommand();
        Map<String, String> params = new HashMap<>();
        params.put("data", "JUnit Test");

        String result = command.execute(params);

        // Verify the interface returns a String as expected
        assertNotNull(result);
        assertTrue(result.contains("Success"));
        assertTrue(result.contains("JUnit Test"));
    }

    @Test
    public void testCommandErrorHandlingContract() {
        Command command = new DummyCommand();
        Map<String, String> params = new HashMap<>();
        params.put("triggerError", "true");

        String result = command.execute(params);

        // Verify the interface can communicate failure via the return String
        assertEquals("Error occurred", result);
    }
}