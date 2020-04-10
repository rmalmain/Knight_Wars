package com.knightwars.game.environment;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;


public class MapFactory {

    /**
     * Generates map procedurally based on map's properties. Elements positions are stored into map's properties.
     */
    public static Map createMap(float width, float height, int buildings) {
        Map proceduralMap = new Map();
        Vector2 size = new Vector2(width, height);
        proceduralMap.getProperties().put("size", new Vector2(width, height));
        List<Vector2> buildingsPos = new ArrayList<>();
        float x, y;
        for (int i = 0; i < buildings; i++) {
            x = ((float) Math.random() * 0.9f + 0.05f) * width;
            y = ((float) Math.random() * 0.9f + 0.05f) * height;
            buildingsPos.add(new Vector2(x, y));
        }
        proceduralMap.getProperties().put("buildingsPositions", buildingsPos);
        return proceduralMap;
    }

}
