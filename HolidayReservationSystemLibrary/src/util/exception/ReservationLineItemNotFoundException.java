/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Asus
 */
public class ReservationLineItemNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>ReservationLineItemNotFoundException</code> without detail message.
     */
    public ReservationLineItemNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>ReservationLineItemNotFoundException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ReservationLineItemNotFoundException(String msg) {
        super(msg);
    }
}
