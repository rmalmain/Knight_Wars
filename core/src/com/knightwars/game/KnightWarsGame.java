package com.knightwars.game;

import com.knightwars.game.environment.Map;
import com.knightwars.game.environment.MapFactory;
import com.knightwars.game.environment.Player;

import static com.badlogic.gdx.math.MathUtils.random;

public class KnightWarsGame {

    private Player playerRed, playerBlue, playerNeutral;
    private Map map;

    public KnightWarsGame() {
        this.playerRed = new Player("Red Player", Player.ColorPlayer.RED);
        this.playerBlue = new Player("Blue Player", Player.ColorPlayer.BLUE);
        this.playerNeutral = new Player("Neutral Player", Player.ColorPlayer.NEUTRAL);

        this.map = MapFactory.createProceduralMap(6f, 4f, 10, this.playerNeutral, 0.4f);

        attributeBuildings();
    }

    /* Attribute a building to each player.
    *  Every other buildings are attributed to the neutral player.
    */
    public void attributeBuildings() {
        int randomRed = random(map.getBuildings().size()-1);
        int randomBlue = random(map.getBuildings().size()-1);
        if (randomBlue == randomRed) {
            randomBlue++;
            randomBlue = randomBlue%(map.getBuildings().size());
        }
        map.getBuildings().get(randomRed).setOwner(playerRed);
        map.getBuildings().get(randomBlue).setOwner(playerBlue);
    }

    public Player getPlayerRed() { return this.playerRed; }

    public Player getPlayerBlue() { return this.playerBlue; }

    public Player getPlayerNeutral() { return this.playerNeutral; }

    public Map getMap() {
        return map;
    }
}
