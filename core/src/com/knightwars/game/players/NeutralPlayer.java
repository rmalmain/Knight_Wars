package com.knightwars.game.players;

/**
 * A player who does nothing.
 */
public class NeutralPlayer extends Player {
    /**
     * Player constructor.
     *
     * @param name  The name of the player
     * @param color The color of the player
     */
    public NeutralPlayer(String name, ColorPlayer color) {
        super(name, color);
    }
}
