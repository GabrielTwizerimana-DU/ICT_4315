
/**
 * File: HolidaySurchargeDecorator.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.Duration;

/**
 * Concrete Decorator that applies a holiday multiplier.
 */
public class HolidaySurchargeDecorator extends ParkingChargeDecorator {
    private final double holidayMultiplier;

    public HolidaySurchargeDecorator(ParkingChargeCalculator calculator, double holidayMultiplier) {
        super(calculator);
        this.holidayMultiplier = holidayMultiplier;
    }

    @Override
    public Money calculateCharge(Duration stayDuration) {
        Money baseAmount = super.calculateCharge(stayDuration);
        return new Money(baseAmount.getAmount() * holidayMultiplier);
    }
}
