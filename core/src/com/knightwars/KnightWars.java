package com.knightwars;

import com.badlogic.gdx.Game;
import com.knightwars.game.GameManager;
import com.knightwars.userInterface.Display;

public class KnightWars extends Game {

	@Override
	public void create () {
		GameManager gameManager = new GameManager();

		Display display = new Display(this, gameManager);
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
