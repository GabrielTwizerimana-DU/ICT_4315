package edu.du.ict4315.parking8.shared;

import edu.du.ict4315.parking1.controller.commands.Command;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import java.util.Map;

/**
 * Command to register a new customer in the parking system.
 * Optimized for direct JSON network serialization over sockets.
 * * File: RegisterCustomerCommand.java
 * Author: Gabriel Twizerimana
 */
public class RegisterCustomerCommand implements Command {
    
    // The transient keyword tells Gson to completely skip this field during serialization
    private transient ParkingOffice office;
    
    private String id;
    private String name;
    private String phoneNumber;

    // 1. Explicit no-argument constructor for Gson reflection on the server side
    public RegisterCustomerCommand() {
    }

    // 2. Full constructor used by the client or factory before transmission
    public RegisterCustomerCommand(ParkingOffice office, String id, String name, String phoneNumber) {
        this.office = office;
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Setter allowing the server to inject its local ParkingOffice context after deserialization
    public void setOffice(ParkingOffice office) {
        this.office = office;
    }

    public ParkingOffice getOffice() {
        return office;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String execute(Map<String, String> data) {
        if (this.office == null) {
            return "Registration Failed: Server execution context (ParkingOffice) missing.";
        }
        if (this.id == null || this.name == null) {
            return "Registration Failed: Missing ID or Name.";
        }

        // 1. Create the real Customer instance
        Customer customer = new Customer(id, name, phoneNumber);

        // 2. Register with the injected office reference
        office.registerCustomer(customer);

        return String.format("Success: Customer %s (%s) has been registered.", name, id);
    }
}