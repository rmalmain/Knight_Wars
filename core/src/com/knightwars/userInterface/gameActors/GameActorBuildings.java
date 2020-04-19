/*
 * Actor to display the buildings and units
 */

package com.knightwars.userInterface.gameActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
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
    private Building selectedBuilding;

    private final static float fontOffsetX = -15f; // Horizontal position offset relative to the building
    private final static float fontOffsetY = 120f; // Vertical position offset relative to the building

    public GameActorBuildings(KnightWarsGame gameState) {
        this.gameState = gameState;

        // Create the sprites
        spriteRedBuilding = new Sprite(new Texture("buildings/red_building.png"));
        spriteBlueBuilding = new Sprite(new Texture("buildings/blue_building.png"));
        spriteNeutralBuilding = new Sprite(new Texture("buildings/neutral_building.png"));
        font = new BitmapFont(Gdx.files.internal("fonts/MontserratBold.ttf.fnt"),
                Gdx.files.internal("fonts/MontserratBold.ttf_0.png"), false);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Fetch the coordinates of the buildings
        List<Building> buildings = gameState.getMap().getBuildings();

        // Draw the buildings
        for (Building building : buildings) {
            Vector2 buildingCoordinates = building.getCoordinates();

            // Draw the building
            Sprite currentSprite = determineBuildingSprite(building);
            batch.draw(currentSprite, buildingCoordinates.x*SCALE - currentSprite.getWidth()/2f,
                    buildingCoordinates.y*SCALE - currentSprite.getHeight()/2f);

            // Draw the number of knights
            font.draw(batch, String.valueOf(building.getKnights()), buildingCoordinates.x*SCALE + fontOffsetX,
                    buildingCoordinates.y*SCALE + fontOffsetY);

            // Display more information about this building if it is selected
            if (building == selectedBuilding) {
                String informations = "Defense : " + (int) building.getDefenseLevel() + "\nGold gen : "
                        + (int) building.getGoldGeneration() + "\nKnight gen : " + (int) building.getKnightGeneration();
                font.draw(batch, informations, buildingCoordinates.x*SCALE - 70f,
                        buildingCoordinates.y*SCALE - 80f, currentSprite.getWidth(), Align.center, false);
            }
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

    /**
     * Display more informations about a building
     * @param selectedBuilding The selected building
     */
    public void showInformation(Building selectedBuilding) {
        this.selectedBuilding = selectedBuilding;
    }

    /**
     * Hide the information on the screen
     */
    public void hideInformation() {
        selectedBuilding = null;
    }
}