
/**
 * File: VehicleSizeSurchargeDecorator.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.Duration;

/**
 * Concrete Decorator for size-based surcharges (e.g., SUVs)
 */
public class VehicleSizeSurchargeDecorator extends ParkingChargeDecorator {
    private final double multiplier;

    public VehicleSizeSurchargeDecorator(ParkingChargeCalculator calculator, double multiplier) {
        super(calculator);
        this.multiplier = multiplier;
    }

    @Override
    public Money calculateCharge(Duration stayDuration) {
        Money baseAmount = super.calculateCharge(stayDuration);
        return new Money(baseAmount.getAmount() * multiplier);
    }
}
