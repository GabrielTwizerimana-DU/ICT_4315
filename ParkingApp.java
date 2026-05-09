
/**
 * File: ParkingApp.java
 * Author: Gabriel Twizerimana
 */
import edu.du.ict4315.parking1.controller.commands.ParkingLot;
import edu.du.ict4315.parking1.domain.model.classes.Car;
import edu.du.ict4315.parking1.domain.model.classes.CarType;
import edu.du.ict4315.parking1.domain.model.classes.Customer;
import edu.du.ict4315.parking1.domain.model.classes.Money;
import edu.du.ict4315.parking6.decorator.pattern.ParkingChargeCalculator;
import edu.du.ict4315.parking6.decorator.pattern.ParkingChargeCalculatorFactory;
import java.time.Duration;

/**
 * Entry point for the Parking System simulation Coordinates the Subject
 * (ParkingLot), Observer (TransactionManager), and Strategy logic
 */
public class ParkingApp {

    public static void main(String[] args) {
        
// 1. Initialize Infrastructure
                // 2. Create Domain Objects
        Customer customer = new Customer("C-101", "Developer", "555-0199");
        Car suv = new Car("CO-SUV-2026", CarType.SUV, customer);
        ParkingLot lot = new ParkingLot("Lot-A", "123 Strategy Lane", 100);
        lot.setPremium(true); // This will trigger the ServiceFeeDecorator in the factory

        // 3. Define the Stay Duration
        // Problem-solving: Using Duration instead of raw double
        Duration stay = Duration.ofHours(4);

        // 4. Assemble the Decorator Stack via the Factory
        // The factory handles: Base (Strategy) -> PeakMultiplier -> SUVFactor -> ServiceFee
        ParkingChargeCalculator calculator = ParkingChargeCalculatorFactory.createCalculator(lot, suv);

        // 5. Execute Calculation
        // Note: Using the synchronized method name 'calculateCharge'
        Money totalFee = calculator.calculateCharge(stay);

        // 6. Robust Output
        System.out.println("========================================");
        System.out.println("   PARKING RECEIPT - " + lot.getName());
        System.out.println("========================================");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Vehicle:  " + suv.getLicensePlate() + " (" + suv.getType() + ")");
        System.out.println("Duration: " + stay.toHours() + " Hours");
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL DUE: $%.2f%n", totalFee.getAmount());
        System.out.println("========================================");
    }
    }
      
