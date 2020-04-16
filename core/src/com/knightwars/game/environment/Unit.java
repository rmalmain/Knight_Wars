package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;

public class Unit {
    private Player owner;
    private Path path;
    private Vector2 speed;
    private Building destinationBuilding;

    /** Unit constructor.
     * @param owner the player owning the unit
     * @param startingPoint the stating point of the unit
     * @param speed the speed of the unit
     * @param destinationBuilding the building where the unit goes
     */
    public Unit(Player owner, Vector2 startingPoint, float speed, Building destinationBuilding) {
        this.owner = owner;
        this.destinationBuilding = destinationBuilding;
        this.speed = new Vector2(speed, speed);
        this.path = new Path(startingPoint, destinationBuilding.getCoordinates(), this);
    }

    public Player getOwner() {
        return this.owner;
    }

    public Vector2 getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = new Vector2(speed, speed);
    }

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
