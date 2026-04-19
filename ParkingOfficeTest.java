
/**
 * File: ParkingOfficeTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.controller.commands.test;

import edu.university.parking.assignment1.controller.commands.Customer;
import edu.university.parking.assignment1.controller.commands.ParkingOffice;
import edu.university.parking.assignment1.domain.model.classes.Car;
import edu.university.parking.assignment1.domain.model.classes.CarType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingOfficeTest {

    @Test
    public void testRegisterAndFindPermit() {
        ParkingOffice office = new ParkingOffice("HQ", null);
        Customer customer = new Customer("John", "Doe", "C1", null, "555");
        Car car = new Car("ABC-123", CarType.SUV, customer);

        office.register(customer);
        String permitId = office.register(car);

        assertNotNull(permitId);
        assertNotNull(office.getPermitForCar("ABC-123"));
        assertEquals(1, office.getListOfCustomers().size());
    }
}
