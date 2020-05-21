package com.knightwars.userInterface;

/** Exception raised when the building provided is unknown.*/
public class UnknownBuildingException extends RuntimeException{
    public UnknownBuildingException(String message) { super(message); }
}

