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
    protected float buildingRange = 2.0f;
    protected float arrowSpeed = 3.0f;
    protected float arrowRate = 1f;

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
        return this.timeElapsedSinceLastArrow > arrowRate;
    }

    public float getBuildingRange() {
        return this.buildingRange;
    }

    private void shootArrow(Map map) {
        Unit destinationUnit = null;

        for (Unit unit : map.getUnits()) {
            if (unit.getCoordinates().dst(this.getCoordinates()) < buildingRange && unit.getOwner() != this.getOwner()) {
                destinationUnit = unit;
                break;
            }
        }
        if (destinationUnit != null) {
            map.sendArrow(new Arrow(this.getCoordinates(), destinationUnit, arrowSpeed));
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
