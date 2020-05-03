/*
 * Actor to display the buildings and units
 */

package com.knightwars.userInterface.gameActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.game.environment.Building;
import com.knightwars.game.environment.InvalidUpgradeException;
import com.knightwars.game.players.Player;
import com.knightwars.userInterface.UnknownPlayerException;

import java.util.ArrayList;
import java.util.List;

import static com.knightwars.userInterface.GameScreen.SCALE;

public class GameActorBuildings extends Actor {

    private final Sprite spriteRedBuilding;
    private final Sprite spriteBlueBuilding;
    private final Sprite spriteNeutralBuilding;
    private final KnightWarsGame gameState;
    private final BitmapFont font;
    private Building selectedBuilding;
    private final Table upgradeTable;
    private final List<TextButton> upgradeButtons;
    private ArrayList<Class<? extends Building>> availableUpgrades;

    private final static float fontOffsetX = -15f; // Horizontal position offset relative to the building
    private final static float fontOffsetY = 120f; // Vertical position offset relative to the building
    private final static float buttonUpgradeHeight = 70f;
    private final static float buttonUpgradeWidth = 150f;
    private final static float buttonUpgradePadding = 10f;
    private final TextField buildingText;

    public GameActorBuildings(final KnightWarsGame gameState, Stage stage) {
        this.gameState = gameState;

        // Load the sprites
        spriteRedBuilding = new Sprite(new Texture("buildings/red_building.png"));
        spriteBlueBuilding = new Sprite(new Texture("buildings/blue_building.png"));
        spriteNeutralBuilding = new Sprite(new Texture("buildings/neutral_building.png"));
        font = new BitmapFont(Gdx.files.internal("fonts/MontserratBold.ttf.fnt"),
                Gdx.files.internal("fonts/MontserratBold.ttf_0.png"), false);

        // Add the buttons to upgrade buildings
        upgradeTable = new Table();
        upgradeButtons = new ArrayList<>();
        Skin skin = new Skin(Gdx.files.internal("buttons/glassy-ui.json"));
        for (int i = 0; i < 4; i++) {
            TextButton upgradeButton = new TextButton("", skin, "small");
            upgradeTable.add(upgradeButton).width(buttonUpgradeWidth).height(buttonUpgradeHeight).pad(buttonUpgradePadding);
            if (i%2 == 1) upgradeTable.row();
            upgradeButtons.add(upgradeButton);

            final int finalI = i;
            upgradeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    try {
                        if (selectedBuilding != null) {
                            gameState.getMap().upgradeBuilding(selectedBuilding, availableUpgrades.get(finalI));
                        }
                    } catch (IndexOutOfBoundsException | InvalidUpgradeException ignored) {
                    } finally {
                        upgradeTable.setVisible(false);
                    }
                }
            });
        }
        buildingText = new TextField("", skin);
        buildingText.setAlignment(Align.center);
        buildingText.setDisabled(true);
        upgradeTable.add(buildingText).colspan(2);
        upgradeTable.setVisible(false);
        stage.addActor(upgradeTable);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Fetch the coordinates of the buildings
        List<Building> buildings = gameState.getMap().getBuildings();

        // Draw the buildings
        for (Building building : buildings) {
            // Draw the building
            Sprite currentSprite = determineBuildingSprite(building);
            batch.draw(currentSprite, building.getCoordinates().x*SCALE - currentSprite.getWidth()/2f,
                    building.getCoordinates().y*SCALE - currentSprite.getHeight()/2f);

            // Draw the number of knights in the building
            font.draw(batch, String.valueOf(building.getKnights()), building.getCoordinates().x*SCALE + fontOffsetX,
                    building.getCoordinates().y*SCALE + fontOffsetY);

            // Display upgrade options if the building is selected
            if (building == selectedBuilding) {
                upgradeTable.setPosition(building.getCoordinates().x*SCALE, building.getCoordinates().y*SCALE,
                        Align.center);
                upgradeTable.draw(batch, parentAlpha);
            }
        }
    }

    /**
     * Get the sprite for a given building
     * @param building The building
     * @return The corresponding sprite
     */
    private Sprite determineBuildingSprite(Building building) {
        if (building.getOwner().getColor() == Player.ColorPlayer.RED) {
            return spriteRedBuilding;
        } else if (building.getOwner().getColor() == Player.ColorPlayer.BLUE) {
            return spriteBlueBuilding;
        } else if (building.getOwner().getColor() == Player.ColorPlayer.NEUTRAL) {
            return spriteNeutralBuilding;
        } else {
            throw new UnknownPlayerException("There is no player associated with the building.");
        }
    }

    /**
     * Display upgrade options
     * @param selectedBuilding The selected building
     */
    public void showUpgrade(Building selectedBuilding) {
        if (selectedBuilding.getOwner().getColor() == Player.ColorPlayer.BLUE) {
            this.selectedBuilding = selectedBuilding;
            buildingText.setText(selectedBuilding.getClass().getSimpleName());
            availableUpgrades = gameState.getMap().availableUpgrade(selectedBuilding);
                for (int i = 0; i < 4; i++) {
                    try {
                    upgradeButtons.get(i).setText(availableUpgrades.get(i).getSimpleName());
                    } catch (IndexOutOfBoundsException e) {
                        upgradeButtons.get(i).setText("");
                    }
                }
            upgradeTable.setVisible(true);
        }
    }

    /**
     * Hide the upgrade options
     */
    public void hideUpgrade() {
        selectedBuilding = null;
        upgradeTable.setVisible(false);
    }
}
