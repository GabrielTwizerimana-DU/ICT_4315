/**
 * File: PermitManager.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.management.layers;

import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.domain.model.classes.Car;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Manages the issuance and tracking of ParkingPermits.
 * Handles the logic for the "register(Car)" operation.
 */
public class PermitManager {
    
       private final List<ParkingPermit> permits = new ArrayList<>();
  
    // We use a Map for O(1) lookup speed by license plate
    private final Map<String, ParkingPermit> permitsByPlate;

    public PermitManager() {
        this.permitsByPlate = new HashMap<>();
    }

    /**
     * Requirement: Create and store a new permit for a car.
     * * @param car The vehicle being registered.
     * @param car
     * @return The newly created ParkingPermit.
     */
   
    public ParkingPermit register(Car car) {
        
       // 1. Create the permit (Logic for generating a unique ID)
       String permitId = "P-" + UUID.randomUUID().toString().substring(0, 8);
        ParkingPermit newPermit = new ParkingPermit(permitId, car);
        
        // 2. IMPORTANT: Save it to the internal list
        permits.add(newPermit);
        
        // 3. Return the object so the Office can get the ID
        return newPermit;
    }

    /**
     * Helper for the ParkingOffice: Find a permit so we can park the car.
     * * @param licensePlate
     * @param licensePlate
     * @return The ParkingPermit, or null if not found.
     */
    public ParkingPermit getPermitForCar(String licensePlate) {
       return permits.stream()
                .filter(p -> p.getCar().getLicensePlate().equals(licensePlate))
                .findFirst()
                .orElse(null);
    }

    /**
     * Verification check for the Strategy algorithms.
     * @param licensePlate
     * @return 
     */
    public boolean hasValidPermit(String licensePlate) {
        return permitsByPlate.containsKey(licensePlate);
    }
}