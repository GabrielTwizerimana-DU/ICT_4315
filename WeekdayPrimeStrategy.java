
/**
 * File: WeekdayPrimeStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment3.strategies;

import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import edu.university.parking.assignment1.domain.model.classes.Money;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class WeekdayPrimeStrategy implements ParkingChargeStrategy {
    @Override
    public Money calculateCharge(Money baseRate, LocalDateTime dateTime, ParkingPermit permit) {
        double multiplier = 1.0;

        // Factor 1: Vehicle Type (20% SUV Surcharge)
        if (permit.getCar().getType() == CarType.SUV) {
            multiplier += 0.2;
        }

        // Factor 2: Time of Day (50% Prime Time Surcharge: Mon-Fri, 8am - 5pm)
        DayOfWeek day = dateTime.getDayOfWeek();
        int hour = dateTime.getHour();
        
        boolean isWeekday = (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY);
        boolean isPrimeTime = (hour >= 8 && hour < 17);

        if (isWeekday && isPrimeTime) {
            multiplier += 0.5;
        }

        long finalCents = Math.round(baseRate.getAmountInCents() * multiplier);
        return new Money(finalCents, baseRate.getCurrency());
    }
}
