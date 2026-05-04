
/**
 * File: ParkingObserver.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking5.observer.pattern;

public interface ParkingObserver {
    
    /**
     * Corresponds to notify(e: Event) in the UML diagram
     * @param event
     */
    void update(ParkingEvent event);
    
}
