package com.knightwars.tests;

import com.knightwars.game.environment.Building;
import com.knightwars.game.environment.InvalidUpgradeException;
import com.knightwars.game.environment.MapFactory;
import com.knightwars.game.environment.buildings.CitadelCastle1;
import com.knightwars.game.environment.buildings.ClassicCastle;
import com.knightwars.game.environment.buildings.ForgeCastle1;
import com.knightwars.game.environment.buildings.FortifiedCastle;
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
            InputStream inputStream = new FileInputStream(new File("src/com/knightwars/tests/building-structure.yml"));
            obj = yaml.load(inputStream);
        } catch(FileNotFoundException e) {
            System.out.println("Fichier introuvable...\n");
            System.exit(0);
        }

        Reflections reflections = new Reflections("com.knightwars.game.environment");
        classes = reflections.getSubTypesOf(Building.class);

         gameMap = MapFactory.createProceduralMap(6f, 3.375f, 1, new NeutralPlayer("Test",
                Player.ColorPlayer.NEUTRAL));
    }

    @Test
    public void simpleUpgradeTest() {
        try {
            gameMap.upgradeBuilding(gameMap.getBuildings().get(0), FortifiedCastle.class);
        } catch (InvalidUpgradeException e) {
            System.out.println("CA MARCHE PAS TON UPGRADE DE MORT !!");
        }
        System.out.println(gameMap.getBuildings().get(0).getClass().toString());
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
}
