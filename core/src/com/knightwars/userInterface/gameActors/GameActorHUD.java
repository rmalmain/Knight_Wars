/*
 * Actor to display the HUD
 */

package com.knightwars.userInterface.gameActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.userInterface.GameScreen;

public class GameActorHUD extends Actor {

    private final Sprite spriteArrow;
    private Arrow arrow;

    private final KnightWarsGame gameState;

    public GameActorHUD(KnightWarsGame gameState) {
        this.gameState = gameState;
        spriteArrow = new Sprite(new Texture("hud/arrow.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (arrow != null && arrow.exists()) {
            batch.draw(spriteArrow, arrow.getOriginX() * GameScreen.SCALE, arrow.getOriginY() * GameScreen.SCALE,
                    0, 0,
                    arrow.getWidth(), arrow.getHeight(), 1, 1, arrow.getRotation());
            batch.draw(spriteArrow, arrow.getOriginX() * GameScreen.SCALE,
                    arrow.getOriginY() * GameScreen.SCALE + 0, 10, 10);
        }
    }

    public void createArrow(Vector2 selectedBuildingCoords, Vector2 currentCoords) {
        if (arrow == null) {
            arrow = new Arrow(selectedBuildingCoords, currentCoords);
        }
        else {
            arrow.setCoords(selectedBuildingCoords, currentCoords);
            arrow.show();
        }
    }

    public void deleteArrow() {
        if (arrow != null) {
            arrow.hide();
        }
    }
}
