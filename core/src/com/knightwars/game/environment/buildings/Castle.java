package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.environment.Player;

public class Castle extends Building {
    public Castle(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
    }

    public Castle(Building building) {
        super(building);
    }

    public Castle Copy() {
        return new Castle(this);
    }
}
