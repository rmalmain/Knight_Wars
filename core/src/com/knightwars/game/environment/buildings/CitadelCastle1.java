package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Arrow;
import com.knightwars.game.environment.Building;
import com.knightwars.game.environment.Map;
import com.knightwars.game.environment.Unit;
import com.knightwars.game.players.Player;

/**
 * This is a Citadel : Has a turret and throws arrows (level 1)
 */
public class CitadelCastle1 extends Building {
    public final static float BUILDING_RANGE = 1.0f;
    public final static float ARROW_SPEED = 2.0f;
    public final static float ARROW_RATE = 1.0f;

    private float timeElapsedSinceLastArrow;

    public CitadelCastle1(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
        this.timeElapsedSinceLastArrow = 0f;
    }

    /** Construct a building from another building.
     * @param building the building to copy
     */
    public CitadelCastle1(Building building) {
        this(building.getOwner(), building.getCoordinates(), building.getKnights(), building.getCanGenerateUnits());
    }

    public CitadelCastle1 Copy() {
        return new CitadelCastle1(this);
    }

    private boolean canShoot() {
        return this.timeElapsedSinceLastArrow > ARROW_RATE;
    }

    private void shootArrow(Map map) {
        Unit destinationUnit = null;

        for (Unit unit : map.getUnits()) {
            if (unit.getCoordinates().dst(this.getCoordinates()) < BUILDING_RANGE && unit.getOwner() != this.getOwner()) {
                destinationUnit = unit;
                break;
            }
        }
        if (destinationUnit != null) {
            map.sendArrow(new Arrow(this.getCoordinates(), destinationUnit, ARROW_SPEED));
        }
    }

    @Override
    public void update(float dt, Map map) {
        super.update(dt, map);
        if (canShoot()) {
            this.timeElapsedSinceLastArrow = 0f;
            shootArrow(map);
        } else {
            this.timeElapsedSinceLastArrow += dt;
        }
    }
}
