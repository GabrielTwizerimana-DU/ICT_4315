package edu.du.ict4315.parking4.charges.factory;

import edu.du.ict4315.parking3.strategies.ParkingChargeStrategy;
import edu.du.ict4315.parking3.strategies.TypeBasedStrategy;
import edu.du.ict4315.parking3.strategies.WeekdayPrimeStrategy;
import edu.du.ict4315.parking3.strategies.WeekdayWeekendStrategy;
import edu.du.ict4315.parking3.strategies.WeekendDiscountStrategy;

/**
 * The Factory class responsible for instantiating the correct 
 * ParkingChargeStrategy based on a provided identifier.
 */
public class ParkingChargeStrategyFactory {

   /**
     * Factory method to get a parking charge strategy.
     * 
     * @param strategyName The name/type of the strategy requested.
     * @return A concrete implementation of ParkingChargeStrategy.
     */
    public ParkingChargeStrategy getStrategy(String strategyName) {
        if (strategyName == null) {
            return new TypeBasedStrategy(); // Default fallback
        }

        // Standardizing input to handle case-insensitive strings
        String normalizedName = strategyName.toUpperCase().replace(" ", "_");

        return switch (normalizedName) {
            case "TYPE_BASED" -> new TypeBasedStrategy();
            case "WEEKDAY_PRIME" -> new WeekdayPrimeStrategy();
            case "WEEKDAY_WEEKEND" -> new WeekdayWeekendStrategy();
            case "WEEKEND_DISCOUNT" -> new WeekendDiscountStrategy();
            default -> new TypeBasedStrategy();
        }; // Log a warning or return a default strategy
    }
}
