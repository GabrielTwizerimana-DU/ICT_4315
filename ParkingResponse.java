
/**
 * File: ParkingResponse.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking8.server;

public class ParkingResponse {

    private int statusCode; // e.g., 200 for Success, 400 for Bad Request / Logic Errors
    private String message;    // Detailed confirmation text or error message

    /**
     * Explicit no-argument constructor required by the Gson reflection engine 
     * for client-side response reconstruction.
     * Standardized network envelope designed to send command execution outcomes
     * and status signaling back to remote clients.
     */
    public ParkingResponse() {
    }

    /**
     * Parameterized constructor used by the server to assemble outbound 
     * status packets immediately following command invocation.
     * * @param statusCode The HTTP-style integer status indicator.
     * @param statusCode
     * @param message    The descriptive result of the command execution string.
     */
    public ParkingResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}