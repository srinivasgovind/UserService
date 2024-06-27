package dev.srinivas.UserService.exception;

/**
 * Exception thrown when a token is invalid.
 */
public class InvalidTokenException extends RuntimeException{

    /**
     * Constructs a new InvalidTokenException with the specified detail message.
     * @param message the detail message
     */
    public InvalidTokenException(String message){
        super(message);
    }
}
