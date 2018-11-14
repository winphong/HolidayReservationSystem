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
public class CreateNewRoomException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewRoomException</code> without
     * detail message.
     */
    public CreateNewRoomException() {
    }

    /**
     * Constructs an instance of <code>CreateNewRoomException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewRoomException(String msg) {
        super(msg);
    }
}
