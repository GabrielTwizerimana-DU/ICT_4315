
/**
 * File: WeekdayWeekendStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies;

import edu.du.ict4315.parking1.controller.commands.ParkingTransaction;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * Strategy that applies different flat rates based on whether the
 * parking event started on a weekday or a weekend.
 */
public class WeekdayWeekendStrategy implements ParkingChargeStrategy {

    private static final double WEEKDAY_RATE = 20.0;
    private static final double WEEKEND_RATE = 10.0;

    /**
     * Calculates the fee by checking the DayOfWeek of the entry time.
     * @param transaction The expert object containing stay details.
     * @return A Money object representing the calculated fee.
     */
    @Override
    public Money calculateFee(ParkingTransaction transaction) {
        if (transaction == null || transaction.getEntryTime() == null) {
            return new Money(0.0);
        }

        // Information Expert: Transaction provides the 'when'
        LocalDateTime entryTime = transaction.getEntryTime();
        DayOfWeek day = entryTime.getDayOfWeek();

        double amount;

        // Check for weekend days
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            amount = WEEKEND_RATE;
        } else {
            amount = WEEKDAY_RATE;
        }

        return new Money(amount);
    }
}