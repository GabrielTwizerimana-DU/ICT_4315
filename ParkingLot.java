/**
 * File: ParkingLot.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.controller.commands;

import edu.university.parking.assignment1.domain.model.classes.Address;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.domain.model.classes.Money;

public class ParkingLot {

    private final String id;
    private final String name;
    private final int address;

    // Base rate stored as a Money object (e.g., 1000 cents for $10.00)
    private final Money baseDailyRate;

    /**
     * Constructor for ParkingLot.
     *
     * @param id Unique identifier for the lot.
     * @param name Human-readable name (e.g., "North Garage").
     * @param address The physical Address object of the lot.
     */
    public ParkingLot(String id, String name, int address) {
        this.id = id;
        this.name = name;
        this.address = address;
        // Defaulting to a $10.00 base rate for this assignment
        this.baseDailyRate = new Money(1000, "USD");
    }

    /**
     * Requirement: getDailyRate(CarType) : Money Calculates the rate for a
     * specific car. Applies a 20% discount if the CarType is COMPACT.
     *
     * * @param type The CarType (COMPACT or SUV)
     * @param type
     * @return A new Money object representing the final charge.
     */
  
    public Money getDailyRate(CarType type) {
    // Check for SUV to apply the 20% surcharge
    if (type == CarType.SUV) {
        // Apply 20% surcharge (amount * 1.2)
        double surchargedAmount = baseDailyRate.getAmountInCents() * 1.2;

        // Return a new Money object with the increased value ($12.00)
        return new Money((long) surchargedAmount, baseDailyRate.getCurrency());
    }

    // Return the standard base rate ($10.00) for COMPACT or other types
    return baseDailyRate;
}

    // --- Standard UML Getters ---
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAddress() {
        return address;
    }

  
}
