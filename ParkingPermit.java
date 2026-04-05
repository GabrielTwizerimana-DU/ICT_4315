
/**
 * File: ParkingPermit.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.controller.commands;

import edu.university.parking.assignment1.domain.model.classes.Car;
import java.util.Date;

/**
 *
 * Links a specific Car to an ID and an expiration date.
 */

public class ParkingPermit {

    private final String id;
    private final Car car;
    private final Date expiration;

    /**
     * Constructor for a new ParkingPermit.
     *
     * @param id The unique identifier for the permit (e.g., "P-12345").
     * @param car The Car object this permit is assigned to.
     * @param expiration The date the permit ceases to be valid.
     */
    public ParkingPermit(String id,Car car, Date expiration) {
        this.id = id;
        this.car = car;
        this.expiration = expiration;
    }

    /**
     * Requirement: getCar()
     *
     * @return The Car associated with this permit.
     */
    public Car getCar() {
        return car;
    }

    /**
     * Requirement: getId()
     *
     * @return The String ID of the permit.
     */
    public String getId() {
        return id;
    }

    /**
     * Requirement: getExpiration()
     *
     * @return The expiration Date.
     */
    public Date getExpiration() {
        return expiration;
    }

    /**
     * Helper method to check if the permit is still valid. Useful for logic
     * inside the ParkingOffice.park() method.
     *
     * @return true if the current date is before the expiration date.
     */
    public boolean isValid() {
        return new Date().before(expiration);
    }

 
}
