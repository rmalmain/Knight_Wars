package com.knightwars.userInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.knightwars.game.KnightWarsGame;

import static com.knightwars.userInterface.GameScreen.SCALE;

public class MainMenuScreen implements Screen {

    private final Display display;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final FillViewport viewport;
    private final Vector2 mapSize;
    private final Texture background;
    private final Texture title;


    public MainMenuScreen(Display display, KnightWarsGame gameState) {
        this.display = display;
        batch = new SpriteBatch();
        font = new BitmapFont();

        // Fetch the map size from the game state
        mapSize = gameState.getMap().getSize();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        camera = new OrthographicCamera();
        camera.position.set(mapSize.x * SCALE / 2, mapSize.y * SCALE / 2, 0);
        viewport = new FillViewport(mapSize.x * SCALE, mapSize.y * SCALE, camera);

        background = new Texture("menu-background.jpg");
        title = new Texture("title.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the camera and the inverse window projection
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Vector3 pos = new Vector3();
        pos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(pos);

        batch.begin();
        batch.draw(background, 0, 0,mapSize.x * SCALE, mapSize.y * SCALE);
        batch.draw(title, 0, 0,mapSize.x * SCALE, mapSize.y * SCALE);
        font.draw(batch, "Tap anywhere to begin!", 420, 100);
        batch.end();

        if (Gdx.input.isTouched()) {
            display.displayGame();
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
