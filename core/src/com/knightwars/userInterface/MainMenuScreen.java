package com.knightwars.userInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.knightwars.game.GameManager;

import static com.knightwars.userInterface.GameScreen.SCALE;

public class MainMenuScreen implements Screen {

    private final FillViewport viewport;
    private final Stage stage;


    public MainMenuScreen(final Display display, GameManager gameManager) {
        SpriteBatch batch = new SpriteBatch();

        // Fetch the map size from the game state
        Vector2 mapSize = gameManager.getKnightWarsGame().getMap().getSize();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FillViewport(mapSize.x * SCALE, mapSize.y * SCALE, camera);

        // Add the skin of the buttons
        Skin skin = new Skin(Gdx.files.internal("buttons/glassy-ui.json"));

        // Create the stage
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // Create the play button
        TextButton playButton = new TextButton("Play", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.dispose();
                display.displayGame();
            }
        });

        // Create the options button
        TextButton optionsButton = new TextButton("Settings", skin);

        // Create the quit button
        TextButton quitButton = new TextButton("Quit", skin);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.dispose();
                Gdx.app.exit();
            }
        });

        // Properties of the buttons
        final float buttonScale = 1.3f;  // Scale of the buttons
        final float buttonPadding = 10f; // Padding between each button
        final float buttonPosY = 400f;   //  Vertical position of the lowest button

        // Create a table to contain the buttons, scale the buttons and add padding
        Table menuTable = new Table();
        menuTable.add(playButton).width(playButton.getWidth()*buttonScale).height(playButton.getHeight()*buttonScale).pad(buttonPadding).row();
        menuTable.add(optionsButton).width(playButton.getWidth()*buttonScale).height(playButton.getHeight()*buttonScale).pad(buttonPadding).row();
        menuTable.add(quitButton).width(playButton.getWidth()*buttonScale).height(playButton.getHeight()*buttonScale).pad(buttonPadding).row();
        menuTable.setPosition(mapSize.x*SCALE/2f, buttonPosY, Align.center);

        // Add the background and the buttons to the stage
        stage.addActor(new MainMenuBackground(viewport.getCamera(), mapSize));
        stage.addActor(menuTable);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the menu screen
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }

}
