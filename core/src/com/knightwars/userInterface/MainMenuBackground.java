package com.knightwars.userInterface;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.knightwars.userInterface.GameScreen.SCALE;

public class MainMenuBackground extends Actor {

    private final Camera camera;
    private Vector2 mapSize;
    private final Texture background;
    private final Texture title;

    public MainMenuBackground(Camera camera, Vector2 mapSize) {
        this.camera = camera;
        this.mapSize = mapSize;
        background = new Texture("menu/menu-background.jpg");
        title = new Texture("menu/title.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Update the camera and the inverse window projection
        batch.setProjectionMatrix(camera.combined);

        // Draw the menu
        final float titlePosY = 300f;   // Vertical position of the game title
        final float titleOffsetX = 30f; // Horizontal offset of the title
        batch.draw(background, 0, 0,mapSize.x*SCALE, mapSize.y*SCALE);
        batch.draw(title, mapSize.x*SCALE/2f - title.getWidth()/2f - titleOffsetX, titlePosY);
    }
}
