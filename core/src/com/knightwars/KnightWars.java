package com.knightwars;

import com.badlogic.gdx.Game;
import com.knightwars.game.GameManager;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.userInterface.Display;

public class KnightWars extends Game {

	private GameManager gameManager;
	private KnightWarsGame game;

	@Override
	public void create () {
		gameManager = new GameManager();
		game = gameManager.getKnightWarsGame();

		Display display = new Display(this, game);
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
