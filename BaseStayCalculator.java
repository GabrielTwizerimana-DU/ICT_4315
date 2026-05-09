
/**
 * File: BaseStayCalculator.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.Duration;


/**
 * The Concrete Component: Provides the basic hourly rate logic
 */
public class BaseStayCalculator implements ParkingChargeCalculator {
    private final double hourlyRate;

    public BaseStayCalculator(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public Money calculateCharge(Duration stayDuration) {
        double hours = Math.ceil(stayDuration.toMinutes() / 60.0);
        return new Money(hours * hourlyRate);
    }
}

