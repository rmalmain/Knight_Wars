package com.knightwars.game.environment;

/** This exception is raised when one tries to take too much knights from a building */

public class NotEnoughKnightsException extends Exception {
    public NotEnoughKnightsException(String message) {
        super(message);
    }
}
