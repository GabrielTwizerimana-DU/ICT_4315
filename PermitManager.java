/**
 * File: PermitManager.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.management.layers;

import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the issuance and tracking of ParkingPermits
 * Ensures each permit is uniquely identified and linked to a vehicle
 */
public class PermitManager {
// Maps Permit ID to the actual Permit object for efficient lookup
    private final Map<String, ParkingPermit> permits;

    public PermitManager() {
        this.permits = new HashMap<>();
    }

    /**
     * Issues a new permit for a specific car.
     * 
     * @param permitId The unique identifier for the new permit.
     * @param car The vehicle associated with this permit.
     * @return The created ParkingPermit object.
     */
    public ParkingPermit registerPermit(String permitId, Car car) {
        if (permitId == null || car == null) {
            throw new IllegalArgumentException("Permit ID and Car cannot be null.");
        }
        
        ParkingPermit newPermit = new ParkingPermit(permitId, car);
        permits.put(permitId, newPermit);
        return newPermit;
    }

    /**
     * Retrieves a permit by its unique ID.
     * Used by the ParkingService to validate permits during entry/exit.
     * 
     * @param permitId The ID to search for.
     * @return The ParkingPermit, or null if not found.
     */
    public ParkingPermit getPermit(String permitId) {
        return permits.get(permitId);
    }

    /**
     * Returns a collection of all issued permits.
     * @return 
     */
    public Map<String, ParkingPermit> getAllPermits() {
        return new HashMap<>(permits);
    }
}