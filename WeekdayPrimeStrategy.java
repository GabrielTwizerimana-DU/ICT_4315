
/**
 * File: WeekdayPrimeStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies;

import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Money;

/**
 * A concrete strategy for "Prime" weekday parking.
 * Demonstrates the flexibility of the Factory Pattern by allowing 
 * different pricing rules for the same CarType.
 */
public class WeekdayPrimeStrategy implements ParkingChargeStrategy {
    /**
     * Calculates the fee using "Prime" rates.
     * 
     * @param permit The permit providing vehicle details.
     * @return A Money object with the premium calculated amount.
     */
    @Override
    public Money calculateFee(ParkingPermit permit) {
        CarType type = permit.getCar().getType();
        
        // Prime rates are higher than standard TypeBased rates
        double amount = switch (type) {
            case COMPACT -> 15.00;
            case SUV -> 25.00;
            case TRUCK -> 35.00;
            default -> 20.00;
        };

        return new Money(amount, "USD");
    }
}
