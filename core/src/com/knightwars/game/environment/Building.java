package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;

public class Building {
    private Player owner;
    private Vector2 coordinates;
    private int knights;
    private float hitPoints;
    private float goldGeneration;
    private float defenceLevel;

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
        this.defenceLevel = 1f;
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
        this.defenceLevel = building.getDefenceLevel();
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

    public void takeDamage(float hitPoints) {
        this.setHitPoints(this.getHitPoints() - hitPoints);
    }

    /** Hit points getter.
     * @return the number of hit points of the building
     */
    public float getHitPoints() { return this.hitPoints; }

    /** update building-related objects.
     * @param dt time parameter
     */
    public void update(float dt) {
        this.owner.addGold(this.getGoldGeneration()*dt);
    }

    /** Knights number getter. */
    public int getKnights () { return this.knights; }

    public float getDefenceLevel() { return this.defenceLevel; }

    /** get total defence points of a building.
     * The formula is totalDefence = BuildingDefence + allyUnitLevel
     * @return the result of the above formula
     */
    public float getTotalDefence() { return (this.defenceLevel + this.owner.getUnitLevel()); }

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

    /** Called when a unit fights with a building.
     * @param unit the unit which fight with the building
     */
    private void fight(Unit unit) {
        takeDamage(determineFightDamages(unit.getTotalAttack(), this.getTotalDefence()));
    }

    /** Determine damage taken in a fight between a unit and a building
     * chosen function : damageTaken = b*exp(-a*(totalDefence - enemyTotalAttack))
     * a and b are choosen such as:
     *      damageTaken = 0.8 when (totalDefence - enemyTotalAttack) = 1
     *      damageTaken = 1.3 when (totalDefence - enemyTotalAttack) = -1
     * Very likely to change in near future, the formula requires in-depth testing
     * @param enemyTotalAttack Total attack of the unit attacking the building
     * @param totalDefence Total level of the building being under attack
     * @return The damages taken by the castle given the attack of the unit and the defence of the building
     */
    private float determineFightDamages (float enemyTotalAttack, float totalDefence) {
        double a = Math.sqrt(26f)/5f;
        double b = 0.2428f;

        double functionParameter = totalDefence - enemyTotalAttack;

        return (float) (b * Math.exp(-a * functionParameter));
    }
}
