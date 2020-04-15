package com.knightwars.game.environment;

/** Exception raised when a unit is not found in a list */
public class NoUnitFoundException extends RuntimeException {
    public NoUnitFoundException(String message){
        super(message);
    }
}
