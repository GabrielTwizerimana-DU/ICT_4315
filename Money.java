
/**
 * File: Money.java
 * Author: Gabriel Twizerimana
 */
package edu.du.ict4315.parking1.domain.model.classes;

import java.util.Objects;

/**
 * A Value Object representing a monetary amount and its currency.
 * Used by Strategy objects to return structured financial data.
 */
public class Money {
private final double amount;
    private final String currency;

    public Money(double amount) {
        this(amount, "USD");
    }

    public Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    /**
     * Adds another Money object to this one.
     * @param other
     * @return A new Money instance representing the sum.
     */
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Money(this.amount + other.amount, this.currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Double.compare(money.amount, amount) == 0 && 
               Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return String.format("$%.2f %s", amount, currency);
    }
}
