
/**
 * File: Car.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.domain.model.classes;

/**
 * Represents a vehicle in the system.
 * This class provides the CarType data required by the Strategy Factory.
 */
public class Car {
private final String licensePlate;
    private CarType type;
    private final Customer owner;

    /**
     * @param licensePlate The unique permanent identifier for the vehicle.
     * @param type The category of the vehicle (e.g., COMPACT, SUV).
     * @param owner The Customer who owns the vehicle.
     */
    public Car(String licensePlate, CarType type, Customer owner) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.owner = owner;
    }

    /**
     * Returns the permanent license plate.
     * @return String licensePlate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Returns the current vehicle type.
     * @return CarType enum
     */
    public CarType getType() {
        return type;
    }
    

    /**
     * Allows for updates to the car type (e.g., if a mistake was made during registration).
     * This ensures the Strategy Factory pulls the most current data during an EXIT event.
     * @param type The new CarType to apply.
     */
    public void setType(CarType type) {
        this.type = type;
    }

    /**
     * Returns the Customer associated with this vehicle.
     * @return Customer owner
     */
    public Customer getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return String.format("Car[Plate: %s, Type: %s, Owner: %s]", 
                licensePlate, type, owner.getName());
    }
}
