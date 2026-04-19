
/**
 * File: ParkingTransaction.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.controller.commands;

import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.domain.model.classes.Money;
import java.time.LocalDateTime;

/**
 * Represents a single parking event record.
 * Captures the date, permit used, location, and final cost.
 */

public class ParkingTransaction {

   private final LocalDateTime transactionDate;
    private final ParkingPermit permit;
    private final ParkingLot parkingLot;
    private final Money chargedAmount;

    // The constructor used in your test

    public ParkingTransaction(LocalDateTime transactionDate, ParkingPermit permit, ParkingLot parkingLot, Money chargedAmount) {
        this.transactionDate = transactionDate;
        this.permit = permit;
        this.parkingLot = parkingLot;
        this.chargedAmount = chargedAmount;
    }
   

    public Money getChargedAmount() {
        return chargedAmount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public ParkingPermit getPermit() {
        return permit;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }
}
