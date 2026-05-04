
/**
 * File: ParkingEventTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking5.observer.pattern.test;

import edu.du.ict4315.parking1.controller.commands.ParkingLot;
import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking5.observer.pattern.ParkingAction;
import edu.du.ict4315.parking5.observer.pattern.ParkingEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that the ParkingEvent (the payload) correctly maintains references
 * to the Subject and the domain data
 */
public class ParkingEventTest {

    private ParkingLot lot;
    private ParkingPermit permit;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        // Initialize real objects (Information Expert)
        lot = new ParkingLot("L-EVENT", "Event Test Lot", 25);
        customer = new Customer("C-EVENT", "Event Tester", "777 Broadcast St");
        Car car = new Car("EVT-123", CarType.SUV, customer);
        permit = new ParkingPermit("P-EVENT", car);
    }

    @Test
    public void testEventConstruction() {
        // Create an ENTER event
        ParkingEvent event = new ParkingEvent(lot, permit, ParkingAction.ENTER);

        // Assertions
        assertNotNull(event);
        assertEquals(lot, event.getLot(), "Event must hold a reference to the triggering lot.");
        assertEquals(permit, event.getPermit(), "Event must carry the correct permit.");
        assertEquals(ParkingAction.ENTER, event.getAction(), "Event must reflect the correct action type.");
    }

    @Test
    public void testDataAccessThroughEvent() {
        // Verifies the Information Expert principle: Can we reach the car type from the event?
        ParkingEvent event = new ParkingEvent(lot, permit, ParkingAction.EXIT);

        String license = event.getPermit().getCar().getLicensePlate();
        CarType type = event.getPermit().getCar().getType();

        assertEquals("EVT-123", license);
        assertEquals(CarType.SUV, type);
    }

    @Test
    public void testEventImmutability() {
        // Ensure the event state is consistent for the Observer
        ParkingEvent event = new ParkingEvent(lot, permit, ParkingAction.ENTER);

        // Check that basic getters return the exact instances provided in constructor
        assertSame(lot, event.getLot());
        assertSame(permit, event.getPermit());
    }
}
