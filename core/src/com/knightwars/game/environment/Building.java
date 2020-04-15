package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;

public class Building {
    private Player owner;
    private Vector2 coordinates;
    private int knights;
    private float hitPoints;
    private float goldGeneration;

    /** Building constructor.
     * @param owner the owner of the building
     * @param coordinates the coordinates of the building
     * @param knights the number of knights in the building
     */
    public Building(Player owner, Vector2 coordinates, int knights) {
        this.owner = owner;
        this.coordinates = new Vector2(coordinates);
        this.knights = knights;
        this.hitPoints = (float) knights;
        this.goldGeneration = 1;
    }

    /** Copy a building.
     * @param building the building to copy
     */
    public Building(Building building) {
        this.owner = building.owner;
        this.coordinates = new Vector2(building.coordinates);
        this.knights = building.knights;
        this.hitPoints = building.getHitPoints();
        this.goldGeneration = building.getGoldGeneration();
    }

    /** owner setter
     * @param player the owner of the building
     */
    public void setOwner(Player player) { this.owner = player; }

    /** Owner getter */
    public Player getOwner() { return this.owner; }

    /** Coordinates getter */
    public Vector2 getCoordinates() { return new Vector2(this.coordinates); }

    public float getGoldGeneration() { return this.goldGeneration; }

    /** Number of knights setter
     * @param knights The number of knights of the building
     */
    public void setKnights(int knights) {
        this.knights = knights;
        this.hitPoints = (float) this.knights;
    }

    /** Hit Points setter
     * @param hitPoints the number of hit points of the building
     */
    public void setHitPoints(float hitPoints) {
        this.hitPoints = hitPoints;
        this.knights = (int) Math.ceil(this.hitPoints);
    }

    /** Hit points getter
     * @return the number of hit points of the building
     */
    public float getHitPoints() { return this.hitPoints; }

    /** update building-related objects
     * @param dt time parameter
     */
    public void update(float dt) {
        this.owner.addGold(goldGeneration*dt);
    }

    /** Knights number getter */
    public int getKnights () { return this.knights; }

    /** Called when a unit arrives near the destination building
     * @param unit The unit which arrived near the building
     */
    public void unitArrival(Unit unit) {
        if (unit.getOwner() == this.owner) {
            setKnights(this.knights + 1);
        }
        else {
            this.fight(unit);
        }
    }

    /** Called when a unit fights with a building
     * @param unit the unit which fight with the building
     */
    private void fight(Unit unit) {
        if (unit.getOwner().getUnitLevel() == this.owner.getUnitLevel()) {
            setHitPoints(this.hitPoints - 1f);
        }
        else if (unit.getOwner().getUnitLevel() > this.owner.getUnitLevel()) {
            setHitPoints(this.hitPoints - 2f);
        }
        else {
            setHitPoints(this.hitPoints - 0.5f);
        }
    }
}
