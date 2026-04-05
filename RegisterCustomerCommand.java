/**
 * File: RegisterCustomerCommand.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.controller.commands;

import edu.university.parking.assignment1.domain.model.classes.Address;
import java.util.Properties;

public class RegisterCustomerCommand implements Command {
    // Variable renamed from 'office' to 'parkingOffice'
    private final ParkingOffice parkingOffice;

    public RegisterCustomerCommand(ParkingOffice parkingOffice) {
        this.parkingOffice = parkingOffice;
    }

    @Override
    public String getCommandName() {
        return "CUSTOMER";
    }

    public String getDisplayName() {
        return "Register Customer";
    }

    @Override
    public String execute(Properties params) {
        String id = params.getProperty("id");
        
        if (id == null || id.isEmpty()) {
            return "Error: Missing Customer ID";
        }

        try {
            Address addr = new Address(
                params.getProperty("streetAddress1"),
                params.getProperty("streetAddress2"),
                params.getProperty("city"),
                params.getProperty("state"),
                params.getProperty("zip")
            );

            Customer customer = new Customer(
                params.getProperty("firstName"),
                params.getProperty("lastName"),
                id,
                addr,
                params.getProperty("phone")
            );

            // Using the renamed variable to register the customer
            parkingOffice.register(customer);
            
            return id;

        } catch (Exception e) {
            return "Error: Registration failed - " + e.getMessage();
        }
    }
}
