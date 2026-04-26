
/**
 * File: WeekdayWeekendStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies;

import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * A concrete strategy that applies different rates based on 
 * whether it is a weekday or a weekend.
 */
public class WeekdayWeekendStrategy implements ParkingChargeStrategy{
/**
     * Calculates the fee based on the current day of the week.
     * Weekend (Sat/Sun) usually has a flat discount or specific rate.
     * 
     * @param permit The permit providing vehicle details.
     * @return A Money object with the calculated amount.
     */
    @Override
    public Money calculateFee(ParkingPermit permit) {
        // Get the current day of the week
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        
        boolean isWeekend = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);
        double amount;

        if (isWeekend) {
            // Flat weekend rate regardless of CarType
            amount = 5.00; 
        } else {
            // Standard weekday rates based on CarType
            CarType type = permit.getCar().getType();
            amount = switch (type) {
                case COMPACT -> 10.00;
                case SUV -> 15.00;
                default -> 12.00;
            };
        }

        return new Money(amount, "USD");
    }
}
