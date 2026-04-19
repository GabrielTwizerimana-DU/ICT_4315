/**
 * File: TransactionManager.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.management.layers;

import edu.university.parking.assignment1.controller.commands.ParkingLot;
import edu.university.parking.assignment1.domain.model.classes.ParkingPermit;
import edu.university.parking.assignment1.controller.commands.ParkingTransaction;
import edu.university.parking.assignment1.domain.model.classes.Money;
import edu.university.parking.assignment1.controller.commands.Customer;
import java.time.LocalDateTime;
import java.util.*;

public class TransactionManager {
    private final List<ParkingTransaction> transactions;

    public TransactionManager() {
        this.transactions = new ArrayList<>();
    }

    /**
     * Requirement: Record a parking event.
     * The fee is passed in as a Money object, having already been 
     * calculated by the ParkingLot's Strategy.
     * @param date
     * @param permit
     * @param lot
     * @param fee
     * @return 
     */
    public ParkingTransaction park(LocalDateTime date, ParkingPermit permit, ParkingLot lot, Money fee) {
        // 1. Create the immutable transaction record
        ParkingTransaction transaction = new ParkingTransaction(date, permit, lot, fee);
        
        // 2. Store it in the internal ledger
        transactions.add(transaction);
        
        return transaction;
    }

   public List<ParkingTransaction> getTransactions(Customer customer) {
    List<ParkingTransaction> filteredTransactions = new ArrayList<>();

    for (ParkingTransaction transaction : this.transactions) {
        // Navigate the object graph to get the owner
        Customer owner = transaction.getPermit().getCar().getOwner();
        
        // Use .equals() which compares IDs, not memory addresses
        if (owner.equals(customer)) {
            filteredTransactions.add(transaction);
        }
    }

    return filteredTransactions;
}

    /**
     * Overloaded version: Returns all transactions in the system.
     * Used in testParkRecordsTransaction.
     * @return 
     */
    public List<ParkingTransaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
}
