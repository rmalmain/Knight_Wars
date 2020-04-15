package com.knightwars;

import com.badlogic.gdx.Game;
import com.knightwars.game.GameManager;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.userInterface.Display;

public class KnightWars extends Game {

	private GameManager gameManager;

	@Override
	public void create () {
		gameManager = new GameManager();

		Display display = new Display(this, gameManager.getKnightWarsGame());
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
