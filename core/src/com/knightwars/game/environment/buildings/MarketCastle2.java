package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

/**
 * This is a Market : specialized in gold generation
 */
public class MarketCastle2 extends MarketCastle1 {
    public MarketCastle2(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
    }

    public MarketCastle2(Building building) {
        super(building);
    }

    public MarketCastle2 Copy() {
        return new MarketCastle2(this);
    }
}
