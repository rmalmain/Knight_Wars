package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;

public class Path {
    private Vector2 startingPoint;
    private Vector2 arrivalPoint;
    private Vector2 currentPosition;
    private float totalTime;
    private float elapsedTime;

    public Path(Vector2 startingPoint, Vector2 arrivalPoint, float totalTime) {
        this.startingPoint = startingPoint;
        this.currentPosition = startingPoint;
        this.arrivalPoint = arrivalPoint;

        this.totalTime = totalTime;
        this.elapsedTime = 0;
    }

    public Vector2 getArrivalPoint() { return this.arrivalPoint; }

    public Vector2 getStartingPoint() { return this.startingPoint; }

    public float getElapsedTime() { return this.elapsedTime; }

    public float getTotalTime() { return this.totalTime; }

    public Vector2 getCoordinates() { return this.currentPosition; }

    public void update(float dt) {
        elapsedTime += dt;
        this.currentPosition = startingPoint.add((arrivalPoint.sub(startingPoint)).scl(elapsedTime/totalTime)); //current = start + (elapsed/total)*(arrival - start)
    }
}
