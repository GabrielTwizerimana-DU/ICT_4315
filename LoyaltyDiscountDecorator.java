
/**
 * File: LoyaltyDiscountDecorator.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.Duration;

/**
 * Concrete Decorator that applies a percentage-based loyalty discount
 */
public class LoyaltyDiscountDecorator extends ParkingChargeDecorator{
    private final double discountRate; // e.g., 0.90 for a 10% discount

    public LoyaltyDiscountDecorator(ParkingChargeCalculator calculator, double discountRate) {
        super(calculator);
        this.discountRate = discountRate;
    }

    @Override
    public Money calculateCharge(Duration stayDuration) {
        Money currentAmount = super.calculateCharge(stayDuration);
        return new Money(currentAmount.getAmount() * discountRate);
    }
    
}
