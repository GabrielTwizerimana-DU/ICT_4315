
/**
 * File: ParkingChargeStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies;

import edu.du.ict4315.parking1.controller.commands.ParkingTransaction;
import edu.du.ict4315.parking1.domain.model.classes.Money;

/**
 * Strategy interface for calculating parking fees
 * This adheres to the Strategy Pattern, allowing different 
 * calculation algorithms to be swapped at runtime
 */
public interface ParkingChargeStrategy {

    /**
     * Calculates the parking fee for a completed transaction.
     * * @param transaction The expert object containing permit, lot, and time data.
     * @param transaction
     * @return A Money object representing the total charge.
     */
    Money calculateFee(ParkingTransaction transaction);
}
