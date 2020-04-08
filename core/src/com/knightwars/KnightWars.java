package com.knightwars;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.knightwars.FrontEnd.*;

public class KnightWars extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	private GameScreen front;
	
	@Override
	public void create () {
		front = new GameScreen(this);
		batch = new SpriteBatch();
		font = new BitmapFont(); //Use LibGDX's default Arial font.
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		front.dispose();
	}
}
