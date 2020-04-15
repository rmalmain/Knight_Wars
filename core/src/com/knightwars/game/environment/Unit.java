package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;

public class Unit {
//    private Player player;
    private Path path;

//    public Unit(Player player, Path path) {
//        this.player = player;
//        this.path = path;
//    }

//    public Player getOwner() {
//        return this.player;
//    }

    public Vector2 getCoordinates() {
        return this.path.getCoordinates();
    }

    public void update(int dt) {
        this.path.update(dt);
    }

}
