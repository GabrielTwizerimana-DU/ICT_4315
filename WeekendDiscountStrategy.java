
/**
 * File: WeekendDiscountStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment3.strategies;

import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.domain.model.classes.Money;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class WeekendDiscountStrategy implements ParkingChargeStrategy {
    @Override
    public Money calculateCharge(Money baseRate, LocalDateTime dateTime, ParkingPermit permit) {
        double multiplier = 1.0;

        // Factor 1: Vehicle Type (10% "Eco" discount for Compact cars)
        if (permit.getCar().getType() == CarType.COMPACT) {
            multiplier -= 0.1;
        }

        // Factor 2: Day of Week (50% Flat Weekend Discount)
        DayOfWeek day = dateTime.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            multiplier -= 0.5;
        }

        // Ensure multiplier doesn't drop to zero or negative (sanity check)
        multiplier = Math.max(0.1, multiplier);

        long finalCents = Math.round(baseRate.getAmountInCents() * multiplier);
        return new Money(finalCents, baseRate.getCurrency());
    }
}
