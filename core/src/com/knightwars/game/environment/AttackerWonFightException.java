package com.knightwars.game.environment;

public class AttackerWonFightException extends Exception {

    private final Player attackingPlayer;
    private final Building attackedBuilding;

    public AttackerWonFightException(String message, Player attackingPlayer, Building attackedBuilding) {
        super(message);
        this.attackingPlayer = attackingPlayer;
        this.attackedBuilding = attackedBuilding;
    }

    public Player getAttackingPlayer() {
        return attackingPlayer;
    }

    public Building getAttackedBuilding() {
        return attackedBuilding;
    }
}
