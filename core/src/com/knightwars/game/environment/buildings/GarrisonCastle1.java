package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

/**
 * This is a Garrison Castle : specialized in Knights generation (level 1)
 */
public class GarrisonCastle1 extends FortifiedCastle {

    public GarrisonCastle1(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
        this.goldGeneration *= 0;
        this.knightGeneration *= 2;
    }

    /** Construct a building from another building.
     * @param building the building to copy
     */
    public GarrisonCastle1(Building building) {
        this(building.getOwner(), building.getCoordinates(), building.getKnights(), building.getCanGenerateUnits());
    }

    public GarrisonCastle1 Copy() {
        return new GarrisonCastle1(this);
    }

}
