/*
 * Actor to display the HUD
 */

package com.knightwars.userInterface;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.knightwars.game.KnightWarsGame;

public class GameScreenHUD extends Actor {

    private final KnightWarsGame gameState;

    public GameScreenHUD(KnightWarsGame gameState) {
        this.gameState = gameState;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }

}
