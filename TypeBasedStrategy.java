
/**
 * File: TypeBasedStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies;

import edu.du.ict4315.parking1.controller.commands.ParkingTransaction;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Money;


/**
 * Concrete implementation of ParkingChargeStrategy.
 * This class acts as a strategy for calculating fees based on vehicle type.
 */
public class TypeBasedStrategy implements ParkingChargeStrategy {

    /**
     * Calculates the fee using the transaction data.
     * @param transaction The expert object containing all stay details.
     * @return A Money object with the calculated amount.
     */
    @Override
    public Money calculateFee(ParkingTransaction transaction) {
        if (transaction == null || transaction.getPermit() == null) {
            return new Money(0.0);
        }

        // Information Expert: Navigating the object graph to find the car type
        CarType type = transaction.getPermit().getCar().getType();
        double amount;

        // Logic based on the CarType enum
        switch (type) {
            case SUV -> amount = 20.0;
            case COMPACT -> amount = 10.0;
            default -> amount = 15.0;
        }

        return new Money(amount);
    }
}
