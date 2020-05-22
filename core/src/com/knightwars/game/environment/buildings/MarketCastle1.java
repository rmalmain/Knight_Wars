package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

/**
 * This is a Market : specialized in gold generation
 */
public class MarketCastle1 extends Building {
    public MarketCastle1(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
    }

    public MarketCastle1(Building building) {
        super(building);
    }

    public MarketCastle1 Copy() {
        return new MarketCastle1(this);
    }
}
