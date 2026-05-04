
/**
 * File: ParkingService.java 
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.controller.commands;

import java.util.HashMap;
import java.util.Map;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;

/**
 * Orchestrates the parking system by managing lots and 
ensuring the ParkingObserver pattern is correctly wired between components
 */
public class ParkingService {
    private final Map<String, ParkingLot> lots = new HashMap<>();
    private final ParkingObserver observer;

    /**
     * Initializes the service with a specific ParkingObserver (e.g., TransactionManager).
     * @param observer
     */
    public ParkingService(ParkingObserver observer) {
        this.observer = observer;
    }

    /**
     * Registers a new lot and automatically attaches the observer to it.
     * This resolves the "cannot be converted to ParkingAction" errors.
     * @param lot
     */
    public void registerLot(ParkingLot lot) {
        if (lot != null) {
            lots.put(lot.getId(), lot);
            // Link the Subject (Lot) to the ParkingObserver
            lot.addObserver(observer);
        }
    }

    /**
     * Triggers a vehicle entry event for a specific lot.
     * @param lotId
     * @param permit
     */
    public void processEntry(String lotId, ParkingPermit permit) {
        ParkingLot lot = findLot(lotId);
        if (lot != null) {
            lot.enter(permit); // This will trigger the internal notifyObservers()
        }
    }

    /**
     * Triggers a vehicle exit event for a specific lot.
     * @param lotId
     * @param permit
     */
    public void processExit(String lotId, ParkingPermit permit) {
        ParkingLot lot = findLot(lotId);
        if (lot != null) {
            lot.exit(permit); // This will trigger the internal notifyObservers()
        }
    }

    private ParkingLot findLot(String lotId) {
        ParkingLot lot = lots.get(lotId);
        if (lot == null) {
            System.err.println("Warning: Lot not found with ID: " + lotId);
        }
        return lot;
    }

    public ParkingObserver getObserver() {
        return observer;
    }
}