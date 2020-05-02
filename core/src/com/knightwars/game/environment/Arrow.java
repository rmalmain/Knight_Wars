package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;

public class Arrow {
    private Vector2 coordinates;
    private Path path;
    private Unit destinationUnit;

    public Arrow(Vector2 departureCoordinates, Unit destinationUnit) {
        this.coordinates = departureCoordinates;
        this.destinationUnit = destinationUnit;
    }
}
