/**
 * File: ParkingOffice.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.controller.commands;

import edu.university.parking.assignment1.domain.model.classes.Address;
import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.Money;
import edu.university.parking.assignment1.management.layers.PermitManager;
import edu.university.parking.assignment1.management.layers.TransactionManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The central coordinator for the parking system. Handles registration and
 * delegates to Permit and Transaction managers.
 */
public class ParkingOffice {

    private final String parkingOfficeName;
    private final List<Customer> listOfCustomers;
    private final List<ParkingLot> listOfParkingLots;

    // Internal managers (Composition relationship)
    private final PermitManager permitManager;
    private final TransactionManager transactionManager;

    public ParkingOffice(String name, Address address) {
        this.parkingOfficeName = name;
        this.listOfCustomers = new ArrayList<>();
        this.listOfParkingLots = new ArrayList<>();
        this.permitManager = new PermitManager();
        this.transactionManager = new TransactionManager();
    }

    /**
     * Requirement: Register a Customer.
     *
     * @param customer
     * @return The unique ID of the customer.
     */
    public String register(Customer customer) {
        listOfCustomers.add(customer);
        return customer.getId();
    }

    /**
     * Requirement: Register a Car. Delegates to PermitManager to create the
     * actual permit.
     *
     * @param car
     * @return The unique ID of the issued ParkingPermit.
     */
   
    public String register(Car car) {
    // 1. Tell the PermitManager to create and STORE the permit
    // This is the step that makes getPermitForCar() work later!
    ParkingPermit permit = permitManager.register(car);
    
    // 2. Return the ID string so the Command can show it to the user
    return permit.getId(); 
}

    /**
     * Records a parking event.
     *
     * @param date
     * @param permit
     * @param lot
     * @return
     */
  public ParkingTransaction park(Date date, ParkingPermit permit, ParkingLot lot) {
    // 1. Calculate the fee based on CarType (COMPACT vs SUV)
    Money finalFee = lot.getDailyRate(permit.getCar().getType());
    
    // 2. CRITICAL: Record the transaction in the TransactionManager
    // If you skip this line, the history stays empty!
    ParkingTransaction transaction = transactionManager.park(date, permit, lot, finalFee);
    
    return transaction;
}

    /**
     * Retrieves total charges for a customer across all their vehicles.
     *
     * @param customer
     * @return
     */
 public Money getParkingCharges(Customer customer) {
    // 1. Start with zero cents
    long totalCents = 0;

    // 2. Get the list of ALL transactions from the manager
    List<ParkingTransaction> allTransactions = transactionManager.getTransactions();

    // 3. Loop through and add only the ones belonging to this customer
    for (ParkingTransaction tx : allTransactions) {
        if (tx.getPermit().getCar().getOwner().equals(customer)) {
            totalCents += tx.getChargedAmount().getAmountInCents();
        }
    }

    // 4. Return a NEW Money object with the total sum
    return new Money(totalCents, "USD");
}

    /**
     * Helper to find a permit by license plate (delegated to PermitManager).
     *
     * @param licensePlate
     * @return
     */
    public ParkingPermit getPermitForCar(String licensePlate) {
        return (ParkingPermit) permitManager.getPermitForCar(licensePlate);
    }

    // --- Getters and Management Methods ---
    public void addParkingLot(ParkingLot lot) {
        this.listOfParkingLots.add(lot);
    }

    public List<Customer> getListOfCustomers() {
        return new ArrayList<>(listOfCustomers);
    }

    public List<ParkingLot> getListOfParkingLots() {
        return new ArrayList<>(listOfParkingLots);
    }

    public String getParkingOfficeName() {
        return parkingOfficeName;
    }
}
