package edu.du.ict4315.parking4.factory.pattern;

import edu.du.ict4315.parking3.strategies.ParkingChargeStrategy;
import edu.du.ict4315.parking3.strategies.TypeBasedStrategy;
import edu.du.ict4315.parking3.strategies.WeekdayWeekendStrategy;

/**
 * The Factory class responsible for instantiating the correct 
 * ParkingChargeStrategy based on a provided identifier.
 */
public class ParkingChargeStrategyFactory {
public static ParkingChargeStrategy getStrategy(String type) {
     if (type == null) {
            return new TypeBasedStrategy(); // Default
        }
        return switch (type.toUpperCase()) {
            case "WEEKDAY_WEEKEND" -> new WeekdayWeekendStrategy();
            case "TYPE_BASED" -> new TypeBasedStrategy();
            default -> new TypeBasedStrategy(); // Default fallback
        };
    }
}
