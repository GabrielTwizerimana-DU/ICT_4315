
/**
 * File: ParkingChargeDecorator.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.Duration;

/**
 * The Abstract Decorator
 */
public abstract class ParkingChargeDecorator implements ParkingChargeCalculator {
    protected ParkingChargeCalculator decoratedCalculator;

    public ParkingChargeDecorator(ParkingChargeCalculator calculator) {
        this.decoratedCalculator = calculator;
    }

    @Override
    public Money calculateCharge(Duration stayDuration) {
        return decoratedCalculator.calculateCharge(stayDuration);
    }
}
