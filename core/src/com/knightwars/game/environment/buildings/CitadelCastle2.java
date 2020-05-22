package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

/**
 * This is a Garrison Castle : specialized in Knights generation
 */
public class CitadelCastle2 extends CitadelCastle1 {
    public final static float BUILDING_RANGE = 1.0f;
    public final static float ARROW_SPEED = 2.0f;
    public final static float ARROW_RATE = 1.0f;

    private float timeElapsedSinceLastArrow;

    public CitadelCastle2(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
        this.timeElapsedSinceLastArrow = 0f;
    }

    /** Construct a building from another building.
     * @param building the building to copy
     */
    public CitadelCastle2(Building building) {
        this(building.getOwner(), building.getCoordinates(), building.getKnights(), building.getCanGenerateUnits());
    }

    public CitadelCastle2 Copy() {
        return new CitadelCastle2(this);
    }
}
