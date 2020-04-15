package com.knightwars.userInterface;

/** Exception raised when the player provided is unknown. */
public class UnknownPlayerException extends RuntimeException {
    UnknownPlayerException(String message) {
        super(message);
    }
}
