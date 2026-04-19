/**
 * File: Money.java
 * Author: Gabriel Twizerimana
 */
package edu.university.parking.assignment1.domain.model.classes;

public class Money {

    private final long amountInCents; // Value in cents
    private final String currency;

    
    public Money(long amount, String currency) {
        this.amountInCents = amount;
        this.currency = currency;
    }
    
public Money(long amountInCents) {
        this.amountInCents = amountInCents;
        this.currency = "USD";
    }

    public long getAmountInCents() {
        return this.amountInCents;
    }

    public String getCurrency() {
        return currency;
    }
    
    public Money add(Money other) {
        if (!this.currency.equals(other.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Money(this.amountInCents + other.getAmountInCents(), this.currency);
    }
    
    public Money multiply(double factor) {
    // We use Math.round to handle any decimal fractions 
    // before converting back to a long (cents).
    long newAmount = Math.round(this.amountInCents * factor);
    return new Money(newAmount, this.currency);
}
    
    @Override
    public String toString() {
    // Converts cents to a decimal string, e.g., 1200 becomes 12.00
    double dollars = amountInCents / 100.0;
    return String.format("$%.2f", dollars);
}
}
