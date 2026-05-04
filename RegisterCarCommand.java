/**
 * File: RegisterCarCommand.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking1.controller.commands;

import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import java.util.Map;

/**
 * Concrete Command to register a car. This ensures the domain objects required
 * by the Factory (like CarType and Customer) are correctly linked.
 */
public class RegisterCarCommand implements Command {

    private final ParkingOffice office;
    private final String customerId;
    private final String licensePlate;
    private final CarType carType;
  

    public RegisterCarCommand(ParkingOffice office, String customerId, String licensePlate, CarType carType) {
        this.office = office;
        this.customerId = customerId;
        this.licensePlate = licensePlate;
        this.carType = carType;
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

    /**
     * Overrides the Command interface method. Note: Even if 'data' isn't used
     * here, the signature must match.
     *
     * @param data
     * @return
     */
    @Override
    public String execute(Map<String, String> data) {
        // 1. Retrieve the customer from the office
        Customer customer = office.getCustomer(customerId);
        if (customer == null) {
            return "Error: Customer " + customerId + " not found.";
        }

        // 2. Create the domain objects
        Car car = new Car(licensePlate, carType, customer);

        // Ensure your office has a method to generate IDs or use a UUID
        String permitId = "P-" + licensePlate;
        ParkingPermit permit = new ParkingPermit(permitId, car);

        // 3. Link objects
        customer.addPermit(permit);

        // Ensure your ParkingOffice has a registerPermit method
        office.registerPermit(permit);

        return String.format("Success: Car %s registered for %s. Permit: %s",
                licensePlate, customer.getName(), permitId);
    }
}
