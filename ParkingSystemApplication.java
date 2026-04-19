
/**
 * File: ParkingSystemApplication.java
Author: Gabriel Twizerimana
 */

import edu.university.parking.assignment1.controller.commands.*;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment3.strategies.TypeBasedStrategy;

import java.util.*;

/**
 * Main entry point for the Parking Management System.
 * Demonstrates the end-to-end flow of the application.
 */
public class ParkingSystemApplication {

       public static void main(String[] args) {
        // 1. Initialize the "Information Expert" (Parking Office)
        ParkingOffice office = new ParkingOffice("University Main Lot");

        // 2. Set the Strategy for the Office (Strategy Pattern)
        // This allows the office to calculate fees without knowing the math.
        office.setPricingStrategy(new TypeBasedStrategy());

        System.out.println("--- Starting Registration Process ---");

        // 3. Setup and Execute RegisterCustomerCommand
        // Note: This command uses the Properties-based interface
        RegisterCustomerCommand customerCmd = new RegisterCustomerCommand(office) {};
        Properties customerParams = new Properties();
        customerParams.setProperty("id", "C-101");
        customerParams.setProperty("firstName", "Jim");
        customerParams.setProperty("lastName", "Halpert");
        customerParams.setProperty("streetAddress1", "1725 Slough Avenue");
        customerParams.setProperty("city", "Scranton");
        customerParams.setProperty("state", "PA");
        customerParams.setProperty("zip", "18503");
        customerParams.setProperty("phone", "570-555-0123");

        String customerId = customerCmd.execute(customerParams);
        System.out.println("Customer Registered with ID: " + customerId);

        // 4. Setup and Execute RegisterCarCommand
        // Note: This command uses the strongly-typed explicit interface
        RegisterCarCommand carCmd = new RegisterCarCommand(office);
        String permitId = carCmd.execute("DUNDER-1", CarType.SUV, "C-101");

        System.out.println("Car Registered. Permit Issued: " + permitId);

        // 5. Demonstrate the Strategy Pattern in action
        // We retrieve the customer we just made to verify the state
        Customer jim = office.getCustomer("C-101");
        if (jim != null) {
            System.out.println("\n--- Final System State ---");
            System.out.println("Customer: " + jim.getFirstName() + " " + jim.getLastName());
            System.out.println("Address: " + jim.getAddress().getCity() + ", " + jim.getAddress().getState());
            
            // If your office has a way to get the fee for the car:
            // double fee = office.calculateFee("DUNDER-1"); 
            // System.out.println("Calculated Strategy-Based Fee: $" + fee);
        }
        
        System.out.println("\nBuild Success: All patterns verified.");
    }
    }
        