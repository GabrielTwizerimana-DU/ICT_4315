/**
 * File: TransactionManager.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking4.charges.factory;

import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.controller.commands.ParkingTransaction;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking3.strategies.ParkingChargeStrategy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages parking transactions by coordinating with the Strategy Factory.
 * Resolves the "Incompatible Types" error by ensuring the lot is treated as a
 * String and the Factory is used to retrieve the pricing logic.
 */
public class TransactionManager {

    private final List<ParkingTransaction> transactions;
    private final ParkingChargeStrategyFactory strategyFactory;

    public TransactionManager() {
        this.transactions = new ArrayList<>();
        // Initializing the factory ensures it is available for use in park()
        this.strategyFactory = new ParkingChargeStrategyFactory();
    }

    /**
     * Records a new parking event.
     *
     * @param permit The ParkingPermit object.
     * @param parkingLot The name of the lot (passed as a String).
     * @param strategyName The pricing strategy identifier (e.g., "WEEKDAY").
     * @return The created ParkingTransaction.
     */
    public ParkingTransaction park(ParkingPermit permit, String parkingLot, String strategyName) {

        // 1. USE the factory (resolves "never read" warning)
        // Converts the String "WEEKDAY" into a Strategy object
        ParkingChargeStrategy strategy = strategyFactory.getStrategy(strategyName);

        // 2. Calculate the fee using the strategy
        Money fee = strategy.calculateFee(permit);

        // 3. Create the transaction record
        // CRITICAL: Ensure the order matches your ParkingTransaction constructor!
        // Order: (Date, String, Permit, Money)
        ParkingTransaction transaction = new ParkingTransaction(
                LocalDateTime.now(),
                parkingLot,
                permit,
                fee
        );

        // 4. Store and return the result
        transactions.add(transaction);
        return transaction;
    }

    public List<ParkingTransaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
}
