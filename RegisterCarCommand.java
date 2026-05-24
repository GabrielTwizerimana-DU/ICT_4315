
/**
 * File: RegisterCarCommand.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking8.shared;

import edu.du.ict4315.parking1.controller.commands.Command;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import java.util.Map;

/**
 * Concrete Command to register a car
 * Refactored for direct polymorphic serialization over a TCP socket stream
 */
public class RegisterCarCommand implements Command {

    // Prevent the server-side runtime manager from being serialized over the socket
    private transient ParkingOffice office;
    
    private String customerId;
    private String licensePlate;
    private CarType carType; // Assumes CarType is an enum or an easily serializable DTO/Value Object

    // 1. Explicit no-argument constructor required by Gson reflection engine
    public RegisterCarCommand() {
    }

    // 2. Full constructor utilized by the client application layer before transport
    public RegisterCarCommand(ParkingOffice office, String customerId, String licensePlate, CarType carType) {
        this.office = office;
        this.customerId = customerId;
        this.licensePlate = licensePlate;
        this.carType = carType;
    }

    // Server-side setter to inject local execution context after deserialization
    public void setOffice(ParkingOffice office) {
        this.office = office;
    }

    public ParkingOffice getOffice() {
        return office;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public CarType getCarType() {
        return carType;
    }

    @Override
    public String execute(Map<String, String> data) {
        // Enforce safety checks against uninjected server context
        if (this.office == null) {
            return "Error: Internal Server Execution Context (ParkingOffice) is missing.";
        }
        if (this.customerId == null || this.licensePlate == null) {
            return "Error: Missing registration credentials (customerId or licensePlate).";
        }

        // 1. Retrieve the customer from the office
        Customer customer = office.getCustomer(customerId);
        if (customer == null) {
            return "Error: Customer " + customerId + " not found.";
        }

        // 2. Create the domain objects
        Car car = new Car(licensePlate, carType, customer);

        String permitId = "P-" + licensePlate;
        ParkingPermit permit = new ParkingPermit(permitId, car);

        // 3. Link objects and register
        customer.addPermit(permit);
        office.registerPermit(permit);

        return String.format("Success: Car %s registered for %s. Permit: %s",
                licensePlate, customer.getName(), permitId);
    }
}