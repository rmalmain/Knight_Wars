package com.knightwars.game;

public class GameManager {

    private KnightWarsGame game;

    public GameManager() { this.game = new KnightWarsGame(); }

    /* Update the game by a certain value */
    public void update(int dt) {}

    /* KnightWarsGame getter */
    public KnightWarsGame getKnightWarsGame() { return this.game; }
}
