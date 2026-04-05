
/**
 * File: RegisterCarCommand.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.controller.commands;

import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import java.util.Properties;

/**
 * Concrete command to handle car registration.
 * Extracts car details from properties and interacts with the ParkingOffice.
 */

public class RegisterCarCommand implements Command {

    private final ParkingOffice office;

    /**
     * Constructor accepts the ParkingOffice to perform the registration.
     *
     * @param office
     */
    public RegisterCarCommand(ParkingOffice office) {
        this.office = office;
    }
    public String register(Car car) {
    // 1. Generate the Permit ID string
    String permitId = "PERMIT-" + car.getLicensePlate();
    
    // 2. Add the car to the office's data structures 
    // (Ensure you have a way to store these, like a List<Car> or Map)
    // this.cars.add(car); 

    // 3. RETURN the permitId so the Command can return it to the Test
    return permitId; 
}

    @Override
    public String getCommandName() {
        return "CAR";
    }

    public String getDisplayName() {
        return "Register Car";
    }

    /**
     * Executes the registration logic. Expected properties: licensePlate, type,
     * ownerId
     *
     * @param params
     * @return
     */
    @Override
    public String execute(Properties params) {
        try {
            String licensePlate = params.getProperty("licensePlate");
            String typeStr = params.getProperty("type");
            String ownerId = params.getProperty("ownerId");

            if (licensePlate == null || typeStr == null || ownerId == null) {
                return "Error: Missing required car parameters.";
            }

            // Find the existing customer in the office
            Customer owner = null;
            for (Customer c : office.getListOfCustomers()) {
                if (c.getId().equals(ownerId)) {
                    owner = c;
                    break;
                }
            }

            if (owner == null) {
                return "Error: Customer ID " + ownerId + " not found.";
            }

            // Convert string type to Enum
            CarType type = CarType.valueOf(typeStr.toUpperCase());

            // Create the Car object
            Car newCar = new Car(licensePlate, type, owner);

            // Register with the office and return the Permit ID
            return office.register(newCar);

        } catch (IllegalArgumentException e) {
            return "Error: Invalid Car Type. Must be COMPACT or SUV.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    
}
