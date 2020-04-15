package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapFactory {

    /**
     * Generates map procedurally based on map's properties. Elements positions are stored into map's properties.
     */
    public static Map createProceduralMap(float width, float height, int buildings, Player defaultPlayer) {
        Map proceduralMap = new Map(width, height);

        float x, y;
        for (int i = 0; i < buildings; i++) {
            x = ((float) Math.random() * 0.9f + 0.05f) * width;
            y = ((float) Math.random() * 0.9f + 0.05f) * height;
            proceduralMap.addBuilding(new Building(defaultPlayer, new Vector2(x, y)));
        }
        return proceduralMap;
    }

}
