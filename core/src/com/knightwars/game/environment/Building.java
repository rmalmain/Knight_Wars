package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;

public class Building {
    public Player owner;
    public Vector2 coordinates;
    public int knights;


    public Building(Player owner, Vector2 coordinates, int knights) {
        this.owner = owner;
        this.coordinates = new Vector2(coordinates);
        this.knights = knights;
    }

    public Building(Building building)
    {
        this.owner = building.owner;
        this.coordinates = new Vector2(building.coordinates);
        this.knights = building.knights;
    }

    public void setOwner(Player player) { this.owner = player; }

    /* Owner getter */
    public Player getOwner() { return this.owner; }

    /* Coordinates getter */
    public Vector2 getCoordinates() { return new Vector2(this.coordinates);}

    /* Knights number getter */
    public int getKnights () { return this.knights; }
}
