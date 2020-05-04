package com.knightwars.userInterface;

import com.knightwars.KnightWars;
import com.knightwars.game.GameManager;
import com.knightwars.game.players.Player;

public class Display {

    private final KnightWars gameDisplay;
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

    public void displayEndMenu(Player winner) {
        gameDisplay.setScreen(new EndGameScreen(this, gameManager, winner));
    }
}
