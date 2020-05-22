package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;

public class Arrow {
    private Vector2 coordinates;
    private float speed;
    private Unit destinationUnit;

    public Arrow(Vector2 departureCoordinates, Unit destinationUnit, float speed) {
        this.coordinates = departureCoordinates;
        this.destinationUnit = destinationUnit;
        this.speed = speed;
    }

    public Unit getDestinationUnit() {
        return this.destinationUnit;
    }

    public Vector2 getCoordinates() {
        return this.coordinates;
    }

    public void update(float dt) {
        Vector2 normedDirectorVector = (new Vector2(destinationUnit.getCoordinates()).sub(coordinates)).nor();
        coordinates.add(new Vector2(normedDirectorVector).scl(speed*dt));
    }

    public boolean isArrived(float threshold) {
        return coordinates.dst(destinationUnit.getCoordinates()) < threshold;
    }
}
