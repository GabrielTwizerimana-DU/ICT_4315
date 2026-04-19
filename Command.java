
/**
 * File: Command.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.controller.commands;

import java.util.Properties;

public interface Command {

    String getCommandName();

    String execute(Properties params);
}
