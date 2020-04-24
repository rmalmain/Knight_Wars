package com.knightwars.game.environment;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.buildings.ClassicCastle;
import com.knightwars.game.players.Player;
import org.yaml.snakeyaml.*;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class MapFactory {
    public static final float BUILDING_GENERATION_THRESHOLD = 0.8f;

    /** Generates map procedurally based on map's properties.
     * @param width Width or the map
     * @param height Height of the map
     * @param buildings Number of buildings on the map
     * @param defaultPlayer Default owner of buildings
     * @return The map procedurally generated
     */
    public static Map createProceduralMap(float width, float height, int buildings, Player defaultPlayer,
                                          String yamlUpgradeHierarchyPath) {
        Map proceduralMap = new Map(width, height, yamlUpgradeHierarchyPath);
        for (int i = 0; i < buildings; i++) {
            proceduralMap.addBuildingCopy(new ClassicCastle(defaultPlayer, generateValidPoint(proceduralMap), 20, false));
        }
        return proceduralMap;
    }

    /** Import a map with neutral buildings
     * @param fileName The name of the file to import
     * @return The map described by the file
     */
    public static Map importMapFromFile(String fileName, Player defaultPlayer, String yamlUpgradeHierarchyPath) {

        Yaml yaml = new Yaml();

        LinkedHashMap<String, Object> mapProperties = yaml.load(fileToString(fileName));

        Map importedMap = new Map(((Double) mapProperties.get("width")).floatValue(),
                ((Double) mapProperties.get("height")).floatValue(), yamlUpgradeHierarchyPath);

        List<HashMap<String,Object>> buildings = (List<HashMap<String, Object>>) mapProperties.get("buildings");

        for (HashMap<String, Object> building : buildings) {
            importedMap.addBuildingCopy(new ClassicCastle(defaultPlayer,
                    new Vector2(((Double)building.get("x")).floatValue(),
                            ((Double)building.get("y")).floatValue()), (int) building.get("knights"),
                    (boolean) building.get("knightGrowth")));
        }

        return importedMap;
    }

    /** Generate coordinates not too close to others buildings on a map.
     * @param map The map where buildings are
     * @return a valid point
     */
    private static Vector2 generateValidPoint(Map map) {
        float x, y;
        float minX = 0.9f;
        float maxX = map.getSize().x - 0.3f;
        float minY = 0.4f;
        float maxY = map.getSize().y - 0.4f;
        do {
            x = MathUtils.random(minX, maxX);
            y = MathUtils.random(minY, maxY);
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

    /** Convert file content to string
     * @param fileName The name of the file to convert
     * @return The content of the file converted to string
     */
    private static String fileToString(String fileName) {
        String fileAsString = "";
        try {
            InputStream file = new FileInputStream(fileName);
            BufferedReader buf = new BufferedReader(new InputStreamReader(file));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while(line != null){
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            fileAsString = sb.toString();
        } catch (IOException e) {
            System.out.println("Map file not found");
            e.printStackTrace();
        }
        return fileAsString;
    }

}
