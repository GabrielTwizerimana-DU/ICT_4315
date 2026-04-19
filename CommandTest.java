
/**
 * File: CommandTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.controller.commands.test;

import edu.university.parking.assignment1.controller.commands.Command;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


/**
 * unit tests for the Command interface contract.
 * Ensures that the Command Pattern structure is sound.
 */
public class CommandTest {

    @Test
    public void testCommandContract() {
        // Create a temporary implementation to test the interface structure
        Command mockCommand = new Command() {
            @Override
            public String getCommandName() {
                return "TEST_CMD";
            }

            public String getDisplayName() {
                return "Test Command";
            }

            @Override
            public String execute(Properties params) {
                return "Success:" + params.getProperty("input");
            }
        };

        // 1. Verify the Name (Used as the Key in the ParkingService HashMap)
        assertEquals("TEST_CMD", mockCommand.getCommandName());

        // 2. Verify Execution Logic with Properties
        Properties testProps = new Properties();
        testProps.setProperty("input", "Value123");
        
        String result = mockCommand.execute(testProps);
        assertEquals("Success:Value123", result, "The command failed to process input properties.");
    }

    @Test
    public void testCommandWithEmptyProperties() {
        Command simpleCommand = new Command() {
            @Override public String getCommandName() { return "EMPTY"; }
            public String getDisplayName() { return "Empty Test"; }
            @Override public String execute(Properties params) {
                return (params != null) ? "ObjectExists" : "Null";
            }
        };

        // Ensure the interface can handle being passed a fresh Properties object
        assertEquals("ObjectExists", simpleCommand.execute(new Properties()));
    }
}