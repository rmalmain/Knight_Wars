package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Map {
    public ArrayList<Building> buildings;
    public ArrayList<Unit> units;
    public Vector2 size;
    public float buildingThreshold;

    /** Map constructor
     * @param width the width of the map
     * @param height the height of the map
     * @param buildingThreshold the collision threshold of the building
     */
    public Map(float width, float height, float buildingThreshold) {
        this.size = new Vector2(width, height);
        this.buildings = new ArrayList<>();
        this.units = new ArrayList<>();
        this.buildingThreshold = buildingThreshold;
    }

    /** Add a building to the map
     * @param building the building to add
     */
    public void addBuildingCopy(Building building) {
        buildings.add(building.Copy());
    }

    /** Add a unit to the map
     * @param unit unit to add to the map
     */
    public void addUnit(Unit unit) { units.add(unit); }

    /** Delete a unit from the map
     * @param unit the unit to delete
     */
    public void deleteUnit(Unit unit) {
        if(!units.remove(unit)) {
            throw new NoUnitFoundException("The unit wasn't found in unit list");
        }
    }

    public Vector2 getSize() { return new Vector2(size); }

    public ArrayList<Building> getBuildings() { return buildings; }

    public ArrayList<Unit> getUnits() { return units; }

    /** Update each element depending on the map
     * @param dt the time parameter of the update
     */
    public void update(float dt) {
        for(Unit unit : units) { // Update units
            unit.update(dt);
            if (unit.isArrived(this.buildingThreshold)) { // if units arrived to the building
                try {
                    unit.getDestinationBuilding().unitArrival(unit);
                } catch (NotEnoughKnightsException e) {
                    // To complete...
                }
                this.deleteUnit(unit);
            }
        }
        for(Building building : buildings) { // Update buildings
            building.update(dt);
        }
    }
}