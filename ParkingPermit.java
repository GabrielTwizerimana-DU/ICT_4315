
/**
 * File: ParkingPermit.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking1.controller.commands;

import edu.du.ict4315.parking1.domain.model.classes.Car;

/**
 * Represents a parking permit issued to a specific car.
 * This object is passed to the TransactionManager to record parking events.
 */
public class ParkingPermit {
private final String id;
    private final Car car;
    

    /**
     * @param id The unique identifier for the permit (e.g., "P1").
     * @param car The Car object associated with this permit.
     */
    public ParkingPermit(String id, Car car) {
        this.id = id;
        this.car = car;
    }
    

    /**
     * Returns the unique permit identifier.
     * @return String id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the vehicle associated with this permit.
     * Required by the TransactionManager to resolve the ParkingChargeStrategy via CarType.
     * @return Car object
     */
    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return String.format("Permit[ID: %s, Vehicle: %s]", id, car.getLicensePlate());
    }
}
