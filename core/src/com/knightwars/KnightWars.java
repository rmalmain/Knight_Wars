package com.knightwars;

import com.badlogic.gdx.Game;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.userInterface.Display;

public class KnightWars extends Game {

	public KnightWarsGame gameState;

	@Override
	public void create () {
		gameState = new KnightWarsGame();

		Display display = new Display(this, gameState);
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
