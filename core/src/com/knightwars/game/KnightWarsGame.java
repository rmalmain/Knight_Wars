package com.knightwars.game;

import com.badlogic.gdx.Gdx;
import com.knightwars.game.environment.Map;
import com.knightwars.game.environment.MapFactory;
import com.knightwars.game.environment.NoBuildingFoundException;
import com.knightwars.game.players.ComputerPlayer;
import com.knightwars.game.players.HumanPlayer;
import com.knightwars.game.players.NeutralPlayer;
import com.knightwars.game.players.Player;

import java.util.ArrayList;
import java.util.List;

public class KnightWarsGame {
    public static final float WIDTH = 6f;
    public static final float HEIGHT = 3.375f;
    public static final int BUILDINGS_NUMBER = 15;
    public static final String YAML_UPGRADE_HIERARCHY_PATH = "core/src/com/knightwars/game/environment/building-structure.yml";

    /** All (active and past) players in the game */
    private final List<Player> players;
    private final HumanPlayer humanPlayer;
    private final Map map;

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

//        this.map = MapFactory.createProceduralMap( // Map generation
//                WIDTH, HEIGHT, BUILDINGS_NUMBER, playerNeutral, YAML_UPGRADE_HIERARCHY_PATH);

        this.map = MapFactory.importMapFromFile("maps/map1.yml", playerNeutral, YAML_UPGRADE_HIERARCHY_PATH);

        // Buildings attribution
        try {
            attributeBuildings(playerRed, playerBlue);
        } catch (NoBuildingFoundException e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    /** Attribute a building to each player. Every other buildings are attributed to the neutral player. */
    public void attributeBuildings(Player playerRed, Player playerBlue) throws NoBuildingFoundException {
        // TODO That should be done at generation time
        try {
            map.getBuildings().get(0).setOwner(playerRed);
            map.getBuildings().get(0).setKnights(50);
            map.getBuildings().get(0).setCanGenerateUnits(true);

            map.getBuildings().get(1).setOwner(playerBlue);
            map.getBuildings().get(1).setKnights(50);
            map.getBuildings().get(1).setCanGenerateUnits(true);
        }
        catch (IndexOutOfBoundsException e) {
            throw new NoBuildingFoundException("The map should at least have two buildings");
        }

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


    public Player gameOver() {
        boolean gameOver = true;
        int i = 0;
        Player winner = map.getBuildings().get(0).getOwner();

        while(gameOver && i < map.getBuildings().size()){
            Player player = map.getBuildings().get(i).getOwner();
            gameOver = (player.getColor() == Player.ColorPlayer.NEUTRAL || winner == player);
            i++;
        }

        if (!gameOver) {
            winner = null;
        } else {
            i = 0;
            while (gameOver && i < map.getUnits().size()) {
                gameOver = (map.getUnits().get(i).getOwner() == winner);
                i++;
            }
            if (!gameOver)
                winner = null;
        }
        return winner;
    }

}
