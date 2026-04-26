
/**
 * File: ParkingLot.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.controller.commands;

/**
 * Represents a parking lot that holds a reference to a specific 
 * charging strategy identifier used by the Factory.
 */
public class ParkingLot {

    private String lotId;
    private String address;
    private String strategyType; // Key used by the Strategy Factory

    /**
     * @param lotId Unique identifier for the lot.
     * @param strategyType The name of the strategy (e.g., "WEEKDAY_PRIME")
     */
    public ParkingLot(String lotId, String strategyType) {
        this.lotId = lotId;
        this.strategyType = strategyType;
    }

    // FIX: Must be public so tests and TransactionManager can access it
    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This is the "hook" for the Factory Pattern.
     * @return The string name of the strategy to be used.
     */
    public String getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType;
    }

    @Override
    public String toString() {
        return "ParkingLot{" +
                "lotId='" + lotId + '\'' +
                ", strategyType='" + strategyType + '\'' +
                '}';
    }
}
