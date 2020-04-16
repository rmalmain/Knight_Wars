package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.knightwars.game.KnightWarsGame;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Map {
    public static final float BUILDING_COLLISION_THRESHOLD = 0.1f;
    public static final float TIME_BETWEEN_UNITS = 0.2f;

    private ArrayList<Building> buildings;
    private ArrayList<Queue<Unit>> unitsToSend;
    private float unitSpawnTick;
    private ArrayList<Unit> units;
    private Vector2 size;

    /** Map constructor
     * @param width the width of the map
     * @param height the height of the map
     */
    public Map(float width, float height) {
        this.size = new Vector2(width, height);
        this.buildings = new ArrayList<>();
        this.units = new ArrayList<>();
        this.unitsToSend = new ArrayList<>();
        this.unitSpawnTick = 0f;
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
    private void addUnit(Unit unit) {
        try {
            unit.getDepartureBuilding().unitDeparture();
            units.add(unit);
        } catch (NotEnoughKnightsException e) {
            e.getStackTrace();
        }
    }

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
        ArrayList<Unit> unitsToDelete = new ArrayList<>();

        this.unitSpawnTick += dt;

        for(Unit unit : units) { // Update units on the map
            unit.update(dt);
            if (unit.isArrived(BUILDING_COLLISION_THRESHOLD)) { // if units arrived to the building
                try {
                    unit.getDestinationBuilding().unitArrival(unit);
                } catch (AttackerWonFightException e) {
                    e.getAttackedBuilding().setOwner(e.getAttackingPlayer());
                    e.getAttackedBuilding().setCanGenerateUnits(true);
                }
                unitsToDelete.add(unit);
            }
        }

        for(Unit unit : unitsToDelete) { // Delete arrived units
            this.units.remove(unit);
        }

        for(Building building : buildings) { // Update buildings
            building.update(dt);
        }

        if(unitSpawnTick > TIME_BETWEEN_UNITS){ // Send units if enough time elapsed
            unitSpawnTick = 0f;
            ArrayList<Queue<Unit>> unitGroupToDelete = new ArrayList<>();

            for(Queue<Unit> unitGroup : unitsToSend){ // all units waiting are sent
                try {
                    addUnit(unitGroup.first());
                    unitGroup.removeFirst();
                } catch (NoSuchElementException e){ continue; } // May be a bad idea ?
                if (unitGroup.isEmpty()) {
                    unitGroupToDelete.add(unitGroup);
                }
            }

            for(Queue<Unit> unitGroup : unitGroupToDelete) { //Remove empty unit groups
                unitsToSend.remove(unitGroup);
            }
        }
    }

    /** Send 100*percentage percent from departureBuilding to arrivalBuilding
     * @param departureBuilding departure building
     * @param arrivalBuilding arrival building
     * @param percentage percentage of unit to send. Must be between 0 and 1
     */
    public void sendUnit(Building departureBuilding, Building arrivalBuilding, float percentage) {
        if (percentage > 1f || percentage < 0f) {
            throw new RuntimeException("The percentage of unit to send is wrong.");
        }

        int knightNumberToSend = (int) Math.floor(departureBuilding.getKnights() * percentage);

        Queue<Unit> unitsToMakeSpawn = new Queue<>();

        for (int i = 0; i < knightNumberToSend; i++) {
            unitsToMakeSpawn.addLast(new Unit(departureBuilding.getOwner(), departureBuilding, arrivalBuilding));
        }

        this.unitsToSend.add(unitsToMakeSpawn);
    }

    public void changeBuilding(Building oldBuilding, Building newBuilding) {
        newBuilding.setKnights(oldBuilding.getKnights());
        newBuilding.setCanGenerateUnits(oldBuilding.getCanGenerateUnits());
        newBuilding.setOwner(oldBuilding.getOwner());
    }
}