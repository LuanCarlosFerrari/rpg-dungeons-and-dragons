/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionHandling;

/**
 *
 * @author minahilIkram
 */
public class InvalidNameException extends Exception {
    
    public InvalidNameException () {}
    public InvalidNameException (String message) {
        super(message);
    }
}
