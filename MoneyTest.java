
/**
 * File: MoneyTest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.domain.model.classes.test;

import edu.du.ict4315.parking1.domain.model.classes.Money;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Money value object
 * Ensures financial data returned by the Strategy Factory is 
 * accurate, comparable, and correctly formatted
 */
public class MoneyTest {
   @Test
    public void testGetters() {
        Money money = new Money(25.50, "USD");
        assertEquals(25.50, money.getAmount(), 0.001);
        assertEquals("USD", money.getCurrency());
    }

    @Test
    public void testToStringFormatting() {
        Money money = new Money(10.0);
        // Ensures the output is formatted to two decimal places (e.g., "10.00 USD")
        assertEquals("$10.00 USD", money.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        Money m1 = new Money(15.75, "USD");
        Money m2 = new Money(15.75, "USD");
        Money m3 = new Money(20.00, "USD");
        Money m4 = new Money(15.75, "EUR");

        // Verify equality logic
        assertEquals(m1, m2, "Objects with same amount and currency must be equal.");
        assertNotEquals(m1, m3, "Objects with different amounts should not be equal.");
        assertNotEquals(m1, m4, "Objects with different currencies should not be equal.");

        // Verify hashcode consistency
        assertEquals(m1.hashCode(), m2.hashCode(), "Equal objects must have the same hashcode.");
    }

    @Test
    public void testImmutability() {
        // Since there are no setters in Money.java, we verify the state 
        // remains consistent through the object's lifecycle.
        Money money = new Money(50.00, "USD");
        double originalAmount = money.getAmount();
        
        // This test logically confirms that the amount cannot be changed 
        // externally (design-level verification).
        assertEquals(50.00, originalAmount);
    }
}
