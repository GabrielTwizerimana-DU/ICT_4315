
/**
 * File: CarType.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking1.domain.model.classes;

/**
 * Defines the categories of vehicles supported by the parking system.
 * The Strategy Factory uses these types to determine specific fee structures.
 */
public enum CarType {
    /** Standard rate vehicle */
    COMPACT,
    
    /** Premium rate vehicle */
    SUV,
    
    /** Large vehicle rate */
    TRUCK,
    
    /** Specialized or commercial rate */
    VAN
}
