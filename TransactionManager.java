/**
 * File: TransactionManager.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking1.management.layers;

import edu.du.ict4315.parking1.controller.commands.ParkingLot;
import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.controller.commands.ParkingTransaction;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking3.strategies.ParkingChargeStrategy;
import edu.du.ict4315.parking3.strategies.TypeBasedStrategy;
import edu.du.ict4315.parking5.observer.pattern.ParkingAction;
import edu.du.ict4315.parking5.observer.pattern.ParkingEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;

/**
 * The Observer in the pattern. Manages the lifecycle of parking transactions
 * based on events received from ParkingLots
 */
  

    public class TransactionManager implements ParkingObserver {

        private final Map<String, ParkingTransaction> activeTransactions = new HashMap<>();
        private final List<ParkingTransaction> completedTransactions = new ArrayList<>();
        private final ParkingChargeStrategy strategy;
        
        public TransactionManager() {
        // Initializes with a default strategy
        this.strategy = new TypeBasedStrategy(); 
    }

        /**
         * Implementation of the Observer interface. Resolves error: "cannot
         * find symbol: method getEventType()"
         */
        @Override
        public void update(ParkingEvent event) {
            ParkingAction action = event.getEventType();
            ParkingLot lot = event.getLot();
            ParkingPermit permit = event.getPermit();

            if (action == ParkingAction.ENTER) {
                handleEntry(lot, permit);
            } else if (action == ParkingAction.EXIT) {
                handleExit(lot, permit);
            }
        }

        private void handleEntry(ParkingLot lot, ParkingPermit permit) {
            ParkingTransaction transaction = new ParkingTransaction(permit, lot, java.time.LocalDateTime.now());
            activeTransactions.put(permit.getId(), transaction);
            System.out.println("Transaction started for permit: " + permit.getId());
        }

        private void handleExit(ParkingLot lot, ParkingPermit permit) {
            ParkingTransaction transaction = activeTransactions.remove(permit.getId());
            if (transaction != null) {
                transaction.setExitTime(java.time.LocalDateTime.now());
                
                Money calculatedFee = strategy.calculateFee(transaction);

                if (calculatedFee == null) {
                    transaction.setFee(new Money(0.0)); // Fallback
                } else {
                    transaction.setFee(calculatedFee);
                }

            }

            completedTransactions.add(transaction);
            activeTransactions.remove(permit.getId());
            System.out.println("Transaction completed for permit: " + permit.getId());
            System.out.println("Exit processed for lot: " + lot.getName());
        }
    

    public ParkingTransaction getActiveTransaction(String permitId) {
        return activeTransactions.get(permitId);
    }

    public List<ParkingTransaction> getCompletedTransactions() {
        return new ArrayList<>(completedTransactions);
    }
    }
