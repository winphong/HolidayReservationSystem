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
public class CheckInException extends Exception {

    /**
     * Creates a new instance of <code>CheckInException</code> without detail
     * message.
     */
    public CheckInException() {
    }

    /**
     * Constructs an instance of <code>CheckInException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CheckInException(String msg) {
        super(msg);
    }
}
