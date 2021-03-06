package com.knightwars.game.environment;

import com.knightwars.game.players.Player;

/** Exception raised if the attacker of a castle won the fight
 */

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
