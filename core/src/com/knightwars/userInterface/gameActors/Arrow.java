package com.knightwars.userInterface.gameActors;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.userInterface.GameScreen;

public class Arrow {

    private boolean exists;
    private Vector2 startCoords;
    public Vector2 endCoords;

    public Arrow(Vector2 startCoords, Vector2 endCoords) {
        exists = true;
        this.startCoords = startCoords;
        this.endCoords = endCoords;
    }

    public void hide() {
        exists = false;
    }

    public void show() {
        exists = true;
    }

    public float getWidth() {
        return (float) (Math.sqrt(Math.pow(startCoords.x - endCoords.x, 2) +
                Math.pow(startCoords.y - endCoords.y, 2)) * GameScreen.SCALE);
    }

    public float getHeight() {
        return getWidth()/3f;
    }

    public float getOriginX() {
        return startCoords.x;
    }

    public float getOriginY() {
        return startCoords.y;
    }

    public boolean exists() {
        return exists;
    }

    public void setCoords(Vector2 startCoords, Vector2 endCoords) {
        this.startCoords = startCoords;
        this.endCoords = endCoords;
    }

    public float getRotation() {
        double angle = Math.atan(Math.abs(startCoords.y - endCoords.y) / Math.abs(startCoords.x - endCoords.x));
        if (startCoords.x > endCoords.x && startCoords.y < endCoords.y) {
            angle = Math.PI - angle;
        }
        else if (startCoords.x > endCoords.x && startCoords.y > endCoords.y) {
            angle += Math.PI;
        }
        else if (startCoords.x < endCoords.x && startCoords.y > endCoords.y) {
            angle = - angle;
        }
        return (float) (180f / Math.PI * angle) ;
    }
}
