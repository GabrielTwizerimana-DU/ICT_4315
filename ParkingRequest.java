
/**
 * File: ParkingRequest.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking8.shared;

public class ParkingRequest {
    
    private String command;
    private Object payload; // Securely wraps RegisterCustomerCommand or RegisterCarCommand instances

    /**
     * Explicit no-argument constructor required by the Gson reflection engine 
     * for server-side packet initialization
     */
    public ParkingRequest() {
    }

    /**
     * Parameterized constructor utilized by clients or factory controllers 
     * to assemble outbound network transmissions.
     * * @param command The explicit routing token identifying the transaction type.
     * @param command
     * @param payload The Command object containing data fields to be serialized.
     */
    public ParkingRequest(String command, Object payload) {
        this.command = command;
        this.payload = payload;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}