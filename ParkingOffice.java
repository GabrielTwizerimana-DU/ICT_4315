
/**
 * File: ParkingOffice.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking1.controller.commands;

import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import java.util.HashMap;
import java.util.Map;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Orchestrates the relationship between lots, customers, and observers Acts as
 * the Information Expert for the parking system state
 */
public class ParkingOffice {
    private final String officeName;
    private final Map<String, ParkingLot> lots = new HashMap<>();
    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    private final Map<String, ParkingPermit> permits = new ConcurrentHashMap<>();
    private final ParkingObserver transactionManager;

    public ParkingOffice(String officeName, ParkingObserver transactionManager) {
        this.officeName = officeName;
        this.transactionManager = transactionManager;
    }

    /**
     * Adds a lot to the office and automatically registers the
     * TransactionManager as an observer to that lot.
     *
     * @param lot
     */
    public void registerLot(ParkingLot lot) {
        if (lot != null) {
            lots.put(lot.getId(), lot);
            // This fixes the "incompatible types" error by ensuring 
            // we pass an ParkingObserver, not a ParkingAction.
            lot.addObserver(transactionManager);
        }
    }

    public ParkingLot getLot(String lotId) {
        return lots.get(lotId);
    }

    public String getOfficeName() {
        return officeName;
    }

    // Method to register a customer
    public synchronized void registerCustomer(Customer customer) {
        if (customer != null && customer.getId() != null) {
            customers.put(customer.getId(), customer);
        }
    }

    // Method to retrieve a customer (used by RegisterCarCommand)
    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }

    /**
     * Registers a permit in the office's global registry.
     *
     * @param permit
     */
    public synchronized void registerPermit(ParkingPermit permit) {
        if (permit != null) {
            permits.put(permit.getId(), permit);
        }
    }

    public Map<String, ParkingLot> getLots() {
        return lots;
    }

    public Map<String, Customer> getCustomers() {
        return customers;
    }

    public Map<String, ParkingPermit> getPermits() {
        return permits;
    }

    public ParkingObserver getTransactionManager() {
        return transactionManager;
    }

    /**
     * Resolves errors where observers were being handled as actions.
     *
     * @param observer
     */
    public void addObserverToAllLots(ParkingObserver observer) {
        for (ParkingLot lot : lots.values()) {
            lot.addObserver(observer);
        }
    }

    public Car findCarByLicense(String licensePlate) {
        // Assuming you store permits in a Map<String, ParkingPermit> or similar
        for (ParkingPermit permit : permits.values()) {
            if (permit.getCar().getLicensePlate().equals(licensePlate)) {
                return permit.getCar();
            }
        }
        return null; // Return null if the car isn't registered
    }

    /**
     * Retrieves a parking permit by its ID.
     *
     * * @param permitId The ID of the permit (e.g., "P-CO-1234")
     * @param permitId
     * @return The ParkingPermit object, or null if not found.
     */
    public ParkingPermit getPermit(String permitId) {
        // Assuming our field is: private Map<String, ParkingPermit> permits;
        return permits.get(permitId);
    }

}
