package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

public class GarrisonCastle1 extends Building {
    public GarrisonCastle1(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
    }

    public GarrisonCastle1(Building building) {
        super(building);
    }

    public GarrisonCastle1 Copy() {
        return new GarrisonCastle1(this);
    }
}
