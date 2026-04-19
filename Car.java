
/**
 * File: Car.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.domain.model.classes;

import edu.university.parking.assignment1.controller.commands.Customer;

public class Car {

    private final String licensePlate;
    private final CarType type;
    private final Customer owner;
    
   

    public Car(String licensePlate, CarType type, Customer owner) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.owner = owner;
    }
    

    public String getLicensePlate() {
        return licensePlate;
    }

    public CarType getType() {
        return type;
    }

    public Customer getOwner() {
        return owner;
    }
    
    

    @Override
    public String toString() {
        return "Car{" + "licensePlate=" + licensePlate + ", type=" + type + ", owner=" + owner + '}';
    }
    
}
