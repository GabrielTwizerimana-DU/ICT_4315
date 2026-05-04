
/**
 * File: Observer.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking5.observer.pattern;

/**
 * A concrete implementation of the ParkingObserver interface
This class can be used as a base for specific observers or as a 
standalone logger for parking events
 */
public class Observer implements ParkingObserver {

    /**
     * Handles the ParkingEvent received from a Subject (ParkingLot)
     * This resolves the "cannot find symbol" error for ParkingEvent 
     * within this class
     */
    @Override
    public void update(ParkingEvent event) {
        if (event == null) {
            return;
        }

        // Accessing event data using the methods we defined
        String lotId = event.getLot().getId();
        String permitId = event.getPermit().getId();
        ParkingAction action = event.getEventType();

        System.out.println(String.format("Observer Notified: Permit %s %sED lot %s at %s",
                permitId, 
                action, 
                lotId, 
                event.getTimestamp()));
    }
}
