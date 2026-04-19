
/**
 * File: ParkingPermit.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.domain.model.classes;

import java.util.UUID;

/**
 *
 * Links a specific Car to an ID and an expiration date.
 */

public class ParkingPermit {

    private final String id;
    private final Car car;

    public ParkingPermit(String id, Car car) {
        this.id = id;
        this.car = car;
    }

    /**
     * Helper constructor to generate a unique ID automatically 
     * if one isn't provided by the registrar.
     * @param car
     */
    public ParkingPermit(Car car) {
        this(UUID.randomUUID().toString().substring(0, 8), car);
    }

    public String getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }
    
   

    /**
     * Convenience method for the Strategy Pattern.
     * Allows algorithms to easily check the vehicle type.
     * @return 
     */
    public CarType getVehicleType() {
        return car.getType();
    }

    @Override
    public String toString() {
        return "Permit ID: " + id + " [" + car.getLicensePlate() + "]";
    }

   
}
