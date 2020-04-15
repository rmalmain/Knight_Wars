package com.knightwars.game;

import com.knightwars.game.environment.*;

import java.util.HashMap;

public class KnightWarsGame {

    Player playerRed, playerBlue, playerNeutral;
    private final HashMap<String, Object> map;

    public KnightWarsGame() {
        this.playerRed = new Player("Red Player", Player.ColorPlayer.RED);
        this.playerBlue = new Player("Blue Player", Player.ColorPlayer.BLUE);
        this.playerNeutral = new Player("Neutral Player", Player.ColorPlayer.NEUTRAL);

        this.map = MapFactory.createMap(3f, 2f, 10);
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

}
