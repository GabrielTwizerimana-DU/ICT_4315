
/**
 * File: ParkingServiceTest.java 
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.controller.commands.test;

import edu.university.parking.assignment1.controller.commands.ParkingOffice;
import edu.university.parking.assignment1.controller.commands.ParkingService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingServiceTest {
    @Test
    public void testPerformCommandCustomer() {
        ParkingOffice office = new ParkingOffice("HQ", null);
        ParkingService service = new ParkingService(office);
        
        String[] args = {"firstName=Bob", "lastName=Smith", "id=C100"};
        String result = service.performCommand("CUSTOMER", args);
        
        // Check if the customer was actually added to the office
        assertEquals("C100", result);
        assertEquals(1, office.getListOfCustomers().size());
        assertEquals("Bob", office.getListOfCustomers().get(0).getFirstName());
    }
}