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
public class UpdateInventoryException extends Exception {

    /**
     * Creates a new instance of <code>UpdateInventoryException</code> without
     * detail message.
     */
    public UpdateInventoryException() {
    }

    /**
     * Constructs an instance of <code>UpdateInventoryException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateInventoryException(String msg) {
        super(msg);
    }
}
