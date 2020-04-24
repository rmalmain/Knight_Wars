package com.knightwars.game;

import com.knightwars.game.environment.InvalidUpgradeException;

/** Exception raised if the format of a YAML file is incorrect
 */

public class InvalidYamlFormatException extends Exception {
    public InvalidYamlFormatException(String message) {
        super(message);
    }
}
