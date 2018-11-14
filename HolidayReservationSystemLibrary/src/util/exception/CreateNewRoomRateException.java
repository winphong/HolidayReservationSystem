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
public class CreateNewRoomRateException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewRoomRateException</code> without
     * detail message.
     */
    public CreateNewRoomRateException() {
    }

    /**
     * Constructs an instance of <code>CreateNewRoomRateException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewRoomRateException(String msg) {
        super(msg);
    }
}
