package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Map {
    public ArrayList<Building> buildings;
    public ArrayList<Unit> units;
    public Vector2 size;

    public Map(float width, float height) {
        this.size = new Vector2(width, height);
        this.buildings = new ArrayList<>();
        this.units = new ArrayList<>();
    }

    public void addBuilding(Building building) {
        buildings.add(new Building(building));
    }

    public void addUnit(Unit unit) { units.add(unit); }

    public void deleteUnit(Unit unit) { units.remove(unit); }

    public Vector2 getSize() { return new Vector2(size); }

    public ArrayList<Building> getBuildings() { return buildings; }

    public ArrayList<Unit> getUnits() { return units; }
}