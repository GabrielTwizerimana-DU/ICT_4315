
/**
 * File: WeekdayPrimeStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies;

import edu.du.ict4315.parking1.controller.commands.ParkingTransaction;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * Strategy implementation that charges a premium rate during weekdays.
 */
public class WeekdayPrimeStrategy implements ParkingChargeStrategy {

    private static final double WEEKDAY_RATE = 25.0;
    private static final double WEEKEND_RATE = 15.0;

    /**
     * Calculates the fee based on the day of the week of the entry time.
     * @param transaction The expert object containing stay details.
     * @return A Money object with the calculated amount.
     */
    @Override
    public Money calculateFee(ParkingTransaction transaction) {
        if (transaction == null || transaction.getEntryTime() == null) {
            return new Money(0.0);
        }

        // Information Expert: Accessing the entry time from the transaction
        LocalDateTime entryTime = transaction.getEntryTime();
        DayOfWeek day = entryTime.getDayOfWeek();

        double amount;
        
        // Determine if it is a weekday (Monday through Friday)
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            amount = WEEKEND_RATE;
        } else {
            amount = WEEKDAY_RATE; // Prime weekday rate
        }

        return new Money(amount);
    }
}