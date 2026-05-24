
/**
 * File: verifyServerMemoryRetention.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking8.verifyServerMemoryRetention.test;

import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gabby
 */
public class verifyServerMemoryRetention {
    
    public verifyServerMemoryRetention() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
public void verifyServerMemoryRetention() {
    // 1. Initialize your stateful office context
    ParkingObserver debugObserver = event -> System.out.println(event);
    ParkingOffice office = new ParkingOffice("Main_Office", debugObserver);
    
    // 2. Simulate what the server does when the command object arrives
    Customer customer = new Customer("CUST2", "Rob", "555-0123");
    office.registerCustomer(customer);
    
    // 3. Assert that it is explicitly stored in the office memory space
    assertNotNull(office.getCustomer("CUST2"));
    assertEquals("Rob", office.getCustomer("CUST2").getName());
}
}
