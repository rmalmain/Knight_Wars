package com.knightwars.game.environment;

public class Player {

    public enum ColorPlayer {NEUTRAL, BLUE, RED}

    private float gold;
    private String name;
    private ColorPlayer color;
    private float unitLevel;
    private float unitPercentage;

    /** Player constructor.
     * @param name The name of the player
     * @param color The color of the player
     */
    public Player(String name, ColorPlayer color) {
        this.name = name;
        this.color = color;
        this.unitLevel = 0f;
        this.gold = 50f; // Default golds, to modify if necessary
        this.unitPercentage = 0.25f; // Default percentage of units to send
    }

    /* Name getter */
    public String getName() { return this.name; }

    /* Color getter */
    public ColorPlayer getColor() { return this.color; }

    /* Gold getter */
    public float getGold() { return this.gold; }

    /** Add golds to the player
     * @param gold golds to give at the player.
     */
    public void addGold(float gold) { this.gold += gold; }

    /** remove golds from the player
     * @param gold golds to remove
     * @throws NotEnoughGoldException raised if the player has not enough golds
     */
    public void removeGold(float gold) throws NotEnoughGoldException {
        if (this.gold < gold) {
            throw new NotEnoughGoldException("The player has not enough gold.");
        }
        else {
            this.gold -= gold;
        }
    }

    public float getUnitLevel() { return this.unitLevel; }

    /** Upgrade the units of a player by a level.
     */
    public void upgradeUnits() { this.unitLevel++; }

    public void setUnitPercentage(float unitPercentage) {
        this.unitPercentage = unitPercentage;
    }

    public float getUnitPercentage() {
        return this.unitPercentage;
    }
}
