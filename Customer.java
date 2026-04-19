/**
 * File: Customer.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.controller.commands;

import edu.university.parking.assignment1.domain.model.classes.Address;
import java.util.Objects;

/**
 * Represents a customer in the parking system.
 */
public class Customer {

    private final String firstName;
    private final String lastName;
    private String id;
    private Address address;
    private String phoneNumber;

    /**
     * Full constructor for the Customer class. Order: First Name, Last Name,
     * ID, Address, Phone
     *
     * @param firstName
     * @param lastName
     * @param id
     * @param address
     * @param phoneNumber
     */
    public Customer( String firstName, String lastName, String id, Address address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
         this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String c101, String alice_Smith, String _Elm_St, String string) {
        this.firstName = null;
        this.lastName = null;
        this.id = null ;
    }

    // --- Getters ---
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id; // Returns the String ID, not the object reference
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // --- Setters (Optional based on your UML) ---
    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Overridden to provide readable identification in logs and tests. This
     * prevents the "expected true but was false" errors in CarTest.
     *
     * @return
     */
    @Override
    public String toString() {
        return "Customer{"
                + "name='" + firstName + " " + lastName + '\''
                + ", id='" + id + '\''
                + '}';
    }

    /**
     * Equals method to help ParkingOffice find specific customers.
     *
     * @param o
     */
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
    if (!(o instanceof Customer)) return false; // More flexible than getClass()
    Customer other = (Customer) o;
    
    // Check if either ID is null to prevent NullPointerException
    if (this.id == null || other.id == null) return false;
    
    // TRIM the IDs just in case there are hidden spaces from a database/input
    return this.id.trim().equalsIgnoreCase(other.id.trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getName() {
       return firstName + "" + lastName;
    }
}
