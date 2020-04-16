package com.knightwars.game.environment;

/** Exception raised when one tries to remove more golds than the player has
 */
public class NotEnoughGoldException extends Exception {
    public NotEnoughGoldException(String message) {
        super(message);
    }
}
