package shawn_cheng.exceptions;

/**
 * Exception for invalid login
 * Might not use
 *
 * @author Shawn Cheng
 */

public class InvalidLoginException extends Exception {
    public InvalidLoginException(String msg) {
        super(msg);
    }
}