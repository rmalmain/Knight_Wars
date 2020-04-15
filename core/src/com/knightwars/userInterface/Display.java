package com.knightwars.userInterface;

import com.knightwars.KnightWars;
import com.knightwars.game.KnightWarsGame;

public class Display {

    public final KnightWars gameDisplay;
    private final KnightWarsGame gameState;

    public Display(KnightWars gameDisplay, KnightWarsGame gameState) {
        this.gameDisplay = gameDisplay;
        this.gameState = gameState;
    }

    public KnightWars getGameDisplay() {
        return gameDisplay;
    }

    public void displayGame() {
        gameDisplay.setScreen(new GameScreen(this, gameState));
    }

    public void displayMainMenu() {
        gameDisplay.setScreen(new MainMenuScreen(this, gameState));
    }
}
