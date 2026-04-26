
/**
 * File: WeekendDiscountStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies;

import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * A concrete strategy that applies a 50% discount on weekends.
 * This demonstrates a "Decorator-like" logic within the Strategy pattern.
 */
public class WeekendDiscountStrategy implements ParkingChargeStrategy {
    /**
     * Calculates the fee by applying a weekend discount to standard rates.
     * 
     * @param permit The permit providing vehicle details.
     * @return A Money object with the discounted (or standard) amount.
     */
    @Override
    public Money calculateFee(ParkingPermit permit) {
        CarType type = permit.getCar().getType();
        
        // Base Weekday Rates
        double amount = switch (type) {
            case COMPACT -> 10.00;
            case SUV -> 15.00;
            case TRUCK -> 20.00;
            default -> 12.00;
        };

        // Apply 50% discount if it's the weekend
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            amount = amount * 0.5;
        }

        return new Money(amount, "USD");
    }
}
