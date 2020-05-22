package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

/**
 * This is a forge : increase Knights defense
 */
public class ForgeCastle2 extends ForgeCastle1 {
    public ForgeCastle2(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
    }

    public ForgeCastle2(Building building) {
        this(building.getOwner(), building.getCoordinates(), building.getKnights(), building.getCanGenerateUnits());
    }

    public ForgeCastle2 Copy() {
        return new ForgeCastle2(this);
    }
}
