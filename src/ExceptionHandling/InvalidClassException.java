/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionHandling;

/**
 *
 * @author minahilIkram
 */
public class InvalidClassException extends Exception {
    public InvalidClassException () {}
    public InvalidClassException (String message) {
        super(message);
    }
}
