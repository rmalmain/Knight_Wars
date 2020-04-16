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

    private static final float WALK_ANIM_WIDTH = 100f;
    private static final float WALK_ANIM_HEIGHT = 100f;

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

        // Draw the units
        for (Unit unit : units) {
            Vector2 unitCoordinate = unit.getCoordinates();
            elapsedTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentUnitFrame = unitAnimation.getKeyFrame(elapsedTime, true);
            batch.draw(currentUnitFrame, unitCoordinate.x*SCALE - WALK_ANIM_WIDTH/2f,
                    unitCoordinate.y*SCALE - WALK_ANIM_HEIGHT/2f, WALK_ANIM_WIDTH, WALK_ANIM_HEIGHT);
        }
    }
}
