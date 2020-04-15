/*
 * Actor to display the units
 */

package com.knightwars.userInterface.gameActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.game.environment.Building;

import java.util.ArrayList;
import java.util.List;

import static com.knightwars.userInterface.GameScreen.SCALE;

public class GameActorUnits extends Actor {

    private final KnightWarsGame gameState;
    private final Sprite spriteRedUnit;

    public GameActorUnits(KnightWarsGame gameState) {
        this.gameState = gameState;

        // Create the sprites
        spriteRedUnit = new Sprite(new Texture("red_unit.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Fetch the coordinates of the units
        List<Vector2> unitCoordinates = new ArrayList<>();
        unitCoordinates.add(new Vector2(1f, 1f));

        // Draw the units
        for (Vector2 unitCoordinate : unitCoordinates) {
            batch.draw(spriteRedUnit, unitCoordinate.x * SCALE - spriteRedUnit.getWidth() / 2f,
                    unitCoordinate.y * SCALE - spriteRedUnit.getHeight() / 2f);
        }
    }

}
