package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

/**
 * This is a Market : specialized in gold generation (level 2)
 */
public class MarketCastle2 extends MarketCastle1 {
    public MarketCastle2(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
        this.goldGeneration *= 1.5;
        this.knightGeneration += 0.5;
    }

    public MarketCastle2(Building building) {
        this(building.getOwner(), building.getCoordinates(), building.getKnights(), building.getCanGenerateUnits());
    }

    public MarketCastle2 Copy() {
        return new MarketCastle2(this);
    }
}
