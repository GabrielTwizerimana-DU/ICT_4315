/**
 * File: ParkingChargeStrategy.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment3.strategies;

import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.domain.model.classes.Money;
import java.time.LocalDateTime;

public interface ParkingChargeStrategy {
    /**
     * Calculates the parking fee based on multiple factors.
     * * @param baseRate The starting rate defined for the specific lot.
     * @param baseRate
     * @param dateTime The arrival or departure time used for temporal factors.
     * @param permit   The permit used to identify vehicle type and customer data.
     * @return A Money object representing the final calculated fee.
     */
    Money calculateCharge(Money baseRate, LocalDateTime dateTime, ParkingPermit permit);
}
    

