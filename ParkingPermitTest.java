
/**
 * File: ParkingPermitTest.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.controller.commands.test;

import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.controller.commands.ParkingPermit;
import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import java.util.Calendar;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ParkingPermit class.
 * Ensures the permit correctly associates with a Car and tracks expiration.
 */

public class ParkingPermitTest {

    private Car testCar;
    private ParkingPermit testPermit;
    private Date expirationDate;

    @BeforeEach
    public void setUp() {
        // Create a dummy customer and car for the permit
        Customer owner = new Customer("Alice", "Brown", "C-500", null, "555-0000");
        testCar = new Car("TEST-123", CarType.SUV, owner);

        // Set expiration to 1 year from now
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        expirationDate = cal.getTime();

        testPermit = new ParkingPermit("PERMIT-001", testCar, expirationDate);
    }

    @Test
    public void testPermitIdentity() {
        // Verify the ID is stored and retrieved correctly
        assertEquals("PERMIT-001", testPermit.getId());
    }

    @Test
    public void testCarAssociation() {
        // Ensure the permit points to the correct Car object
        assertNotNull(testPermit.getCar());
        assertEquals("TEST-123", testPermit.getCar().getLicensePlate());
        assertEquals(CarType.SUV, testPermit.getCar().getType());
    }

    @Test
    public void testExpirationDate() {
        // Verify the expiration date matches what was set
        assertEquals(expirationDate, testPermit.getExpiration());
    }

    @Test
    public void testValidityLogic() {
        // Test a permit that is still valid
        assertTrue(testPermit.getExpiration().after(new Date()), "Permit should be valid");

        // Test an expired permit
        Calendar pastCal = Calendar.getInstance();
        pastCal.add(Calendar.DAY_OF_YEAR, -1); // Yesterday
        ParkingPermit expiredPermit = new ParkingPermit("OLD-1", testCar, pastCal.getTime());

        assertTrue(expiredPermit.getExpiration().before(new Date()), "Permit should be expired");
    }
}
