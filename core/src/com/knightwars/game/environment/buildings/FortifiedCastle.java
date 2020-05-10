package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

public class FortifiedCastle extends ClassicCastle {
    public FortifiedCastle(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
        this.knightGeneration *= 2;
        this.goldGeneration *= 1.5;
    }

    /** Construct a building from another building.
     * @param building the building to copy
     */
    public FortifiedCastle(Building building) {
        this(building.getOwner(), building.getCoordinates(), building.getKnights(), building.getCanGenerateUnits());
    }

    public ClassicCastle Copy() {
        return new FortifiedCastle(this);
    }
}
