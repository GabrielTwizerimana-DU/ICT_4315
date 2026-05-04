
/**
 * File: ParkingTransaction.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.controller.commands;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import java.time.LocalDateTime;

/**
 * Encapsulates the data for a single parking stay.
 * Acts as the Data Holder for the TransactionManager.
 */
public class ParkingTransaction {
    private final ParkingPermit permit;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Money fee; 
    private final ParkingLot lot;

    /**
     * Initialize a transaction at the moment of entry.
     * Matches the call: new ParkingTransaction(permit, entryTime)
     * @param permit
     * @param entryTime
     * @param lot
     */
   

    public ParkingTransaction(ParkingPermit permit, ParkingLot lot, LocalDateTime entryTime) {
        this.permit = permit;
        this.entryTime = entryTime;
        this.fee = new Money(0.0);
        this.lot = lot;
        
    }

    public ParkingPermit getPermit() {
        return permit;
    }

    public LocalDateTime getEntryTime() { 
        return entryTime; 
    }

    public LocalDateTime getExitTime() { 
        return exitTime; 
    }

    public ParkingLot getLot() {
        return lot;
    }
    
    
    
    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public Money getFee() { 
        return fee; 
    }
   
    public void setFee(Money fee) {
        this.fee = fee;
    }

    @Override
public String toString() {
    return String.format("Transaction[Permit=%s, Lot=%s, EntryTime=%s, Fee=%s]",
        permit.getId(),
        lot.getName(), 
        entryTime.toLocalTime().toString(), // Prints just the time for readability
        fee.getAmount() 
    );
}

   
   
}
