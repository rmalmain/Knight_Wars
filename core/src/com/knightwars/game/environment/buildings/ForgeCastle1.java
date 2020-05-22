package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

/**
 * This is a forge : increase Knights defense (level 1)
 */
public class ForgeCastle1 extends Building {
    public ForgeCastle1(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
        owner.upgradeUnits();
    }

    public ForgeCastle1(Building building) {
        this(building.getOwner(), building.getCoordinates(), building.getKnights(), building.getCanGenerateUnits());
    }

    public ForgeCastle1 Copy() {
        return new ForgeCastle1(this);
    }
}
