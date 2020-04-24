package com.knightwars.game.environment;

/** Raised exception when an upgrade is not valid for a given building
 */
public class InvalidUpgradeException extends Exception {
    public InvalidUpgradeException(String message) {
        super(message);
    }
}
