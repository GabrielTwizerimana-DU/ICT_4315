
/**
 * File: ParkingEvent.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking5.observer.pattern;

import edu.du.ict4315.parking1.controller.commands.ParkingLot;
import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import java.time.LocalDateTime;

/**
 *
 * @author gabby
 */
public class ParkingEvent {
    private final ParkingLot lot;
    private final ParkingPermit permit;
    private final ParkingAction action;
    private final LocalDateTime timestamp;

    /**
     * Matches the call in ParkingLot: new ParkingEvent(this, permit, ParkingAction.ENTER)
     * @param lot
     * @param permit
     * @param action
     */
    public ParkingEvent(ParkingLot lot, ParkingPermit permit, ParkingAction action) {
        this.lot = lot;
        this.permit = permit;
        this.action = action;
        this.timestamp = LocalDateTime.now();
    }

    public ParkingLot getLot() {
        return lot;
    }

    public ParkingPermit getPermit() {
        return permit;
    }

    /**
     * Resolves error: "cannot find symbol: method getEventType()"
     * @return 
     */
    public ParkingAction getEventType() {
        return action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ParkingAction getAction() {
        return action;
    }
    
    
}
