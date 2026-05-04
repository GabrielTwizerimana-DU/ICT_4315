/**
 * File: Customer.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking1.domain.model.classes;

import edu.du.ict4315.parking1.controller.commands.ParkingPermit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer who owns vehicles in the parking system
 * Acts as an information expert for owner-based pricing strategies
 */
public class Customer {
private final String id;
    private String name;
    private String phoneNumber;
    private final List<Car> cars = new ArrayList<>();
    private final List<ParkingPermit> permits = new ArrayList<>();

    /**
     * @param id The permanent unique identifier for the customer.
     * @param name The customer's full name.
     * @param phoneNumber The customer's contact number.
     */
    public Customer(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the permanent unique ID of the customer.
     * @return String id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the customer's name.
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the customer's name.
     * @param name The new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the customer's phone number.
     * @return String phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Updates the customer's phone number.
     * @param phoneNumber The new phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void addCar(Car car) {
        if (car != null) {
            this.cars.add(car);
        }
    }
    
    public void addPermit(ParkingPermit permit) {
    if (permit != null) {
        this.permits.add(permit);
    }
}

    public List<Car> getCars() {
        return cars;
    }

    public List<ParkingPermit> getPermits() {
        return permits;
    }
    
 
    
    

    @Override
    public String toString() {
        return String.format("Customer[ID: %s, Name: %s, Phone: %s]", 
                id, name, phoneNumber);
    }
}
