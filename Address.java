
/**
 * File: Address.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.domain.model.classes;

/**
 * Represents a physical mailing address. Used by both Customers and the Parking
 * Office.
 */

public class Address {

    private final String streetAddress1;
    private final String streetAddress2;
    private final String city;
    private final String state;
    private final String zip;

    /**
     * Constructor for a complete Address.
     *
     * @param streetAddress1
     * @param streetAddress2
     * @param city
     * @param state
     * @param zip
     */
    public Address(String streetAddress1, String streetAddress2, String city, String state, String zip) {
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    // --- Standard UML Getters ---
    public String getStreetAddress1() {
        return streetAddress1;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    /**
     * Helper method to print the address in a standard format.
     *
     * @return
     */
    @Override
    public String toString() {
        String s2 = (streetAddress2 == null || streetAddress2.isEmpty()) ? "" : streetAddress2 + "\n";
        return String.format("%s\n%s%s, %s %s", streetAddress1, s2, city, state, zip);
    }
}
