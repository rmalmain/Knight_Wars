package com.knightwars.game.environment;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.buildings.ClassicCastle;


public class MapFactory {
    public static final float BUILDING_GENERATION_THRESHOLD = 0.9f;

    /**
     * Generates map procedurally based on map's properties.
     * @param width Width or the map
     * @param height Height of the map
     * @param buildings Number of buildings on the map
     * @param defaultPlayer Default owner of buildings
     * @return The map procedurally generated
     */
    public static Map createProceduralMap(float width, float height, int buildings, Player defaultPlayer) {
        Map proceduralMap = new Map(width, height);
        for (int i = 0; i < buildings; i++) {
            proceduralMap.addBuildingCopy(new ClassicCastle(defaultPlayer, generateValidPoint(proceduralMap), 20, false));
        }
        return proceduralMap;
    }

    /** Generate coordinates not too close to others buildings on a map.
     * @param map The map where buildings are
     * @return a valid point
     */
    private static Vector2 generateValidPoint(Map map) {
        float x, y;
        do {
            x = ((float) Math.random() * 0.9f + 0.05f) * map.getSize().x;
            y = ((float) Math.random() * 0.9f + 0.05f) * map.getSize().y;
        } while (!isValidPoint(map, new Vector2(x, y))); // generating points until it is
        // not too close to other buildings
        return new Vector2(x, y);
    }

    /** Know if a point is valid or not.
     * @param map the map where buildings are
     * @param point is this point valid ?
     * @return true if the point is valid, false otherwise
     */
    private static Boolean isValidPoint(Map map, Vector2 point) {
        for (int i=0; i<map.getBuildings().size(); i++) {
            if (point.dst(map.getBuildings().get(i).getCoordinates()) < BUILDING_GENERATION_THRESHOLD) {
                return false;
            }
        }
        return true;
    }

}
