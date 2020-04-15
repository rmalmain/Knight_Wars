package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapFactory {

    /**
     * Generates map procedurally based on map's properties.
     * @param width Width or the map
     * @param height Height of the map
     * @param buildings Number of buildings on the map
     * @param defaultPlayer Default owner of buildings
     * @param threshold Minimum distance between buildings
     * @return The map procedurally generated
     */
    public static Map createProceduralMap(float width, float height, int buildings, Player defaultPlayer, float threshold) {
        Map proceduralMap = new Map(width, height);
        for (int i = 0; i < buildings; i++) {
            proceduralMap.addBuilding(new Building(defaultPlayer, generateValidPoint(proceduralMap, width, height, threshold), 0));
        }
        return proceduralMap;
    }

    private static Vector2 generateValidPoint(Map map, float width, float height, float threshold) {
        float x, y;
        do {
            x = ((float) Math.random() * 0.9f + 0.05f) * width;
            y = ((float) Math.random() * 0.9f + 0.05f) * height;
        } while (!isValidPoint(map, new Vector2(x, y), threshold)); // generating points until it is
        // not too close to other buildings
        return new Vector2(x, y);
    }

    private static Boolean isValidPoint(Map map, Vector2 point, float threshold) {
        for (int i=0; i<map.getBuildings().size(); i++) {
            if (point.dst(map.getBuildings().get(i).coordinates) < threshold) {
                return false;
            }
        }
        return true;
    }

}
