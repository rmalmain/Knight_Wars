package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

/**
 * This is a Citadel : Has a turret and throws arrows
 */
public class GarrisonCastle2 extends GarrisonCastle1 {
    public GarrisonCastle2(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
    }

    /** Construct a building from another building.
     * @param building the building to copy
     */
    public GarrisonCastle2(Building building) {
        this(building.getOwner(), building.getCoordinates(), building.getKnights(), building.getCanGenerateUnits());
    }

    public ClassicCastle Copy() {
        return new FortifiedCastle(this);
    }
}
