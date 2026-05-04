
/**
 * File: Command.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.controller.commands;

import java.util.Map;

public interface Command {
// Ensure the method signature matches your classes exactly
    String execute(Map<String, String> parameters);
}
