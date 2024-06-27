package dev.srinivas.UserService.exception;

/**
 * Exception thrown when user credentials are invalid.
 */
public class InvalidCredentialException extends RuntimeException{

    /**
     * Constructs a new InvalidCredentialException with the specified detail message.
     * @param message the detail message
     */
    public InvalidCredentialException(String message){
        super(message);
    }
}
