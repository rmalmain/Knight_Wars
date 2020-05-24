package com.knightwars.game.players;

import com.knightwars.game.KnightWarsGame;
import com.knightwars.game.environment.Building;
import com.knightwars.game.environment.InvalidUpgradeException;
import com.knightwars.game.environment.NotEnoughGoldException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A player who makes automated moves.
 */
public class ComputerPlayer extends Player {

    /**
     * The computer only attacks if it has 20% more units than the destination building.
     */
    float SAFETY_MARGIN = 1.2f;

    /**
     * The computer only make a sync'd attack if it has 150% more units than the destination building.
     */
    float SAFETY_MARGIN_SYNC = 2.5f;

    /**
     * Probability of trying to update a building is 1/10 000.
     */
    int UPGRADE_PROBABILITY = 1000;

    /**
     * Player constructor.
     *
     * @param name  The name of the player
     * @param color The color of the player
     */
    public ComputerPlayer(String name, ColorPlayer color) {
        super(name, color);
    }

    /**
     * Yay, a basic AI to play the game against. Let's make it smart enough to beat casual players.
     *
     * @param game Current game
     */
    @Override
    public void makeMoves(KnightWarsGame game) {
        // As of now, we don't stack incoming moves, we just choose and trigger them
        // In a future iteration, we should move to events rather than direct instructions
        // That's why we'll list the moves before triggering them, that'll make a refactor easier
        List<List<Object>> moves = new ArrayList<>();

        List<Building> buildings = game.getMap().getBuildings();
        int reserveUnits = 0; // Total number of the computer's units

        // TODO Temporary workaround, that can be fixed by adding more details to buildings
        Map<Building, Integer> remainingUnits = new HashMap<>();
        for (Building building : buildings) {
            remainingUnits.put(building, building.getKnights());
            if (building.getOwner() == this) {
                reserveUnits += building.getKnights();
            }
        }

        // A distance that is not too long to be walked by our faithful soldiers
        float reachableDistance = game.getMap().getSize().len() / 4f;

        // Let's find a good move
        for (Building source : buildings) {

            if (ThreadLocalRandom.current().nextInt(1, UPGRADE_PROBABILITY+1) > UPGRADE_PROBABILITY-1 && source.getOwner() == this) {
                try {
                    Map<Class<? extends Building>, Integer> availableUpgradesMap =
                        game.getMap().availableUpgrade(source);
                    ArrayList<Class<? extends Building>> availableUpgradesClassList = new ArrayList<>(availableUpgradesMap.keySet());

                    game.getMap().upgradeBuilding(source, availableUpgradesClassList.get(ThreadLocalRandom.current().nextInt(0, availableUpgradesClassList.size())));
                }
                catch (InvalidUpgradeException e) {
                }
                catch (NotEnoughGoldException e) {
                }
                finally {
                    continue;
                }
            }
            for (Building destination : buildings) {
                if (source == destination) {
                    continue;
                }

                // Is that an interesting move?
                if (source.getOwner() != this || destination.getOwner() == this) {
                    continue; // Impossible or useless
                }
                if (source.getCoordinates().dst(destination.getCoordinates()) > reachableDistance) {
                    continue; // Too far away
                }
                if (remainingUnits.get(source) < remainingUnits.get(destination) * SAFETY_MARGIN) {
                    continue; // Too dangerous
                }

                float percentage = remainingUnits.get(destination) * SAFETY_MARGIN / remainingUnits.get(source);
                int numberOfUnitsSent = (int) (remainingUnits.get(destination) * SAFETY_MARGIN);
                reserveUnits -= numberOfUnitsSent;
                remainingUnits.put(source, remainingUnits.get(source) - numberOfUnitsSent);

                List<Object> move = new ArrayList<>();
                move.add(source);
                move.add(destination);
                move.add(percentage);
                moves.add(move);
            }
        }

        // We have planned nothing yet, what about a big, synchronised attack?
        if (moves.isEmpty()) {
            for (Building target : buildings) {
                if (target.getOwner() == this || target.getKnights() * SAFETY_MARGIN_SYNC > reserveUnits) {
                    continue;
                }
                for (Building source : buildings) {
                    if (source.getOwner() == this) {
                        List<Object> move = new ArrayList<>();
                        move.add(source);
                        move.add(target);
                        // TODO This sends in the end 100% of the building units
                        //  because the synchronized attack is sent multiple times. That won't happen
                        //  with a more detailed game state, that also contains incoming and in progress attacks.
                        move.add(0.5f);
                        moves.add(move);
                    }
                }
                break;
            }
        }

        for (List<Object> move : moves) {
            // TODO Remove those unchecked casts; implement an event system with a stack
            game.getMap().sendUnit((Building) move.get(0), (Building) move.get(1), (float) move.get(2));
        }

    }
}
