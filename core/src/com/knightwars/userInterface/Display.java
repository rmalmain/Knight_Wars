package com.knightwars.userInterface;

import com.knightwars.KnightWars;
import com.knightwars.game.GameManager;

public class Display {

    public final KnightWars gameDisplay;
    private final GameManager gameManager;

    public Display(KnightWars gameDisplay, GameManager gameManager) {
        this.gameDisplay = gameDisplay;
        this.gameManager = gameManager;
    }

    public KnightWars getGameDisplay() {
        return gameDisplay;
    }

    public void displayGame() {
        gameDisplay.setScreen(new GameScreen(this, gameManager));
    }

    public void displayMainMenu() {
        gameDisplay.setScreen(new MainMenuScreen(this, gameManager));
    }
}
