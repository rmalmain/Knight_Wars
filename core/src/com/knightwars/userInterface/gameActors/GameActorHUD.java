/*
 * Actor to display the HUD
 */

package com.knightwars.userInterface.gameActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.knightwars.game.KnightWarsGame;

public class GameActorHUD extends Actor {

    private final KnightWarsGame gameState;

    public GameActorHUD(KnightWarsGame gameState) {
        this.gameState = gameState;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }

}
