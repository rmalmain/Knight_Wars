package com.knightwars.userInterface;

/** Exception raised when the player provided is unknown. */
public class UnknownPlayerException extends RuntimeException {
    public UnknownPlayerException(String message) {
        super(message);
    }
}
