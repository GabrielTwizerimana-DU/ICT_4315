/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking6.decorator.pattern;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * A concrete decorator that applies a surge multiplier during 
 * high-traffic peak hours.
 */
public class PeakHoursMultiplierCalculator extends ParkingChargeDecorator {
    
    /**
     * @param inner The calculator to wrap
     * @param multiplier The surge rate (e.g., 1.5 for 50% increase)
     */
    public PeakHoursMultiplierCalculator(ParkingChargeCalculator inner, double multiplier) {
        super(inner);
    }

    @Override
   public Money calculateCharge(Duration stayDuration) {
        // 1. Delegate to the inner calculator to get the base Money object
        Money currentTotal = super.calculateCharge(stayDuration);
        
        // 2. Check for peak hours
        if (isPeakHour()) {
            // 3. Multiply the Money amount (Assuming Money has a multiply method)
            return currentTotal.add(currentTotal);
        }
        
        return currentTotal;
    }

    /**
     * Logic to determine peak hour status.
     * Peak: 7am-9am and 4pm-6pm.
     */
    private boolean isPeakHour() {
        int hour = LocalDateTime.now().getHour();
        return (hour >= 7 && hour <= 9) || (hour >= 16 && hour <= 18);
    }
}