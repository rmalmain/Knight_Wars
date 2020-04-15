package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;

public class Building {
    public Player owner;
    public Vector2 coordinates;

    public Building(Player owner, Vector2 coordinates) {
        this.owner = owner;
        this.coordinates = new Vector2(coordinates);
    }

    public Building(Building building)
    {
        this.owner = building.owner;
        this.coordinates = new Vector2(building.coordinates);
    }

    /* Owner getter */
    public Player getOwner() { return this.owner; }

    /* Coordinates getter */
    public Vector2 getCoordinates() { return new Vector2(this.coordinates);}
}
