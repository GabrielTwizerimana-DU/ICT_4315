/**
 * File: ParkingLotTest.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking1.controller.commands;

import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.management.layers.TransactionManager;
import edu.du.ict4315.parking5.observer.pattern.ParkingEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;

public class ParkingLotTest {

    private ParkingLot lot;
    private TransactionManager manager;
    private ParkingPermit permit;

    @BeforeEach
    public void setUp() {
        lot = new ParkingLot("L1", "Main Lot", 10);
        manager = new TransactionManager();

        // FIX: Use addObserver instead of registerObserver
        lot.addObserver(manager);

        Customer customer = new Customer("C1", "Alice", "123 St");
        Car car = new Car("ABC-123", CarType.COMPACT, customer);
        permit = new ParkingPermit("P1", car);
    }

    @Test
    public void testEnterNotification() {
        // FIX: enter() now only requires the permit
        lot.enter(permit);

        assertNotNull(manager.getActiveTransaction("P1"), "Observer should create transaction on entry");
    }

    @Test
    public void testExitNotification() {
        lot.enter(permit);
        // FIX: exit() now only requires the permit
        lot.exit(permit);

        assertEquals(1, manager.getCompletedTransactions().size(), "Observer should finalize transaction on exit");
    }

    /**
     * Inner class to test custom observation logic. FIX: Implements Observer
     * (Interface), not ParkingAction (Enum).
     */
    private class TestParkingObserver implements ParkingObserver {

        private boolean notified = false;

        @Override
        public void update(ParkingEvent event) {
            this.notified = true;
        }

        public boolean isNotified() {
            return notified;
        }
    }

    @Test
    public void testCustomObserver() {
        TestParkingObserver testObserver = new TestParkingObserver();
        lot.addObserver(testObserver);

        lot.enter(permit);
        assertTrue(testObserver.isNotified(), "Custom observer should be notified of event");
    }
}
