
/**
 * File: TypeBasedStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies;

import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Money;


 /**
 * A concrete strategy that calculates parking fees based on the type of car.
 * This is a "Concrete Product" in the Factory Pattern.
 */
public class TypeBasedStrategy implements ParkingChargeStrategy {
/**
     * Calculates the fee by navigating the object graph: 
     * Permit -> Car -> CarType.
     * 
     * @param permit The permit providing access to vehicle details.
     * @return A Money object with the calculated amount.
     */
    @Override
    public Money calculateFee(ParkingPermit permit) {
        // Navigate the object graph to find the car type
        CarType type = permit.getCar().getType();
        
        double amount;

        // Logic based on the CarType enum
        amount = switch (type) {
            case COMPACT -> 10.00;
            case SUV -> 15.00;
            case TRUCK -> 20.00;
            default -> 12.00;
        }; // Default flat rate

        return new Money(amount, "USD");
    }
}
