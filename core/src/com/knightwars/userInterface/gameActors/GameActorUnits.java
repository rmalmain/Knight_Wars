/*
 * Actor to display the units
 */

package com.knightwars.userInterface.gameActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.game.environment.Unit;
import com.knightwars.game.players.Player;

import java.util.ArrayList;

import static com.knightwars.userInterface.GameScreen.SCALE;

public class GameActorUnits extends Actor {

    private final KnightWarsGame gameState;
    private final Animation<TextureRegion> unitAnimationRed;
    private final Animation<TextureRegion> unitAnimationBlue;
    private float elapsedTime = 0;

    private static final float WALK_ANIM_WIDTH = 41.5f;
    private static final float WALK_ANIM_HEIGHT = 50f;

    public GameActorUnits(KnightWarsGame gameState) {
        this.gameState = gameState;

        // Create the sprites
        TextureAtlas unitAtlasRed = new TextureAtlas(Gdx.files.internal("units/walkRed.atlas"));
        unitAnimationRed = new Animation<TextureRegion>(1/15f, unitAtlasRed.getRegions());

        TextureAtlas unitAtlasBlue = new TextureAtlas(Gdx.files.internal("units/walkBlue.atlas"));
        unitAnimationBlue = new Animation<TextureRegion>(1/15f, unitAtlasBlue.getRegions());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Fetch the coordinates of the units
        ArrayList<Unit> units = gameState.getMap().getUnits();
        elapsedTime += Gdx.graphics.getDeltaTime();

        // Draw the units
        for (Unit unit : units) {
            // Get the current frame of the unit animation to display
            TextureRegion currentUnitFrame;
            if (unit.getOwner().getColor() == Player.ColorPlayer.RED) {
                currentUnitFrame = unitAnimationRed.getKeyFrame(elapsedTime, true);
            } else {
                currentUnitFrame = unitAnimationBlue.getKeyFrame(elapsedTime, true);
            }
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
