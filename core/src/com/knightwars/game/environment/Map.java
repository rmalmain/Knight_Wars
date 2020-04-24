package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.knightwars.game.YamlParser;
import com.knightwars.game.players.Player;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Map {
    public static final float BUILDING_COLLISION_THRESHOLD = 0.25f;
    public static final float TIME_BETWEEN_UNITS = 0.15f;
    public static final float EPSILON_BUILDING_COORDINATES = 0.000000001f;

    private ArrayList<Building> buildings;
    private ArrayList<Queue<Unit>> unitsToSend;
    private float unitSpawnTick;
    private ArrayList<Unit> units;
    private Vector2 size;
    private java.util.Map<String, Object> buildingHierarchy;

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
        try {
            this.buildingHierarchy = YamlParser.yamlToJavaMap("E:\\Library\\Documents\\N7\\1A\\TOB\\gros-projet\\github\\core" +
                    "\\src\\com\\knightwars\\game\\environment\\building-structure.yml");
            YamlParser.yamlValidity(this.buildingHierarchy, Building.class, "com.knightwars.game.environment.buildings");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
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
        if (departureBuilding.getOwner().getColor() != Player.ColorPlayer.NEUTRAL  // Neutral castles can't be selected as departure buildings
                && departureBuilding != arrivalBuilding) { // Units can't be send to their own building
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
    }

    public void changeBuilding(Building oldBuilding, Building newBuilding) {
        newBuilding.setKnights(oldBuilding.getKnights());
        newBuilding.setCanGenerateUnits(oldBuilding.getCanGenerateUnits());
        newBuilding.setOwner(oldBuilding.getOwner());
    }

    private int coordinatesToBuildingsIndex(Vector2 coordinates) throws NoBuildingFoundException {
        for (Building building : buildings) {
            if (building.getCoordinates().dst(coordinates) < EPSILON_BUILDING_COORDINATES) {
                return buildings.indexOf(building);
            }
        }
        throw new NoBuildingFoundException("No building has been found.");
    }

    public void upgradeBuilding(Vector2 coordinatesBuildingToUpgrade, Class<? extends Building> building) throws InvalidUpgradeException, NoBuildingFoundException {
        upgradeBuilding(this.buildings.get(coordinatesToBuildingsIndex(coordinatesBuildingToUpgrade)), building);
    }

    private boolean isUpgradable(Building building, Class<?> buildingClass) {
        String hierarchyResult = this.buildingHierarchy.get(building.getClass().getSimpleName()).toString();
        return Arrays.asList(hierarchyResult.substring(1, hierarchyResult.length()-1).split("\\s*,\\s*")).contains(buildingClass.getSimpleName());
    }

    public void upgradeBuilding(Building buildingToUpgrade, Class<? extends Building> building) throws InvalidUpgradeException {
        if (!isUpgradable(buildingToUpgrade, building)) {
            throw new InvalidUpgradeException("This building can't be upgraded to such class according to the hierarchy. Please" +
                    " take a look at the yaml file 'building-structure'.");
        }
        else {
            try {
                this.buildings.set(this.buildings.indexOf(buildingToUpgrade),
                        building.getConstructor(Building.class).newInstance(buildingToUpgrade)); //JPP DE JAVA
                                                                                    // SI CA MARCHE WALLAH JE ME COUPE LES
                                                                                    // VEINES !!!!!!!!!!
            } catch (Exception e) {
                System.out.println("Erreur lors de l'instanciation du bâtiment amélioré...");
            }
        }
    }
}
