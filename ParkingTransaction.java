
/**
 * File: ParkingTransaction.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.controller.commands;

import edu.university.parking.assignment1.domain.model.classes.Money;
import java.util.Date;

/**
 * Represents a single parking event record.
 * Captures the date, permit used, location, and final cost.
 */

public class ParkingTransaction {

    private final Date date;
    private final ParkingPermit permit;
    private final ParkingLot parkingLot;
    private final Money chargedAmount;

    /**
     * Constructor for a new transaction. This is typically created by the
     * TransactionManager when the park() method is invoked.
     * @param date
     * @param permit
     * @param parkingLot
     * @param chargedAmount
     */
    public ParkingTransaction(Date date, ParkingPermit permit, ParkingLot parkingLot, Money chargedAmount) {
        this.date = date;
        this.permit = permit;
        this.parkingLot = parkingLot;
        this.chargedAmount = chargedAmount;
    }

    /**
     * Requirement: getChargedAmount()
     *
     * @return Money object representing the cost of this transaction.
     */
    public Money getChargedAmount() {
        return this.chargedAmount;
    }

    /**
     * Requirement: getPermit()
     *
     * @return The ParkingPermit associated with this transaction.
     */
    public ParkingPermit getPermit() {
        return permit;
    }

    // Standard Getters for remaining UML attributes
    public Date getDate() {
        return date;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    /**
     * Helper method to summarize the transaction for billing or logs.
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("Date: %s | Permit: %s | Lot: %s | Charge: %d %s",
                date.toString(),
                permit.getId(),
                parkingLot.getName(),
                chargedAmount.getAmountInCents(),
                chargedAmount.getCurrency());
    }
}
