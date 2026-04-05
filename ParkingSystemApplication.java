
/**
 * File: ParkingSystemApplication.java
Author: Gabriel Twizerimana
 */

import edu.university.parking.assignment1.controller.commands.*;
import edu.university.parking.assignment1.domain.model.classes.Address;
import edu.university.parking.assignment1.domain.model.classes.Money;

import java.util.*;

/**
 * Main entry point for the Parking Management System.
 * Demonstrates the end-to-end flow of the application.
 */
public class ParkingSystemApplication {

    public static void main(String[] args) {
        System.out.println("=== Initializing Parking Management System ===");

        // 1. Setup the Core Engine
        Address officeAddr = new Address("100 University Way", "", "Denver", "CO", "80204");
        ParkingOffice parkingOffice = new ParkingOffice("University Central Office", officeAddr);
        
        // 2. Setup a Parking Lot
        // $10.00 base rate (1000 cents)
        ParkingLot northLot = new ParkingLot("North Lot", "200 North St", 1000);
        parkingOffice.addParkingLot(northLot);

        // 3. Register Commands
        Map<String, Command> commands = new HashMap<>();
        commands.put("CUSTOMER", new RegisterCustomerCommand(parkingOffice));
        commands.put("CAR", new RegisterCarCommand(parkingOffice));

        System.out.println("System Ready. Simulating Administrative Actions...\n");

        // --- Step 1: Register a Customer ---
        Properties custParams = new Properties();
        custParams.setProperty("id", "CUST-999");
        custParams.setProperty("firstName", "Jim");
        custParams.setProperty("lastName", "Halpert");
        custParams.setProperty("streetAddress1", "1725 Slough Ave");
        custParams.setProperty("city", "Scranton");
        custParams.setProperty("state", "PA");
        custParams.setProperty("zip", "18503");
        custParams.setProperty("phone", "570-555-1212");

        String custResult = commands.get("CUSTOMER").execute(custParams);
        System.out.println("[Action: CUSTOMER] Result: " + custResult);

        // --- Step 2: Register a Car for that Customer ---
        Properties carParams = new Properties();
        carParams.setProperty("licensePlate", "PMPR-MIF");
        carParams.setProperty("type", "SUV"); // Will trigger SUV pricing
        carParams.setProperty("ownerId", "CUST-999");

        String carResult = commands.get("CAR").execute(carParams);
        System.out.println("[Action: CAR] Result: " + carResult);

        // --- Step 3: Simulate First Parking Event ---
        System.out.println("\n=== Recording First Parking Event ===");
        ParkingPermit permit = parkingOffice.getPermitForCar("PMPR-MIF");
        
        if (permit != null) {
            // First stay in the North Lot
            parkingOffice.park(new Date(), permit, northLot);
            System.out.println(" - Stay 1 recorded in North Lot ($12.00)");
        }

        // --- Step 4: Simulate Second Parking Event (The New Call) ---
        System.out.println("\n=== Recording Second Parking Event ===");
        // Simulate parking again (perhaps later in the day or the next day)
        parkingOffice.park(new Date(), permit, northLot);
        System.out.println(" - Stay 2 recorded in North Lot ($12.00)");

        // --- Step 5: Final Billing Report ---
        System.out.println("\n=== Final Billing Report ===");
        
        // This will now sum BOTH transactions
        Customer jim = parkingOffice.getListOfCustomers().get(0);
        Money totalCharges = parkingOffice.getParkingCharges(jim);
        
        System.out.println("Total Charges for Customer CUST-999: " + totalCharges);
        System.out.println("=== Simulation Complete ===");
}}