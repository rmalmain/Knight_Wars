package com.knightwars.userInterface;

import com.knightwars.KnightWars;

public class Display {

    public final KnightWars game;

    public Display(KnightWars game) {
        this.game = game;
    }

    public KnightWars getGame() {
        return game;
    }

    public void displayGame() {
        game.setScreen(new GameScreen(this));
    }

    public void displayMainMenu() {
        game.setScreen(new MainMenuScreen(this));
    }
}
