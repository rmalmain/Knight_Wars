package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;

public class Path {
    private Vector2 currentPosition;
    private Vector2 arrivalPoint;
    private Unit unit;

    /** Path constructor
     * @param startingPoint the starting point of the path
     * @param arrivalPoint the arrival point of the path
     * @param unit the unit being into the path
     */
    public Path(Vector2 startingPoint, Vector2 arrivalPoint, Unit unit) {
        this.currentPosition = new Vector2(startingPoint);
        this.arrivalPoint = new Vector2(arrivalPoint);
        this.unit = unit;
    }

    public Path(Building statingBuilding, Building arrivalBuilding, Unit unit) {
        this(statingBuilding.getCoordinates(), arrivalBuilding.getCoordinates(), unit);
    }

    public Vector2 getArrivalPoint() { return this.arrivalPoint; }

    public Vector2 getCurrentPosition() { return this.currentPosition; }

    /** Update the position of the unit
     * @param dt time parameter
     */
    public void update(float dt) {
        this.currentPosition = currentPosition.add(unit.getSpeed().scl(dt)); //current = current + speed*dt
    }
}
