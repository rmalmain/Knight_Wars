package com.knightwars.game.players;

/**
 * A player who uses a mouse or a touchscreen.
 */
public class HumanPlayer extends Player {
    /**
     * Player constructor.
     *
     * @param name  The name of the player
     * @param color The color of the player
     */
    public HumanPlayer(String name, ColorPlayer color) {
        super(name, color);
    }
}
