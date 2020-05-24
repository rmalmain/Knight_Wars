package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.InvalidYamlFormatException;
import com.knightwars.game.YamlParser;
import com.knightwars.game.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Map {
    public static final float BUILDING_COLLISION_THRESHOLD = 0.25f;
    public static final float ARROW_COLLISION_THRESHOLD = 0.25f;
    public static final float TIME_BETWEEN_UNITS = 0.15f;
    public static final float EPSILON_BUILDING_COORDINATES = 0.000000001f;
    public static final String BUILDINGS_LOCATION_PACKAGE = "com.knightwars.game.environment.buildings";

    private ArrayList<Building> buildings;
    private List<TroopMovement> movements;
    private ArrayList<com.knightwars.game.environment.Arrow> arrows;

    private Vector2 size;
    private java.util.Map<String, java.util.Map<String, Integer>> buildingHierarchy;

    /**
     * Map constructor
     *
     * @param width  the width of the map
     * @param height the height of the map
     */
    public Map(float width, float height, String yamlUpgradeHierarchyPath) {
        this.size = new Vector2(width, height);
        this.buildings = new ArrayList<>();
        this.arrows = new ArrayList<>();

        this.movements = new ArrayList<>();
        try {
            this.buildingHierarchy = YamlParser.yamlToJavaMap(yamlUpgradeHierarchyPath);
            YamlParser.yamlValidity(this.buildingHierarchy, Building.class, BUILDINGS_LOCATION_PACKAGE);
        } catch (InvalidYamlFormatException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Add a building to the map
     *
     * @param building the building to add
     */
    public void addBuildingCopy(Building building) {
        buildings.add(building.Copy());
    }

    /**
     * Delete a unit from the map
     *
     * @param unit the unit to delete
     */
    public void deleteUnit(Unit unit) {
        if (!unit.getRegiment().removeUnit(unit)) {
            throw new NoUnitFoundException("The unit wasn't found in unit list");
        }
    }

    public Vector2 getSize() {
        return new Vector2(size);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public ArrayList<Unit> getUnits() {
        ArrayList<Unit> units = new ArrayList<>();
        for (TroopMovement movement : this.movements) {
            units.addAll(movement.getUnits());
        }
        return units;
    }

    /**
     * Update each element depending on the map
     *
     * @param dt the time parameter of the update
     */
    public void update(float dt) {

        for (TroopMovement movement : this.movements) {
            movement.update(dt);
        }

        for (Building building : buildings) { // Update buildings
            building.update(dt, this);
        }

        ArrayList<Arrow> arrowsToRemove = new ArrayList<>();

        for (Arrow arrow : arrows) {
            if (arrow.isArrived(ARROW_COLLISION_THRESHOLD)) {
                try {
                    deleteUnit(arrow.getDestinationUnit());
                } catch (NoUnitFoundException nothing) {
                }
                arrowsToRemove.add(arrow);
            } else if (this.getUnits().contains(arrow.getDestinationUnit())) {
                arrow.update(dt);
            } else {
                arrowsToRemove.add(arrow);
            }
        }

        for (Arrow arrow : arrowsToRemove) {
            arrows.remove(arrow);
        }
    }

    /**
     * Send 100*percentage percent from departureBuilding to arrivalBuilding
     *
     * @param departureBuilding departure building
     * @param arrivalBuilding   arrival building
     * @param percentage        percentage of unit to send. Must be between 0 and 1
     */
    public void sendUnit(Building departureBuilding, Building arrivalBuilding, float percentage) {
        if (departureBuilding.getOwner().getColor() != Player.ColorPlayer.NEUTRAL // Neutral castles can't be selected
                                                                                  // as departure buildings
                && departureBuilding != arrivalBuilding) { // Units can't be send to their own building
            if (percentage > 1f || percentage < 0f) {
                throw new RuntimeException("The percentage of unit to send is wrong.");
            }

            // Number of units to be sent
            int number = (int) Math.floor(departureBuilding.getKnights() * percentage);

            // Prevent overlapping regiments
            for (TroopMovement movement : this.movements) {
                if (movement.getDepartureBuilding() == departureBuilding
                        && movement.getDestinationBuilding() == arrivalBuilding && !movement.canSendNextAttack()) {
                    return;
                }
            }

            Path path = Path.findPath(this, departureBuilding, arrivalBuilding);
            TroopMovement movement = new TroopMovement(departureBuilding.getOwner(), departureBuilding, arrivalBuilding,
                    path, number);
            this.movements.add(movement);
        }
    }

    public void sendArrow(Arrow arrow) {
        arrows.add(arrow);
    }

    public ArrayList<Arrow> getArrows() {
        return arrows;
    }

    /**
     * Return the building associated to some coordinates
     *
     * @param coordinates the coordinates of the building to find
     * @return The building associated to the building
     * @throws NoBuildingFoundException is thrown if the coordinates were not able
     *                                  to determine a building
     */
    private int coordinatesToBuildingsIndex(Vector2 coordinates) throws NoBuildingFoundException {
        for (Building building : buildings) {
            if (building.getCoordinates().dst(coordinates) < EPSILON_BUILDING_COORDINATES) {
                return buildings.indexOf(building);
            }
        }
        throw new NoBuildingFoundException("No building has been found.");
    }

    /**
     * Upgrade a building given the coordinates of a building to upgrade and the
     * class of the upgraded building
     *
     * @param coordinatesBuildingToUpgrade Coordinates of the building to upgrade
     * @param buildingUpgradeClass         building class of the upgrade
     * @throws InvalidUpgradeException  is thrown if the upgrade is invalid
     *                                  regarding the upgrade tree
     * @throws NoBuildingFoundException is thrown if the building can't be found
     *                                  with the given coordinates
     */
    public void upgradeBuilding(Vector2 coordinatesBuildingToUpgrade, Class<? extends Building> buildingUpgradeClass)
            throws InvalidUpgradeException, NoBuildingFoundException, NotEnoughGoldException {
        upgradeBuilding(this.buildings.get(coordinatesToBuildingsIndex(coordinatesBuildingToUpgrade)),
                buildingUpgradeClass);
    }

    /**
     * To know if an upgrade is valid or not.
     *
     * @param building             is the building to upgrade
     * @param buildingUpgradeClass aimed upgrade
     * @return true if the upgrade is valid and false elsewhere
     */
    private boolean isUpgradable(Building building, Class<?> buildingUpgradeClass) {
        String hierarchyResult = this.buildingHierarchy.get(building.getClass().getSimpleName()).keySet().toString();
        return Arrays.asList(hierarchyResult.substring(1, hierarchyResult.length() - 1).split("\\s*,\\s*"))
                .contains(buildingUpgradeClass.getSimpleName());
    }

    /**
     * Get every available upgrade given a building
     *
     * @param building is the upgradable building
     * @return A java Map of available upgrade and their costs in gold. It is empty
     *         if there is not any available upgrade.
     */
    public java.util.HashMap<Class<? extends Building>, Integer> availableUpgrade(Building building) {
        java.util.HashMap<Class<? extends Building>, Integer> upgrades = new java.util.HashMap<>();

        if (buildingHierarchy.get(building.getClass().getSimpleName()) == null)
            return upgrades;

        java.util.Map<String, Integer> upgradeName = buildingHierarchy.get(building.getClass().getSimpleName());

        for (String name : upgradeName.keySet()) {
            try {
                upgrades.put((Class<? extends Building>) Class.forName(BUILDINGS_LOCATION_PACKAGE + "." + name),
                        upgradeName.get(name));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return upgrades;
    }

    /**
     * Get every available upgrade given building's coordinates
     *
     * @param buildingCoordinates Coordinates of the upgradable building
     * @return A list of available upgrade. It is empty if there is not any
     *         available upgrade.
     * @throws NoBuildingFoundException is thrown if the building can't be found
     *                                  with the given coordinates
     */
    public java.util.HashMap<Class<? extends Building>, Integer> availableUpgrade(Vector2 buildingCoordinates)
            throws NoBuildingFoundException {
        return availableUpgrade(this.buildings.get(coordinatesToBuildingsIndex(buildingCoordinates)));
    }

    /**
     * Upgrade a building given a building to upgrade and the class of the upgraded
     * building
     *
     * @param buildingToUpgrade    is the building to upgrade
     * @param buildingUpgradeClass is the class of the upgraded building
     * @throws InvalidUpgradeException is thrown if the upgrade is not valid
     *                                 regarding the upgrade tree
     */
    public void upgradeBuilding(Building buildingToUpgrade, Class<? extends Building> buildingUpgradeClass)
            throws InvalidUpgradeException, NotEnoughGoldException {
        if (!isUpgradable(buildingToUpgrade, buildingUpgradeClass)) {
            throw new InvalidUpgradeException(
                    "This building can't be upgraded to such class according to the hierarchy. Please"
                            + " take a look at the yaml file 'building-structure'.");
        } else if (buildingToUpgrade.getOwner().getGold() < buildingHierarchy
                .get(buildingToUpgrade.getClass().getSimpleName()).get(buildingUpgradeClass.getSimpleName())) {
            throw new NotEnoughGoldException("Not enough gold to upgrade the building.");
        } else {
            try {
                buildingToUpgrade.getOwner().removeGold(buildingHierarchy
                        .get(buildingToUpgrade.getClass().getSimpleName()).get(buildingUpgradeClass.getSimpleName()));
                this.buildings.set(this.buildings.indexOf(buildingToUpgrade),
                        buildingUpgradeClass.getConstructor(Building.class).newInstance(buildingToUpgrade));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
