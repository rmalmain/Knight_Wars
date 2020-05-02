package com.knightwars.tests;

import com.knightwars.game.environment.Building;
import com.knightwars.game.environment.InvalidUpgradeException;
import com.knightwars.game.environment.MapFactory;
import com.knightwars.game.environment.NoBuildingFoundException;
import com.knightwars.game.environment.buildings.*;
import com.knightwars.game.players.NeutralPlayer;
import com.knightwars.game.players.Player;
import org.junit.*;
import org.reflections.Reflections;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BuildingTreeTest {
    Yaml yaml;
    Set<Class<? extends Building>> classes;
    Map<String, Object> obj;

    com.knightwars.game.environment.Map gameMap;

    @Before
    public void setUp() {
        yaml = new Yaml();
        try {
            InputStream inputStream = new FileInputStream(new File("src/com/knightwars/game/environment/building-structure" +
                    ".yml"));
            obj = yaml.load(inputStream);
        } catch(FileNotFoundException e) {
            System.out.println("File not found...\n");
            System.exit(0);
        }

        Reflections reflections = new Reflections("com.knightwars.game.environment");
        classes = reflections.getSubTypesOf(Building.class);

         gameMap = MapFactory.createProceduralMap(6f, 3.375f, 1, new NeutralPlayer("Test",
                Player.ColorPlayer.NEUTRAL), "src/com/knightwars/game/environment/building-structure.yml");
    }

    @Test
    public void simpleUpgradeTest() throws InvalidUpgradeException {
        gameMap.upgradeBuilding(gameMap.getBuildings().get(0), FortifiedCastle.class);

        Assert.assertTrue(gameMap.getBuildings().get(0).getClass().getSimpleName().equals(FortifiedCastle.class.getSimpleName()));
    }

    @Test(expected=InvalidUpgradeException.class)
    public void wrongUpgradeTest() throws InvalidUpgradeException {
        gameMap.upgradeBuilding(gameMap.getBuildings().get(0), CitadelCastle1.class);
    }

    @Test
    public void multipleUpgradeTest() {
        try {
            gameMap.upgradeBuilding(gameMap.getBuildings().get(0), FortifiedCastle.class);
        } catch (InvalidUpgradeException e) {
            System.exit(1);
        }

        try {
            gameMap.upgradeBuilding(gameMap.getBuildings().get(0), ForgeCastle1.class);
        } catch (InvalidUpgradeException e) {
            System.exit(1);
        }

        Assert.assertTrue(gameMap.getBuildings().get(0).getClass().getSimpleName().equals(ForgeCastle1.class.getSimpleName()));
    }

    @Test
    public void getAvailableUpgrades() throws InvalidUpgradeException {
        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).get(0).getSimpleName().equals(
                "FortifiedCastle"));

        gameMap.upgradeBuilding(gameMap.getBuildings().get(0), FortifiedCastle.class);

        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).contains(CitadelCastle1.class));
        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).contains(MarketCastle1.class));
        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).contains(ForgeCastle1.class));
        Assert.assertFalse(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).contains(FortifiedCastle.class));
        Assert.assertFalse(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).contains(ClassicCastle.class));

        gameMap.upgradeBuilding(gameMap.getBuildings().get(0), ForgeCastle1.class);

        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).isEmpty());
    }

    @Test
    public void getAvailableUpgradesCoordinates() throws InvalidUpgradeException, NoBuildingFoundException {
        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).get(0).getSimpleName().equals(
                "FortifiedCastle"));

        gameMap.upgradeBuilding(gameMap.getBuildings().get(0).getCoordinates(), FortifiedCastle.class);

        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0).getCoordinates()).contains(CitadelCastle1.class));
        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0).getCoordinates()).contains(MarketCastle1.class));
        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0).getCoordinates()).contains(ForgeCastle1.class));
        Assert.assertFalse(gameMap.availableUpgrade(gameMap.getBuildings().get(0).getCoordinates()).contains(FortifiedCastle.class));
        Assert.assertFalse(gameMap.availableUpgrade(gameMap.getBuildings().get(0).getCoordinates()).contains(ClassicCastle.class));

        gameMap.upgradeBuilding(gameMap.getBuildings().get(0).getCoordinates(), ForgeCastle1.class);

        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0).getCoordinates()).isEmpty());
    }
}
