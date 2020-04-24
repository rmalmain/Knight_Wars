package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

public class CitadelCastle1 extends Building {
    public CitadelCastle1(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
    }

    public CitadelCastle1(Building building) {
        super(building);
    }

    public CitadelCastle1 Copy() {
        return new CitadelCastle1(this);
    }
}
