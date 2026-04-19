
/**
 * File: ParkingLot.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.controller.commands;

import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.domain.model.classes.Money;
import edu.university.parking.assignment3.strategies.ParkingChargeStrategy;
import java.time.LocalDateTime;

// The ParkingLot no longer calculates money. It simply holds a reference to a strategy.
public class ParkingLot {

   private final String name;
    private final Money baseRate;
    private ParkingChargeStrategy strategy; // The "Strategy" reference

    /**
     * Constructor for Release 2.
     * Note: This replaces the 'UnsupportedOperationException' stub.
     * @param name
     * @param baseRate
     * @param strategy
     */
    public ParkingLot(String name, Money baseRate, ParkingChargeStrategy strategy) {
        this.name = name;
        this.baseRate = baseRate;
        this.strategy = strategy;
    }

    /**
     * The core logic for the Strategy Pattern.
     * Delegates the actual calculation to the current strategy object.
     * @param dateTime
     * @param permit
     * @return 
     */
    public Money getCharge(LocalDateTime dateTime, ParkingPermit permit) {
        if (strategy == null) {
            return baseRate; // Fallback if no strategy is set
        }
        return strategy.calculateCharge(baseRate, dateTime, permit);
    }

    /**
     * Requirement: Ability to change strategies at runtime.
     * This allows a lot to switch from 'Weekday' to 'Special Event' pricing.
     * @param strategy
     */
    public void setStrategy(ParkingChargeStrategy strategy) {
        this.strategy = strategy;
    }

    public String getName() {
        return name;
    }

    public Money getBaseRate() {
        return baseRate;
    }

    public ParkingChargeStrategy getStrategy() {
        return strategy;
    }
}
    
