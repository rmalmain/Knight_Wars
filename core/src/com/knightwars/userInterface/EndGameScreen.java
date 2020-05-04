package com.knightwars.userInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.knightwars.game.GameManager;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.game.players.Player;

import static com.knightwars.userInterface.GameScreen.SCALE;

public class EndGameScreen implements Screen {

    private final FillViewport viewport;
    private final Stage stage;


    public EndGameScreen(final Display display, final GameManager gameManager, Player winner) {
        SpriteBatch batch = new SpriteBatch();

        // Fetch the map size from the game state
        Vector2 mapSize = gameManager.getKnightWarsGame().getMap().getSize();

        // Constructs a new OrthographicCamera and viewport using the screen width and height
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FillViewport(mapSize.x * SCALE, mapSize.y * SCALE, camera);

        KnightWarsGame gameState = gameManager.getKnightWarsGame();

        // Add the skin of the buttons
        Skin skin = new Skin(Gdx.files.internal("buttons/glassy-ui.json"));
        Skin skinLabel = (Skin) skin.newDrawable("winner message", winner.getColor() == Player.ColorPlayer.RED ? Color.RED : Color.BLUE);

        // Create the stage
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // Create the winner label
        Label winnerLabel = new Label(winner.getName() + " has won !", skinLabel);

        // Create the play button
        TextButton playButton = new TextButton("Restart", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.resetGame();
                stage.dispose();
                display.displayGame();
            }
        });

        // Create the back to menu button
        TextButton backToMenuButton = new TextButton("Main menu", skin);
        backToMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.resetGame();
                stage.dispose();
                display.displayMainMenu();
            }
        });

        // Properties of the buttons
        final float buttonScale = 1.1f;  // Scale of the buttons
        final float buttonPadding = 10f; // Padding between each button
        final float buttonPosY = 230f;   //  Vertical position of the lowest button

        // Create a table to contain the buttons, scale them and add padding
        Table menuTable = new Table();
        menuTable.add(winnerLabel).width(winnerLabel.getWidth()*buttonScale).height(winnerLabel.getHeight()*buttonScale).pad(buttonPadding).row();
        menuTable.add(playButton).width(playButton.getWidth()*buttonScale).height(playButton.getHeight()*buttonScale).pad(buttonPadding).row();
        menuTable.add(backToMenuButton).width(playButton.getWidth()*buttonScale).height(playButton.getHeight()*buttonScale).pad(buttonPadding).row();
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

