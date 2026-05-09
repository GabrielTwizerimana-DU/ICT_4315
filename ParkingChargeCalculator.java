
/**
 * File: ParkingChargeCalculator.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.Duration;

/**
 * Component interface for the Decorator pattern
 */
public interface ParkingChargeCalculator {
    Money calculateCharge(Duration stayDuration);
    
}
