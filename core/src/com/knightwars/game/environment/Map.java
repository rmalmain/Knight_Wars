package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Map {
    public ArrayList<Building> buildings;
    public Vector2 size;

    public Map(float width, float height) {
        this.size = new Vector2(width, height);
        this.buildings = new ArrayList<>();
    }

    public void addBuilding(Building building) {
        buildings.add(new Building(building));
    }

    public ArrayList<Building> getBuildings() { return buildings; }
}