/*
 * Actor to display the HUD
 */

package com.knightwars.userInterface.gameActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.userInterface.GameScreen;

public class GameActorHUD extends Actor {

    private final BitmapFont font;
    private final KnightWarsGame gameState;
    private Arrow arrow;
    private final Sprite spriteArrow;

    public GameActorHUD(final KnightWarsGame gameState, Stage stage) {
        this.gameState = gameState;

        // Load the textures and font
        spriteArrow = new Sprite(new Texture("hud/arrow.png"));
        Skin skin = new Skin(Gdx.files.internal("buttons/glassy-ui.json"));
        font = new BitmapFont(Gdx.files.internal("fonts/MontserratBold.ttf.fnt"),
                Gdx.files.internal("fonts/MontserratBold.ttf_0.png"), false);

        // Create the check boxes to select the percentage of units to send to battle
        // and add them to a button group so only one checkbox can be checked at a time
        ButtonGroup<CheckBox> buttonGroup = new ButtonGroup<>();
        for (int i=0; i < 4; i++) {
            buttonGroup.add(new CheckBox("", skin, "radio"));
        }

        // Create a table to position the check boxes
        Table percentageTable = new Table();

        // Check box properties
        final int checkBoxWidth = 100;
        final int checkBoxHeight = 85;
        final int checkBoxPadding = 10;
        final int checkBoxPosX = 100;
        final int checkBoxPosY = 600;

        int index = 1;
        for (CheckBox checkBox : buttonGroup.getButtons()) {
            // Change the percentage when the check box is clicked
            final int finalIndex = index; // So index can be accessed from the inner class
            checkBox.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameState.getPlayerBlue().setUnitPercentage(finalIndex * 0.25f);
                }
            });

            // Set the size of the checkbox
            checkBox.getImage().setScaling(Scaling.stretch);
            checkBox.getCells().get(0).size(checkBoxWidth,checkBoxHeight);

            // Add the check box to the table
            percentageTable.add(checkBox).pad(checkBoxPadding).row();

            index ++;
        }

        // Set the max and min amount of buttons to be checked to 1
        buttonGroup.setMaxCheckCount(1);
        buttonGroup.setMinCheckCount(1);
        buttonGroup.setUncheckLast(true); // Uncheck last button if a new one is checked

        // Set the check box position
        percentageTable.setPosition(checkBoxPosX, checkBoxPosY);

        // Add the table to the stage so it is drawn on the screen
        stage.addActor(percentageTable);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw an arrow between the selected building and the mouse cursor
        if (arrow != null && arrow.exists()) {
            batch.draw(spriteArrow, arrow.getOriginX() * GameScreen.SCALE, arrow.getOriginY() * GameScreen.SCALE,
                    0, 0, arrow.getWidth(), arrow.getHeight(),
                    1, 1, arrow.getRotation());
        }

        // Display the current amount of gold of the player
        font.draw(batch, "Gold : " + (int) gameState.getPlayerBlue().getGold(), 50f, 950f);

        // Display the percentages in the checkboxes
        final float percentagePosX = 58f;
        for (int i=1; i < 5; i ++) {
            final float percentagePosY = 765f - (i-1)*104f;
            font.draw(batch, "" + i*25 + "%", percentagePosX, percentagePosY);
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
