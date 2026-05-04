
/**
 * File: ParkingOfficeTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.controller.commands;

import edu.du.ict4315.parking1.management.layers.TransactionManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ParkingOffice logic
 * Resolves errors: cannot find symbol method addLot and incompatible types Map/List
 */
public class ParkingOfficeTest {
    private ParkingOffice office;
    private TransactionManager manager;
    private ParkingLot lot;

    @BeforeEach
    public void setUp() {
        manager = new TransactionManager();
        // The constructor now takes (String, ParkingObserver)
        office = new ParkingOffice("Main Office", manager);
        lot = new ParkingLot("L1", "South Lot", 100);
    }

    @Test
    public void testRegisterLot() {
        // FIX: Change addLot() to registerLot()
        office.registerLot(lot);
        
        // Verify the lot was added to the internal map
        assertNotNull(office.getLots().get("L1"));
        assertEquals("South Lot", office.getLots().get("L1").getName());
    }

    @Test
    public void testGetLotsAsList() {
        office.registerLot(lot);
        
        // FIX: If your test expects a List but getLots() returns a Map
        // Convert the map values to a new list for the assertion
        List<ParkingLot> lotList = new ArrayList<>(office.getLots().values());
        
        assertFalse(lotList.isEmpty());
        assertEquals(1, lotList.size());
        assertEquals("L1", lotList.get(0).getId());
    }

    @Test
    public void testObserverPropagation() {
        office.registerLot(lot);
        
        // Verify that the office correctly attached the manager to the lot
        // We do this by checking if a parking event in the lot reaches the manager
        // (Assuming you have a way to verify observers in ParkingLot)
        assertTrue(lot.getObservers().contains(manager), 
            "Office should register the TransactionManager as an observer to all new lots.");
    }
}