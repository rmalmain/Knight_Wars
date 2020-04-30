package com.knightwars.game.environment;

/** Exception raised if someone tried to access a building and was not found
 */

public class NoBuildingFoundException extends Exception{
    public NoBuildingFoundException(String message) {
        super(message);
    }
}
