/*
 * Actor to display the units
 */

package com.knightwars.userInterface.gameActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.game.environment.Unit;

import java.util.ArrayList;

import static com.knightwars.userInterface.GameScreen.SCALE;

public class GameActorUnits extends Actor {

    private final KnightWarsGame gameState;
    private final Animation<TextureRegion> unitAnimation;
    private ArrayList<Unit> units;
    private float elapsedTime = 0;

    private static final float WALK_ANIM_WIDTH = 41.5f;
    private static final float WALK_ANIM_HEIGHT = 50f;

    public GameActorUnits(KnightWarsGame gameState) {
        this.gameState = gameState;

        // Create the sprites
        TextureAtlas unitAtlas = new TextureAtlas(Gdx.files.internal("units/walk.atlas"));
        unitAnimation = new Animation<TextureRegion>(1/10f, unitAtlas.getRegions());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Fetch the coordinates of the units
        units = gameState.getMap().getUnits();
        elapsedTime += Gdx.graphics.getDeltaTime();

        // Draw the units
        for (Unit unit : units) {
            // Get the current frame of the unit animation to display
            TextureRegion currentUnitFrame = unitAnimation.getKeyFrame(elapsedTime, true);

            // Compute the unit's coordinates on the screen
            float unitScreenX = unit.getCoordinates().x*SCALE - WALK_ANIM_WIDTH/2f;
            float unitScreenY = unit.getCoordinates().y*SCALE - WALK_ANIM_HEIGHT/2f;

            // If the unit is going in the left direction, flip the texture
            boolean flip = unit.getDepartureBuilding().getCoordinates().x > unit.getDestinationBuilding().getCoordinates().x;

            // Draw the unit on the screen
            batch.draw(currentUnitFrame, flip ? unitScreenX + WALK_ANIM_WIDTH : unitScreenX,
                    unitScreenY, flip ? -WALK_ANIM_WIDTH : WALK_ANIM_WIDTH, WALK_ANIM_HEIGHT);
        }
    }
}
