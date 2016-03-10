/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionHandling;

/**
 *
 * @author minahilIkram
 */
public class InvalidNumberException extends Exception {
    public InvalidNumberException () {}
    public InvalidNumberException (String message) {
        super(message);
    }
}