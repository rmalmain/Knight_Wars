package com.knightwars.game;

import com.knightwars.game.environment.InvalidUpgradeException;

public class InvalidYamlFormat extends Exception {
    public InvalidYamlFormat(String message) {
        super(message);
    }
}
