
/**
 * File: ParkingActionTest.java
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that ParkingAction enums are correctly recognized and passed within
 * ParkingEvent payloads.
 */
public class ParkingActionTest {

    private ParkingLot lot;
    private ParkingPermit permit;

    @BeforeEach
    public void setUp() {
        lot = new ParkingLot("L1", "Enum Test Lot", 10);
        Customer customer = new Customer("C-1", "Enum Tester", "123 State St");
        Car car = new Car("ENUM-1", CarType.COMPACT, customer);
        permit = new ParkingPermit("P-ENUM", car);
    }

    @Test
    public void testActionValues() {
        // Ensure the core actions required by TransactionManager exist
        assertNotNull(ParkingAction.valueOf("ENTER"));
        assertNotNull(ParkingAction.valueOf("EXIT"));
    }

    @Test
    public void testEventActionIntegrity() {
        // Create an entry event
        ParkingEvent entryEvent = new ParkingEvent(lot, permit, ParkingAction.ENTER);
        assertEquals(ParkingAction.ENTER, entryEvent.getAction(), "Event should store the ENTER action.");

        // Create an exit event
        ParkingEvent exitEvent = new ParkingEvent(lot, permit, ParkingAction.EXIT);
        assertEquals(ParkingAction.EXIT, exitEvent.getAction(), "Event should store the EXIT action.");
    }

    @Test
    public void testActionComparisonLogic() {
        // This mirrors the logic inside TransactionManager.update()
        ParkingAction action = ParkingAction.ENTER;

        boolean isEntry = (action == ParkingAction.ENTER);
        boolean isExit = (action == ParkingAction.EXIT);

        assertTrue(isEntry);
        assertFalse(isExit);
    }
}
