/**
 * File: RegisterCustomerCommand.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking1.controller.commands;

import edu.du.ict4315.parking1.domain.model.classes.Customer;
import java.util.Map;

/**
 * Command to register a new customer in the parking system
 * This utilizes the ParkingService to maintain consistency across the office
 */
public class RegisterCustomerCommand implements Command {
private final ParkingOffice office;
    private final String id;
    private final String name;
    private final String address;

    public RegisterCustomerCommand(ParkingOffice office, String id, String name, String address) {
        this.office = office;
        this.id = id;
        this.name = name;
        this.address = address;
    }

    /**
     * Executes the customer registration logic.
     * Resolves error: "cannot find symbol: method registerCustomer"
     * @param data
     * @return 
     */
@Override
    public String execute(Map<String, String> data) {
        if (id == null || name == null) {
            return "Registration Failed: Missing ID or Name.";
        }

        // 1. Create the real Customer instance
        Customer customer = new Customer(id, name, address);

        // 2. Register with the office
        office.registerCustomer(customer);

        return String.format("Success: Customer %s (%s) has been registered.", name, id);
    }
}
