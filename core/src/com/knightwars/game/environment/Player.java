package com.knightwars.game.environment;

public class Player {

    public enum ColorPlayer {NEUTRAL, BLUE, RED}

    private int gold;
    private String name;
    private ColorPlayer color;

    public Player(String name, ColorPlayer color) {
        this.name = name;
        this.color = color;

        this.gold = 50; // Default golds, to modify if necessary
    }

    /* Name getter */
    public String getName() { return this.name; }

    /* Color getter */
    public ColorPlayer getColor() { return this.color; }

    /* Gold getter */
    public int getGold() { return this.gold; }
}
