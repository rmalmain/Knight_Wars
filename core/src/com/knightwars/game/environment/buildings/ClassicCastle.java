package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

/**
 * This is a Classic Castle : base for gold generation and knights generation
 */
public class ClassicCastle extends Building {
    public ClassicCastle(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
    }

    public ClassicCastle(Building building) {
        this(building.getOwner(), building.getCoordinates(), building.getKnights(), building.getCanGenerateUnits());
    }

    public ClassicCastle Copy() {
        return new ClassicCastle(this);
    }
}
