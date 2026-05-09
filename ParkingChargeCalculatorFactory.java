
/**
 * File: ParkingChargeCalculatorFactory.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking6.decorator.pattern;

import edu.du.ict4315.parking1.controller.commands.ParkingLot;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;

public class ParkingChargeCalculatorFactory {
    
    public static ParkingChargeCalculator createCalculator(ParkingLot lot, Car car) {
        // 1. Start with the base calculator for the lot
        ParkingChargeCalculator calculator = new BaseStayCalculator(lot.getBaseHourlyRate());

        // 2. Layer: Vehicle Size Surcharge
        if (car.getType() == CarType.SUV) {
            calculator = new VehicleSizeSurchargeDecorator(calculator, 1.5);
        }

        // 3. Layer: Holiday Surcharge (Logic could check a Calendar service)
        if (lot.isHolidayRateActive()) {
            calculator = new HolidaySurchargeDecorator(calculator, 2.0);
        }

        // 4. Layer: Loyalty Discount
        if (car.getOwner() != null && car.getOwner().isLoyaltyMember()) {
            calculator = new LoyaltyDiscountDecorator(calculator, 0.80); // 20% off
        }
         
        return calculator;
    }
}
