package com.knightwars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.knightwars.userInterface.*;

public class KnightWars extends Game {

	private Display display;
	
	@Override
	public void create () {
		display = new Display(this);
		display.displayMainMenu();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}
