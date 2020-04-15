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
            do {
                x = ((float) Math.random() * 0.9f + 0.05f) * width;
                y = ((float) Math.random() * 0.9f + 0.05f) * height;
            } while (!isValidPoint(proceduralMap, new Vector2(x, y), 0.4f)); // generating points until it is
                                                                                // not too close to other buildings

            proceduralMap.addBuilding(new Building(defaultPlayer, new Vector2(x, y)));
        }
        return proceduralMap;
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
