package edu.university.parking.assignment1.controller.commands;

/**
 * File: Command.java 
 * Author: Gabriel Twizerimana
 */
import java.util.*;

public class ParkingService {

    private final Map<String, Command> commands = new HashMap<>();

    public ParkingService(ParkingOffice office) {
        commands.put("CUSTOMER", new RegisterCustomerCommand(office));
        commands.put("CAR", new RegisterCarCommand(office));
    }

    public String performCommand(String name, String[] args) {
        Command cmd = commands.get(name);
        if (cmd == null) {
            return "Error";
        }

        Properties props = new Properties();
        for (String s : args) {
            String[] kv = s.split("=");
            props.setProperty(kv[0], kv[1]);
        }
        return cmd.execute(props);
    }
}
