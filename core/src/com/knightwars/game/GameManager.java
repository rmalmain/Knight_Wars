package com.knightwars.game;

public class GameManager {

    private KnightWarsGame game;

    public GameManager() { this.game = new KnightWarsGame(); }

    /** Update the game
     * @param dt time parameter
     */
    public void update(float dt) {
        game.update(dt);
    }

    public KnightWarsGame getKnightWarsGame() { return this.game; }
}
