
/**
 * File: ParkingChargeStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking3.strategies;

import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.Money;

/**
 * The Strategy interface for calculating parking fees.
 * This is the "Product" interface in the Factory Pattern.
 */
public interface ParkingChargeStrategy {
    
    
    /**
     * Calculates the parking fee for a specific permit.
     * 
     * @param permit The permit associated with the vehicle.
     * @return A Money object representing the calculated cost.
     */
    Money calculateFee(ParkingPermit permit);
    
}
