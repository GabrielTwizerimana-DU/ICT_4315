
/**
 * File: Address.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.domain.model.classes;

/**
 * A Value Object representing a physical address.
 * Required for Customer instantiation in the Command Pattern.
 */
public class Address {
private String streetAddress1;
    private String city;
    private String state;
    private String zipCode;

    /**
     * Constructor for Assignment 4.
     * Order: Street, City, State, Zip.
     * @param streetAddress1
     * @param city
     * @param state
     * @param zipCode
     */
    public Address(String streetAddress1, String city, String state, String zipCode) {
        this.streetAddress1 = streetAddress1;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s %s", streetAddress1, city, state, zipCode);
    }
}
