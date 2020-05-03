package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.knightwars.game.InvalidYamlFormatException;
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
    public static final String BUILDINGS_LOCATION_PACKAGE = "com.knightwars.game.environment.buildings";

    private ArrayList<Building> buildings;
    private ArrayList<Queue<Unit>> unitsToSend;
    private ArrayList<Unit> units;
    private ArrayList<com.knightwars.game.environment.Arrow> arrows;

    private float unitSpawnTick;
    private Vector2 size;
    private java.util.Map<String, java.util.Map<String, Integer>> buildingHierarchy;

    /** Map constructor
     * @param width the width of the map
     * @param height the height of the map
     */
    public Map(float width, float height, String yamlUpgradeHierarchyPath) {
        this.size = new Vector2(width, height);
        this.buildings = new ArrayList<>();
        this.units = new ArrayList<>();
        this.unitsToSend = new ArrayList<>();
        this.arrows = new ArrayList<>();
        this.unitSpawnTick = 0f;
        try {
            this.buildingHierarchy = YamlParser.yamlToJavaMap(yamlUpgradeHierarchyPath);
            YamlParser.yamlValidity(this.buildingHierarchy, Building.class, BUILDINGS_LOCATION_PACKAGE);
        } catch (FileNotFoundException | InvalidYamlFormatException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(0);
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

    /** Return the building associated to some coordinates
     * @param coordinates the coordinates of the building to find
     * @return The building associated to the building
     * @throws NoBuildingFoundException is thrown if the coordinates were not able to determine a building
     */
    private int coordinatesToBuildingsIndex(Vector2 coordinates) throws NoBuildingFoundException {
        for (Building building : buildings) {
            if (building.getCoordinates().dst(coordinates) < EPSILON_BUILDING_COORDINATES) {
                return buildings.indexOf(building);
            }
        }
        throw new NoBuildingFoundException("No building has been found.");
    }

    /** Upgrade a building given the coordinates of a building to upgrade and the class of the upgraded building
     * @param coordinatesBuildingToUpgrade Coordinates of the building to upgrade
     * @param buildingUpgradeClass building class of the upgrade
     * @throws InvalidUpgradeException is thrown if the upgrade is invalid regarding the upgrade tree
     * @throws NoBuildingFoundException is thrown if the building can't be found with the given coordinates
     */
    public void upgradeBuilding(Vector2 coordinatesBuildingToUpgrade, Class<? extends Building> buildingUpgradeClass) throws InvalidUpgradeException, NoBuildingFoundException, NotEnoughGoldException {
        upgradeBuilding(this.buildings.get(coordinatesToBuildingsIndex(coordinatesBuildingToUpgrade)), buildingUpgradeClass);
    }

    /** To know if an upgrade is valid or not.
     * @param building is the building to upgrade
     * @param buildingUpgradeClass aimed upgrade
     * @return true if the upgrade is valid and false elsewhere
     */
    private boolean isUpgradable(Building building, Class<?> buildingUpgradeClass) {
        String hierarchyResult = this.buildingHierarchy.get(building.getClass().getSimpleName()).keySet().toString();
        return Arrays.asList(hierarchyResult.substring(1, hierarchyResult.length()-1).split("\\s*,\\s*")).contains(buildingUpgradeClass.getSimpleName());
    }

    /** Get every available upgrade given a building
     * @param building is the upgradable building
     * @return A java Map of available upgrade and their costs in gold. It is empty if there is not any available upgrade.
     */
    public java.util.HashMap<Class<? extends Building>, Integer> availableUpgrade(Building building) {
        java.util.HashMap<Class<? extends Building>, Integer> upgrades = new java.util.HashMap<>();

        if (buildingHierarchy.get(building.getClass().getSimpleName()) == null) return upgrades;

        java.util.Map<String, Integer> upgradeName = buildingHierarchy.get(building.getClass().getSimpleName());

        for(String name : upgradeName.keySet()) {
            try {
                upgrades.put((Class<? extends Building>) Class.forName(BUILDINGS_LOCATION_PACKAGE + "." + name),
                        upgradeName.get(name));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return upgrades;
    }

    /** Get every available upgrade given building's coordinates
     * @param buildingCoordinates Coordinates of the upgradable building
     * @return A list of available upgrade. It is empty if there is not any available upgrade.
     * @throws NoBuildingFoundException is thrown if the building can't be found with the given coordinates
     */
    public java.util.HashMap<Class<? extends Building>, Integer> availableUpgrade(Vector2 buildingCoordinates) throws NoBuildingFoundException {
        return availableUpgrade(this.buildings.get(coordinatesToBuildingsIndex(buildingCoordinates)));
    }

    /** Upgrade a building given a building to upgrade and the class of the upgraded building
     * @param buildingToUpgrade is the building to upgrade
     * @param buildingUpgradeClass is the class of the upgraded building
     * @throws InvalidUpgradeException is thrown if the upgrade is not valid regarding the upgrade tree
     */
    public void upgradeBuilding(Building buildingToUpgrade, Class<? extends Building> buildingUpgradeClass) throws InvalidUpgradeException, NotEnoughGoldException {
        if (!isUpgradable(buildingToUpgrade, buildingUpgradeClass)) {
            throw new InvalidUpgradeException("This building can't be upgraded to such class according to the hierarchy. Please" +
                    " take a look at the yaml file 'building-structure'.");
        } else if (buildingToUpgrade.getOwner().getGold() < buildingHierarchy.get(buildingToUpgrade.getClass().getSimpleName()).get(buildingUpgradeClass.getSimpleName())) {
            throw new NotEnoughGoldException("Not enough gold to upgrade the building.");
        }
        else {
            try {
                buildingToUpgrade.getOwner().removeGold(buildingHierarchy.get(buildingToUpgrade.getClass().getSimpleName()).get(buildingUpgradeClass.getSimpleName()));
                this.buildings.set(this.buildings.indexOf(buildingToUpgrade),
                        buildingUpgradeClass.getConstructor(Building.class).newInstance(buildingToUpgrade));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
