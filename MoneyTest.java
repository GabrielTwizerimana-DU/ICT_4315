
/**
 * File: MoneyTest.java
 * Author: Gabriel Twizerimana
 */

package edu.university.parking.assignment1.domain.model.classes.test;

import edu.university.parking.assignment1.domain.model.classes.Money;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {
    @Test
    public void testMultiplyDiscount() {
        Money fullPrice = new Money(1000, "USD"); // $10.00
        Money discounted = (Money) fullPrice.multiply(0.8); // 20% off
        assertEquals(800, discounted.getAmountInCents());
        assertEquals("USD", discounted.getCurrency());
    }

    @Test
    public void testAddMoney() {
        Money m1 = new Money(500, "USD");
        Money m2 = new Money(250, "USD");
        Money total = (Money) m1.add(m2);
        assertEquals(750, total.getAmountInCents());
    }
}
