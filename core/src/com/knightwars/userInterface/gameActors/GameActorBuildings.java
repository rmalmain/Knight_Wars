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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.game.environment.Arrow;
import com.knightwars.game.environment.Building;
import com.knightwars.game.environment.InvalidUpgradeException;
import com.knightwars.game.environment.NotEnoughGoldException;
import com.knightwars.game.environment.buildings.*;
import com.knightwars.game.players.Player;
import com.knightwars.userInterface.UnknownBuildingException;
import com.knightwars.userInterface.UnknownPlayerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.knightwars.userInterface.GameScreen.SCALE;

public class GameActorBuildings extends Actor {

    private final HashMap<Class<? extends Building>, Sprite> redSpritesBuildings;
    private final HashMap<Class<? extends  Building>, Sprite> blueSpritesBuildings;
    private final Sprite spriteNeutralBuilding;
    private final KnightWarsGame gameState;
    private final BitmapFont font;
    private final Sprite spriteArrow;
    private final Sprite spriteRange;
    private Building selectedBuilding;
    private final Table upgradeTable;
    private final List<TextButton> upgradeButtons;
    private ArrayList<Class<? extends Building>> availableUpgradesClassList;

    private final static float fontOffsetX = -15f; // Horizontal position offset relative to the building
    private final static float fontOffsetY = 120f; // Vertical position offset relative to the building
    private final static float buttonUpgradeHeight = 70f;
    private final static float buttonUpgradeWidth = 150f;
    private final static float buttonUpgradePadding = 10f;
    private final TextField buildingText;

    public GameActorBuildings(final KnightWarsGame gameState, Stage stage) {
        this.gameState = gameState;
        // Load the sprites
        redSpritesBuildings = new HashMap<>();
        blueSpritesBuildings = new HashMap<>();

        redSpritesBuildings.put(ClassicCastle.class, new Sprite(new Texture("buildings/red_building.png")));
        redSpritesBuildings.put(FortifiedCastle.class, new Sprite(new Texture("buildings/red_fortified_castle.png")));
        redSpritesBuildings.put(ForgeCastle1.class, new Sprite(new Texture("buildings/red_forge.png")));
        redSpritesBuildings.put(ForgeCastle2.class, new Sprite(new Texture("buildings/red_forge.png")));
        redSpritesBuildings.put(GarrisonCastle1.class, new Sprite(new Texture("buildings/red_building.png")));
        redSpritesBuildings.put(GarrisonCastle2.class, new Sprite(new Texture("buildings/red_building.png")));
        redSpritesBuildings.put(CitadelCastle1.class, new Sprite(new Texture("buildings/red_citadel.png")));
        redSpritesBuildings.put(CitadelCastle2.class, new Sprite(new Texture("buildings/red_citadel.png")));
        redSpritesBuildings.put(MarketCastle1.class, new Sprite(new Texture("buildings/red_market1.png")));
        redSpritesBuildings.put(MarketCastle2.class, new Sprite(new Texture("buildings/red_market2.png")));


        blueSpritesBuildings.put(ClassicCastle.class, new Sprite(new Texture("buildings/blue_building.png")));
        blueSpritesBuildings.put(FortifiedCastle.class, new Sprite(new Texture("buildings/blue_fortified_castle.png")));
        blueSpritesBuildings.put(ForgeCastle1.class, new Sprite(new Texture("buildings/blue_forge.png")));
        blueSpritesBuildings.put(ForgeCastle2.class, new Sprite(new Texture("buildings/blue_forge.png")));
        blueSpritesBuildings.put(GarrisonCastle1.class, new Sprite(new Texture("buildings/blue_building.png")));
        blueSpritesBuildings.put(GarrisonCastle2.class, new Sprite(new Texture("buildings/blue_building.png")));
        blueSpritesBuildings.put(CitadelCastle1.class, new Sprite(new Texture("buildings/blue_citadel.png")));
        blueSpritesBuildings.put(CitadelCastle2.class, new Sprite(new Texture("buildings/blue_citadel.png")));
        blueSpritesBuildings.put(MarketCastle1.class, new Sprite(new Texture("buildings/blue_market1.png")));
        blueSpritesBuildings.put(MarketCastle2.class, new Sprite(new Texture("buildings/blue_market2.png")));


        spriteNeutralBuilding = new Sprite(new Texture("buildings/neutral_building.png"));
        spriteArrow = new Sprite(new Texture("buildings/arrow.png"));
        spriteRange = new Sprite(new Texture("buildings/range.png"));

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
                            gameState.getMap().upgradeBuilding(selectedBuilding, availableUpgradesClassList.get(finalI));
                        }
                    } catch (IndexOutOfBoundsException | InvalidUpgradeException | NotEnoughGoldException ignored) {
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
            // Show range of citadel
            if (building instanceof CitadelCastle1) {
                float range = 2*((CitadelCastle1) building).getBuildingRange();
                batch.draw(spriteRange, building.getCoordinates().x*SCALE - range*SCALE/2f,
                        building.getCoordinates().y*SCALE - range*SCALE/2f, range*SCALE, range*SCALE);
            }

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

        // Draw arrows shot by citadels
        ArrayList<Arrow> arrows = gameState.getMap().getArrows();
        for (Arrow arrow : arrows) {
            batch.draw(spriteArrow, arrow.getCoordinates().x*SCALE - spriteArrow.getWidth()/2f,
                    arrow.getCoordinates().y*SCALE - spriteArrow.getHeight()/2f);
        }
    }

    /**
     * Get the sprite for a given building
     * @param building The building
     * @return The corresponding sprite
     */
    private Sprite determineBuildingSprite(Building building) {
        Sprite spriteReturned;
        if (building.getOwner().getColor() == Player.ColorPlayer.RED) {
              spriteReturned = redSpritesBuildings.get(building.getClass());
        } else if (building.getOwner().getColor() == Player.ColorPlayer.BLUE /*&& is a classic castle*/) {
            spriteReturned = blueSpritesBuildings.get(building.getClass());
        } else if (building.getOwner().getColor() == Player.ColorPlayer.NEUTRAL /*&& is a classic castle*/) {
            spriteReturned = spriteNeutralBuilding;
        } else {
            throw new UnknownPlayerException("There is no player associated with the building.");
        }
        if (spriteReturned == null){
            throw new UnknownBuildingException("There is no sprite associated with this building.");
        }
        return spriteReturned;
    }

    /**
     * Display upgrade options
     * @param selectedBuilding The selected building
     */
    public void showUpgrade(Building selectedBuilding) {
        if (selectedBuilding.getOwner().getColor() == Player.ColorPlayer.BLUE) {
            this.selectedBuilding = selectedBuilding;
            buildingText.setText(selectedBuilding.getClass().getSimpleName());
            Map<Class<? extends Building>, Integer> availableUpgradesMap =
                    gameState.getMap().availableUpgrade(selectedBuilding);
            availableUpgradesClassList = new ArrayList<>(availableUpgradesMap.keySet());
            int i = 0;
            for (Map.Entry<Class<? extends Building>, Integer> entry : availableUpgradesMap.entrySet()) {
                upgradeButtons.get(i).setText(entry.getKey().getSimpleName() + "\n" + entry.getValue());
                i ++;
            }
            while (i < 4) {
                upgradeButtons.get(i).setText("");
                i ++;
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
