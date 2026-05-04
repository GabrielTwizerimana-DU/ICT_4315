/**
 * File: ParkingLot.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking1.controller.commands;

import edu.du.ict4315.parking5.observer.pattern.ParkingAction;
import edu.du.ict4315.parking5.observer.pattern.ParkingEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;

/**
 * The Subject in the Observer pattern. Broadcasts entry and exit events to
 * registered ParkingAction observers.
 */
public class ParkingLot {

    private final String id;
    private final String name;
    private final int capacity;
    private final List<ParkingObserver> observers = new ArrayList<>();

    public ParkingLot(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public void addObserver(ParkingObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(ParkingObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers of a parking event.
     *
     * @param event
     */
    public void notifyObservers(ParkingEvent event) {
        for (ParkingObserver observer : observers) {
            observer.update(event);
        }
    }

    public void enter(ParkingPermit permit) {
        // Domain logic for entry...

        // Create the event with (Lot, Permit, Action) to match the constructor
        ParkingEvent event = new ParkingEvent(this, permit, ParkingAction.ENTER);
        notifyObservers(event);
    }

    public void exit(ParkingPermit permit) {
        // Domain logic for exit...

        ParkingEvent event = new ParkingEvent(this, permit, ParkingAction.EXIT);
        notifyObservers(event);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<ParkingObserver> getObservers() {
        return observers;
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }

}
