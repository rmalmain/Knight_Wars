package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;

public class Unit {
    public static final float DEFAULT_UNIT_SPEED = 1f;

    private Player owner;
    private Path path;
    private float speed;
    private Building departureBuilding;
    private Building destinationBuilding;

    /** Unit constructor.
     * @param owner the player owning the unit
     * @param departureBuilding the stating point of the unit
     * @param destinationBuilding the building where the unit goes
     */
    public Unit(Player owner, Building departureBuilding, Building destinationBuilding) {
        this.owner = owner;
        this.departureBuilding = departureBuilding;
        this.destinationBuilding = destinationBuilding;
        this.speed = DEFAULT_UNIT_SPEED;
        this.path = new Path(departureBuilding.getCoordinates(), destinationBuilding.getCoordinates(), this);
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
