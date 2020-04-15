package com.knightwars.game.environment;

public class Player {

    public enum ColorPlayer {NEUTRAL, BLUE, RED}

    private int gold;
    private String name;
    private ColorPlayer color;
    private int unitLevel;

    /** Player constructor.
     * @param name The name of the player
     * @param color The color of the player
     */
    public Player(String name, ColorPlayer color) {
        this.name = name;
        this.color = color;
        this.unitLevel = 0;
        this.gold = 50; // Default golds, to modify if necessary
    }

    /* Name getter */
    public String getName() { return this.name; }

    /* Color getter */
    public ColorPlayer getColor() { return this.color; }

    /* Gold getter */
    public int getGold() { return this.gold; }

    public int getUnitLevel() { return this.unitLevel; }

    /** Upgrade the units of a player by a level.
     */
    public void upgradeUnits() { this.unitLevel++; }
}
