package com.knightwars.game;

import com.knightwars.game.environment.Map;
import com.knightwars.game.environment.MapFactory;
import com.knightwars.game.players.HumanPlayer;
import com.knightwars.game.players.NeutralPlayer;
import com.knightwars.game.players.Player;
import com.knightwars.game.players.ComputerPlayer;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

public class KnightWarsGame {
    public static final float WIDTH = 6f;
    public static final float HEIGHT = 3.375f;
    public static final int BUILDINGS_NUMBER = 15;
    public static final String YAML_UPGRADE_HIERARCHY_PATH = "core/src/com/knightwars/game/environment/building-structure.yml";

    /** All (active and past) players in the game */
    private List<Player> players;
    private HumanPlayer humanPlayer;
    private Map map;

    public KnightWarsGame() {
        // Players initialization
        Player playerRed = new ComputerPlayer("Red Player", Player.ColorPlayer.RED);
        HumanPlayer playerBlue = new HumanPlayer("Blue Player", Player.ColorPlayer.BLUE);
        Player playerNeutral = new NeutralPlayer("Neutral Player", Player.ColorPlayer.NEUTRAL);

        // Keep them all in a list
        this.players = new ArrayList<>();
        this.players.add(playerRed);
        this.players.add(playerBlue);
        this.players.add(playerNeutral);

        this.humanPlayer = playerBlue;

        this.map = MapFactory.createProceduralMap( // Map generation
                WIDTH, HEIGHT, BUILDINGS_NUMBER, playerNeutral, YAML_UPGRADE_HIERARCHY_PATH);

        // Buildings attribution
        attributeBuildings(playerRed, playerBlue);
    }

    /** Attribute a building to each player. Every other buildings are attributed to the neutral player. */
    public void attributeBuildings(Player playerRed, Player playerBlue) {
        // TODO That should be done at generation time
        int randomRed = random(map.getBuildings().size()-1);
        int randomBlue = random(map.getBuildings().size()-1);
        if (randomBlue == randomRed) {
            randomBlue++;
            randomBlue = randomBlue%(map.getBuildings().size());
        }

        map.getBuildings().get(randomRed).setOwner(playerRed);
        map.getBuildings().get(randomRed).setKnights(50);
        map.getBuildings().get(randomRed).setCanGenerateUnits(true);

        map.getBuildings().get(randomBlue).setOwner(playerBlue);
        map.getBuildings().get(randomBlue).setKnights(50);
        map.getBuildings().get(randomBlue).setCanGenerateUnits(true);
    }

    public List<Player> getPlayers() { return this.players; }

    /**
     * A way to get the player who uses a mouse or a touchscreen.
     * @return The human player currently playing
     */
    public HumanPlayer getHumanPlayer() {
        return this.humanPlayer;
    }

    public Map getMap() { return map; }

    public void update(float dt) {
        map.update(dt);
        for (Player player : this.players) {
            player.makeMoves(this);
        }
    }
}
