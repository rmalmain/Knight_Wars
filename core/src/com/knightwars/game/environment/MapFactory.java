package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.buildings.Castle;


public class MapFactory {

    /**
     * Generates map procedurally based on map's properties.
     * @param width Width or the map
     * @param height Height of the map
     * @param buildings Number of buildings on the map
     * @param defaultPlayer Default owner of buildings
     * @param buildingGenerationThreshold Minimum distance between buildings. It must be chosen carefully, the game
     *                                    may crash if it is too high.
     * @return The map procedurally generated
     */
    public static Map createProceduralMap(float width, float height, int buildings, Player defaultPlayer, float buildingGenerationThreshold, float buildingCollisionThreshold) {
        Map proceduralMap = new Map(width, height, buildingCollisionThreshold);
        for (int i = 0; i < buildings; i++) {
            proceduralMap.addBuildingCopy(new Castle(defaultPlayer, generateValidPoint(proceduralMap, buildingGenerationThreshold), 20));
        }
        return proceduralMap;
    }

    /** Generate coordinates not too close to others buildings on a map.
     * @param map The map where buildings are
     * @param threshold threshold to know if a point is valid or not
     * @return a valid point
     */
    private static Vector2 generateValidPoint(Map map, float threshold) {
        float x, y;
        do {
            x = ((float) Math.random() * 0.9f + 0.05f) * map.getSize().x;
            y = ((float) Math.random() * 0.9f + 0.05f) * map.getSize().y;
        } while (!isValidPoint(map, new Vector2(x, y), threshold)); // generating points until it is
        // not too close to other buildings
        return new Vector2(x, y);
    }

    /** Know if a point is valid or not.
     * @param map the map where buildings are
     * @param point is this point valid ?
     * @param threshold threshold to know if a point is valid or not
     * @return true if the point is valid, false otherwise
     */
    private static Boolean isValidPoint(Map map, Vector2 point, float threshold) {
        for (int i=0; i<map.getBuildings().size(); i++) {
            if (point.dst(map.getBuildings().get(i).getCoordinates()) < threshold) {
                return false;
            }
        }
        return true;
    }

}
