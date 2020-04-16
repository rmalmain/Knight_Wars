/*
 * Actor to display the buildings and units
 */

package com.knightwars.userInterface.gameActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.game.environment.Building;
import com.knightwars.userInterface.UnknownPlayerException;

import java.util.List;

import static com.knightwars.userInterface.GameScreen.SCALE;

public class GameActorBuildings extends Actor {

    private final Sprite spriteRedBuilding;
    private final Sprite spriteBlueBuilding;
    private final Sprite spriteNeutralBuilding;
    private final KnightWarsGame gameState;
    private final BitmapFont font;

    public GameActorBuildings(KnightWarsGame gameState) {
        this.gameState = gameState;

        // Create the sprites
        spriteRedBuilding = new Sprite(new Texture("buildings/red_building.png"));
        spriteBlueBuilding = new Sprite(new Texture("buildings/blue_building.png"));
        spriteNeutralBuilding = new Sprite(new Texture("buildings/neutral_building.png"));
        font = new BitmapFont();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Fetch the coordinates of the buildings
        List<Building> buildings = gameState.getMap().getBuildings();

        // Draw the buildings
        for (Building building : buildings) {
            Vector2 buildingCoordinates = building.getCoordinates();
            // Draw the building
            batch.draw(determineBuildingSprite(building), buildingCoordinates.x * SCALE - spriteRedBuilding.getWidth() / 2f,
                    buildingCoordinates.y * SCALE - spriteRedBuilding.getHeight() / 2f);
            // Draw the number of knights
            font.draw(batch, String.valueOf(building.getKnights()), buildingCoordinates.x * SCALE,
                    buildingCoordinates.y * SCALE + spriteRedBuilding.getHeight());
        }
    }

    /**
     * Get the sprite for a given building
     * @param building The building
     * @return The corresponding sprite
     */
    private Sprite determineBuildingSprite(Building building) {
        if (building.getOwner() == gameState.getPlayerRed()) {
            return spriteRedBuilding;
        } else if (building.getOwner() == gameState.getPlayerBlue()) {
            return spriteBlueBuilding;
        } else if (building.getOwner() == gameState.getPlayerNeutral()) {
            return spriteNeutralBuilding;
        } else {
            throw new UnknownPlayerException("There is no player associated with the building.");
        }
    }
}