package com.knightwars.tests;

import com.knightwars.game.environment.*;
import com.knightwars.game.environment.buildings.*;
import com.knightwars.game.players.NeutralPlayer;
import com.knightwars.game.players.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;

public class BuildingTreeTest {
    public final static double EPSILON = 1e-6;

    private Map<String, Map<String, Integer>> obj;

    private com.knightwars.game.environment.Map gameMap;

    @Before
    public void setUp() {
        Yaml yaml = new Yaml();
        try {
            InputStream inputStream = new FileInputStream(new File("src/com/knightwars/game/environment/building-structure" +
                    ".yml"));
            obj = yaml.load(inputStream);
        } catch(FileNotFoundException e) {
            System.out.println("File not found...\n");
            System.exit(0);
        }

        Reflections reflections = new Reflections("com.knightwars.game.environment");
        reflections.getSubTypesOf(Building.class);

         gameMap = MapFactory.createProceduralMap(6f, 3.375f, 1, new NeutralPlayer("Test",
                Player.ColorPlayer.NEUTRAL), "src/com/knightwars/game/environment/building-structure.yml");

         gameMap.getBuildings().get(0).getOwner().addGold(10000);
    }

    @Test
    public void simpleUpgradeTest() throws InvalidUpgradeException, NotEnoughGoldException {
        float initialGolds = gameMap.getBuildings().get(0).getOwner().getGold();
        gameMap.upgradeBuilding(gameMap.getBuildings().get(0), FortifiedCastle.class);

        Assert.assertEquals(gameMap.getBuildings().get(0).getClass().getSimpleName(), FortifiedCastle.class.getSimpleName());
        Assert.assertEquals(initialGolds - obj.get(ClassicCastle.class.getSimpleName()).get(FortifiedCastle.class.getSimpleName()),
                gameMap.getBuildings().get(0).getOwner().getGold(),
                EPSILON);
    }

    @Test(expected=InvalidUpgradeException.class)
    public void wrongUpgradeTest() throws InvalidUpgradeException, NotEnoughGoldException {
        float initialGolds = gameMap.getBuildings().get(0).getOwner().getGold();
        try {
            gameMap.upgradeBuilding(gameMap.getBuildings().get(0), GarrisonCastle1.class);
        } finally {
            Assert.assertEquals(initialGolds, gameMap.getBuildings().get(0).getOwner().getGold(), EPSILON);
        }
    }

    @Test
    public void multipleUpgradeTest() throws NotEnoughGoldException, InvalidUpgradeException {
        float initialGolds = gameMap.getBuildings().get(0).getOwner().getGold();
        gameMap.upgradeBuilding(gameMap.getBuildings().get(0), FortifiedCastle.class);
        Assert.assertEquals(initialGolds - obj.get(ClassicCastle.class.getSimpleName()).get(FortifiedCastle.class.getSimpleName()),
                gameMap.getBuildings().get(0).getOwner().getGold(), EPSILON);

        gameMap.upgradeBuilding(gameMap.getBuildings().get(0), ForgeCastle1.class);
        Assert.assertEquals(initialGolds - obj.get(ClassicCastle.class.getSimpleName()).get(FortifiedCastle.class.getSimpleName())
                - obj.get(FortifiedCastle.class.getSimpleName()).get(ForgeCastle1.class.getSimpleName()),
                gameMap.getBuildings().get(0).getOwner().getGold(), EPSILON);
        Assert.assertEquals(gameMap.getBuildings().get(0).getClass().getSimpleName(), ForgeCastle1.class.getSimpleName());
    }

    @Test
    public void getAvailableUpgrades() throws InvalidUpgradeException, NotEnoughGoldException {

        HashSet<Class<? extends Building>> initialUpgrades = new HashSet<>();
        initialUpgrades.add(FortifiedCastle.class);

        Assert.assertEquals(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).keySet(), initialUpgrades);

        gameMap.upgradeBuilding(gameMap.getBuildings().get(0), FortifiedCastle.class);

        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).containsKey(GarrisonCastle1.class));
        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).containsKey(MarketCastle1.class));
        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).containsKey(ForgeCastle1.class));
        Assert.assertFalse(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).containsKey(FortifiedCastle.class));
        Assert.assertFalse(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).containsKey(ClassicCastle.class));

        gameMap.upgradeBuilding(gameMap.getBuildings().get(0), ForgeCastle1.class);

        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).isEmpty());
    }

    @Test
    public void getAvailableUpgradesCoordinates() throws InvalidUpgradeException, NoBuildingFoundException, NotEnoughGoldException {
        HashSet<Class<? extends Building>> initialUpgrades = new HashSet<>();
        initialUpgrades.add(FortifiedCastle.class);

        Assert.assertEquals(gameMap.availableUpgrade(gameMap.getBuildings().get(0)).keySet(), initialUpgrades);

        gameMap.upgradeBuilding(gameMap.getBuildings().get(0).getCoordinates(), FortifiedCastle.class);

        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0).getCoordinates()).containsKey(GarrisonCastle1.class));
        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0).getCoordinates()).containsKey(MarketCastle1.class));
        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0).getCoordinates()).containsKey(ForgeCastle1.class));
        Assert.assertFalse(gameMap.availableUpgrade(gameMap.getBuildings().get(0).getCoordinates()).containsKey(FortifiedCastle.class));
        Assert.assertFalse(gameMap.availableUpgrade(gameMap.getBuildings().get(0).getCoordinates()).containsKey(ClassicCastle.class));

        gameMap.upgradeBuilding(gameMap.getBuildings().get(0).getCoordinates(), ForgeCastle1.class);

        Assert.assertTrue(gameMap.availableUpgrade(gameMap.getBuildings().get(0).getCoordinates()).isEmpty());
    }
}
