package com.knightwars.game;

import com.knightwars.game.environment.Map;
import com.knightwars.game.environment.MapFactory;
import com.knightwars.game.environment.Player;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;

public class KnightWarsGame {
    public static final float WIDTH = 6f;
    public static final float HEIGHT = 4f;
    public static final int BUILDINGS_NUMBER = 10;

    private ArrayList<Player> players;
    private Player playerRed, playerBlue, playerNeutral;
    private Map map;

    public KnightWarsGame() {
        // Players initialization
        playerRed = new Player("Red Player", Player.ColorPlayer.RED);
        playerBlue = new Player("Blue Player", Player.ColorPlayer.BLUE);
        playerNeutral = new Player("Neutral Player", Player.ColorPlayer.NEUTRAL);

        this.map = MapFactory.createProceduralMap( // Map generation
                WIDTH, HEIGHT, BUILDINGS_NUMBER, this.playerNeutral);

        // Buildings attribution
        attributeBuildings();
    }

    /** Attribute a building to each player. Every other buildings are attributed to the neutral player. */
    public void attributeBuildings() {
        int randomRed = random(map.getBuildings().size()-1);
        int randomBlue = random(map.getBuildings().size()-1);
        if (randomBlue == randomRed) {
            randomBlue++;
            randomBlue = randomBlue%(map.getBuildings().size());
        }

        map.getBuildings().get(randomRed).setOwner(playerRed);
        map.getBuildings().get(randomRed).setKnights(50);
        map.getBuildings().get(randomRed).setKnightGrowth(true);

        map.getBuildings().get(randomBlue).setOwner(playerBlue);
        map.getBuildings().get(randomBlue).setKnights(50);
        map.getBuildings().get(randomBlue).setKnightGrowth(true);
    }

    public Player getPlayerRed() { return this.playerRed; }

    public Player getPlayerBlue() { return this.playerBlue; }

    public Player getPlayerNeutral() { return this.playerNeutral; }

    public Map getMap() { return map; }

    public void update(float dt) {
        map.update(dt);
    }
}
