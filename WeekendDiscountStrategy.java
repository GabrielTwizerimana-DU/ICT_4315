
/**
 * File: WeekendDiscountStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies;

import edu.du.ict4315.parking1.controller.commands.ParkingTransaction;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * Strategy that applies a discounted rate for weekend parking.
 * Adheres to the Strategy Pattern and Information Expert principle.
 */
public class WeekendDiscountStrategy implements ParkingChargeStrategy {

    private static final double WEEKEND_RATE = 5.0;  // Discounted rate
    private static final double WEEKDAY_RATE = 15.0; // Standard rate

    /**
     * Calculates the fee based on the day of the week of the entry.
     * @param transaction The expert object containing stay data.
     * @return A Money object representing the calculated fee.
     */
    @Override
    public Money calculateFee(ParkingTransaction transaction) {
        if (transaction == null || transaction.getEntryTime() == null) {
            return new Money(0.0);
        }

        // Information Expert: Transaction provides the entry time
        LocalDateTime entryTime = transaction.getEntryTime();
        DayOfWeek day = entryTime.getDayOfWeek();

        double amount;

        // Apply discount if it's Saturday or Sunday
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            amount = WEEKEND_RATE;
        } else {
            amount = WEEKDAY_RATE;
        }

        return new Money(amount);
    }
}