package com.knightwars.game;

import com.knightwars.game.environment.MapFactory;

import java.util.HashMap;

public class KnightWarsGame {

    //Player playerRed, playerBlue, playerNeutral;
    private final HashMap<String, Object> map;

    public KnightWarsGame() {
        //this.playerRed = new Player();
        //this.playerBlue = new Player();
        //this.playerNeutral = new Player();

        this.map = MapFactory.createMap(3f, 2f, 10);
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

}
