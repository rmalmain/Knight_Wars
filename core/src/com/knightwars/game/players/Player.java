package com.knightwars.game.players;

import com.knightwars.game.KnightWarsGame;
import com.knightwars.game.environment.NotEnoughGoldException;

/**
 * A base class for all types of players.
 */
abstract public class Player {

    private float gold;
    private final String name;
    private final ColorPlayer color;
    private float unitLevel;
    private float unitPercentage;

    /**
     * Player constructor.
     *
     * @param name  The name of the player
     * @param color The color of the player
     */
    public Player(String name, ColorPlayer color) {
        this.name = name;
        this.color = color;
        this.unitLevel = 1f;
        this.gold = 5000f; // Default golds, to modify if necessary
        this.unitPercentage = 0.25f; // Default percentage of units to send
    }

    /* Name getter */
    public String getName() {
        return this.name;
    }

    /* Color getter */
    public ColorPlayer getColor() {
        return this.color;
    }

    /* Gold getter */
    public float getGold() {
        return (float) Math.floor(this.gold);
    }

    /**
     * Add golds to the player
     *
     * @param gold golds to give at the player.
     */
    public void addGold(float gold) {
        this.gold += gold;
    }

    /**
     * remove golds from the player
     *
     * @param gold golds to remove
     * @throws NotEnoughGoldException raised if the player has not enough golds
     */
    public void removeGold(float gold) throws NotEnoughGoldException {
        if (this.gold < gold) {
            throw new NotEnoughGoldException("The player has not enough gold.");
        } else {
            this.gold -= gold;
        }
    }

    public void setGold(float gold) {
        this.gold = gold;
    }

    public float getUnitLevel() {
        return this.unitLevel;
    }

    /**
     * Upgrade the units of a player by a level.
     */
    public void upgradeUnits() {
        this.unitLevel++;
    }

    public float getUnitPercentage() {
        return this.unitPercentage;
    }

    public void setUnitPercentage(float unitPercentage) {
        this.unitPercentage = unitPercentage;
    }

    /**
     * Offers the player the opportunity to make a move.
     * Called about 60 times a second.
     *
     * @param game Current game
     */
    public void makeMoves(KnightWarsGame game) {
        // We may want to have something like public List<Move> makeMoves(...) in the future
    }

    public enum ColorPlayer {NEUTRAL, BLUE, RED}
}
