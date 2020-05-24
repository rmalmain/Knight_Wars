package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.players.Player;

public class Unit {
    public static final float DEFAULT_UNIT_SPEED = 0.5f;

    private com.knightwars.game.players.Player owner;
    private Path path;
    private float speed;
    private Building departureBuilding;
    private Building destinationBuilding;

    /**
     * Unit constructor.
     *
     * @param owner               The player owning the unit
     * @param departureBuilding   The stating point of the unit
     * @param destinationBuilding The building where the unit goes
     * @param path                A list of points followed by the unit
     */
    public Unit(Player owner, Building departureBuilding, Building destinationBuilding, Path path) {
        this.owner = owner;
        this.path = path;
        this.departureBuilding = departureBuilding;
        this.destinationBuilding = destinationBuilding;
        this.speed = DEFAULT_UNIT_SPEED;
    }

    public Player getOwner() {
        return this.owner;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Building getDepartureBuilding() { return this.departureBuilding; }

    public Building getDestinationBuilding() { return this.destinationBuilding; }

    public Vector2 getCoordinates() {
        return this.path.getCurrentPosition();
    }

    /** Update the unit
     * @param dt time parameter
     */
    public void update(float dt) {
        this.path.update(dt);
    }

    /** Know if a unit arrived at building destination
     * @param threshold the collision threshold
     * @return true if the unit arrived, false otherwise
     */
    public boolean isArrived(float threshold) {
        return this.getCoordinates().dst(this.getDestinationBuilding().getCoordinates()) < threshold;
    }

    public float getTotalAttack() {
        return this.owner.getUnitLevel();
    }
}
