package com.knightwars.FrontEnd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.knightwars.KnightWars;

public class GameScreen implements Screen {

    private final KnightWars game;
    private SpriteBatch batch;
    private Texture img;

    public GameScreen(final KnightWars game) {
        this.game = game;
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
    }


    /**
     * Called when this screen becomes the current screen.
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    /**
     * Called when the screen is resized
     * @param width The width of the resized screen.
     * @param height The height of the resized screen.
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Called when the game is paused, usually when it's not active or visible on-screen. The game is also
     * paused before it is destroyed.
     */
    @Override
    public void pause() {

    }

    /**
     * Called when the game is resumed from a paused state, usually when it regains focus.
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
