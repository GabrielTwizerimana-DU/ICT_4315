
/**
 * File: ParkingOffice.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.controller.commands;

import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.domain.model.classes.Address;
import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.domain.model.classes.Money;
import edu.university.parking.assignment1.management.layers.PermitManager;
import edu.university.parking.assignment1.management.layers.TransactionManager;
import edu.university.parking.assignment3.strategies.TypeBasedStrategy;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The central coordinator for the parking system. Handles registration and
 * delegates to Permit and Transaction managers.
 */
public class ParkingOffice {
    
   public TypeBasedStrategy currentStrategy;

   private final String parkingOfficeName;
   private final List<Customer> listOfCustomers;
   private final List<ParkingLot> listOfParkingLots;

    // Subsystem Managers
    private final PermitManager permitManager;
    private final TransactionManager transactionManager;

    public ParkingOffice(String name, Address address) {
        this.parkingOfficeName = name;
        this.listOfCustomers = new ArrayList<>();
        this.listOfParkingLots = new ArrayList<>();
        this.permitManager = new PermitManager();
        this.transactionManager = new TransactionManager();
    }

    public ParkingOffice(String name) {
        this(name, null); 
    }

    /**
     * Requirement: Record a parking event.
     * Delegates calculation to the ParkingLot Strategy and storage to TransactionManager.
     * @param date
     * @param permit
     * @param lot
     * @return 
     */
    public ParkingTransaction park(Date date, ParkingPermit permit, ParkingLot lot) {
        // 1. Convert legacy java.util.Date to modern LocalDateTime
        // This is required for our Strategy factors (DayOfWeek and Hour)
        LocalDateTime ldt = date.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime();

        // 2. The Hook: Tell the lot to calculate the charge based on its assigned Strategy
        Money finalFee = lot.getCharge(ldt, permit);
        
        // 3. Record the transaction in the ledger
        return transactionManager.park(ldt, permit, lot, finalFee);
    }

    /**
     * Requirement: Aggregates total charges for a customer across all their vehicles.
     * @param customer
     * @return 
     */
    public Money getParkingCharges(Customer customer) {
        long totalCents = 0;

        // Retrieve only transactions belonging to this specific customer
        List<ParkingTransaction> transactions = transactionManager.getTransactions(customer);

        for (ParkingTransaction tx : transactions) {
            totalCents += tx.getChargedAmount().getAmountInCents();
        }

        return new Money(totalCents, "USD");
    }
    
    public String register(Customer customer, String licensePlate, CarType type) {
    // 1. Create the Car object
    Car newCar = new Car(licensePlate, type, customer);
    
    // 2. Register the car (this likely calls your existing logic 
    // that saves the car and returns a permit ID)
    return this.register(newCar); 
}

    /**
     * Registration logic
     * @param customer
     * @return 
     */
    public String register(Customer customer) {
        listOfCustomers.add(customer);
        return customer.getId();
    }

    public String register(Car car) {
        // Delegates to PermitManager to create and store the permit
        ParkingPermit permit = permitManager.register(car);
  
        return (permit != null) ? permit.getId(): null; 
    }

    public Customer getCustomer(String customerId) {
      if (customerId == null) return null;

    return listOfCustomers.stream()
        .filter(c -> c.getId().equals(customerId)) // Ensure this is getId(), not getName()!
        .findFirst()
        .orElse(null);
    }

    public ParkingPermit getPermitForCar(String licensePlate) {
        return permitManager.getPermitForCar(licensePlate);
    }

    // --- Management Methods ---
    public void addParkingLot(ParkingLot lot) {
        this.listOfParkingLots.add(lot);
    }

    public List<ParkingLot> getListOfParkingLots() {
        return new ArrayList<>(listOfParkingLots);
    }

    public String getParkingOfficeName() {
        return parkingOfficeName;
    }

    public List<Customer> getListOfCustomers() {
        return new ArrayList<>(listOfCustomers);
    }

 public void setPricingStrategy(TypeBasedStrategy strategy) {
     
    this.currentStrategy = strategy;
    // Log it so you can see it working in the console
    System.out.println("Pricing strategy updated for office: " + parkingOfficeName);
}

}
