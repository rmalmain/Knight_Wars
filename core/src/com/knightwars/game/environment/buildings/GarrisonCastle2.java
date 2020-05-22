package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

/**
 * This is a Garrison Castle : specialized in Knights generation (level 2)
 */
public class GarrisonCastle2 extends GarrisonCastle1 {
    public GarrisonCastle2(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
        this.goldGeneration += 0.5;
        this.knightGeneration *= 1.5;
    }

    public GarrisonCastle2(Building building) {
        this(building.getOwner(), building.getCoordinates(), building.getKnights(), building.getCanGenerateUnits());
    }

    public GarrisonCastle2 Copy() {
        return new GarrisonCastle2(this);
    }
}
