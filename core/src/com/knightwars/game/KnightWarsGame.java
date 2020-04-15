package com.knightwars.game;

import com.knightwars.game.environment.*;

import java.util.HashMap;

public class KnightWarsGame {

    private Player playerRed, playerBlue, playerNeutral;
    private Map map;

    public KnightWarsGame() {
        this.playerRed = new Player("Red Player", Player.ColorPlayer.RED);
        this.playerBlue = new Player("Blue Player", Player.ColorPlayer.BLUE);
        this.playerNeutral = new Player("Neutral Player", Player.ColorPlayer.NEUTRAL);

        this.map = MapFactory.createProceduralMap(3f, 2f, 10, this.playerNeutral);
    }

    public Map getMap() {
        return map;
    }

}
