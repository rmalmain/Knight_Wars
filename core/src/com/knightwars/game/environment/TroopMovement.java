package com.knightwars.game.environment;

import java.util.ArrayList;
import java.util.List;

import com.knightwars.game.players.Player;

/**
 * A troop movement, pictured as an infantry regiment.
 */
public class TroopMovement {

    /**
     * Number of units attacking side by side in a regiment.
     */
    final static int RANK_SIZE = 2;

    /**
     * Distance between units in a battle line.
     */
    final static float BATTLE_LINE_OFFSET = 0.05f;

    private Player owner;
    private Building departureBuilding;
    private Building destinationBuilding;

    private List<Unit> units;
    private float time;
    private Path originalPath;
    private Path[] differentPaths;
    private int number;
    private int numberOfUnitsStayingAtHome;
    private int numberSpawned;
    private boolean allSent;

    public TroopMovement(Player owner, Building departureBuilding, Building destinationBuilding, Path originalPath,
            int number) {
        this.owner = owner;
        this.departureBuilding = departureBuilding;
        this.destinationBuilding = destinationBuilding;
        this.originalPath = originalPath;

        this.units = new ArrayList<>();
        this.number = number;
        this.numberSpawned = 0;
        this.numberOfUnitsStayingAtHome = departureBuilding.getKnights() - number;
        this.allSent = false;

        differentPaths = new Path[RANK_SIZE];
        differentPaths[0] = Path.pathVariant(this.originalPath, BATTLE_LINE_OFFSET);
        differentPaths[1] = Path.pathVariant(this.originalPath, -BATTLE_LINE_OFFSET);
    }

    public Player getOwner() {
        return this.owner;
    }

    public Building getDepartureBuilding() {
        return this.departureBuilding;
    }

    public Building getDestinationBuilding() {
        return this.destinationBuilding;
    }

    public List<Unit> getUnits() {
        return this.units;
    }

    public boolean removeUnit(Unit unit) {
        return this.units.remove(unit);
    }

    public boolean allUnitsSent() {
        return this.allSent;
    }

    public boolean canSendNextAttack() {
        return this.allSent && this.time > (this.number + 1) * Map.TIME_BETWEEN_UNITS;
    }

    public boolean done() {
        return this.allSent && this.units.isEmpty();
    }

    /**
     * Spawn new units and update the walking ones.
     */
    public void update(float dt) {

        this.time += dt;

        List<Unit> unitsToDelete = new ArrayList<>();

        for (Unit unit : this.units) {
            unit.update(dt);
            if (unit.isArrived(Map.BUILDING_COLLISION_THRESHOLD)) {
                try {
                    unit.getDestinationBuilding().unitArrival(unit);
                } catch (AttackerWonFightException e) {
                    e.getAttackedBuilding().setOwner(e.getAttackingPlayer());
                    e.getAttackedBuilding().setCanGenerateUnits(true);
                }
                unitsToDelete.add(unit);
            }
        }

        for (Unit unit : unitsToDelete) { // Delete units arrived
            this.units.remove(unit);
        }

        while (!this.allSent && this.time / Map.TIME_BETWEEN_UNITS >= numberSpawned
                && this.departureBuilding.getKnights() > this.numberOfUnitsStayingAtHome
                && this.numberSpawned < this.number) {
            int rankSize = Math.min(RANK_SIZE,
                    Math.min(this.departureBuilding.getKnights() - this.numberOfUnitsStayingAtHome,
                            this.number - this.numberSpawned));
            try {
                if (rankSize == 1) {
                    Path path1 = new Path(this.originalPath);
                    Unit unit1 = new Unit(this.owner, this.departureBuilding, this.destinationBuilding, path1, this);
                    this.numberSpawned++;
                    unit1.getDepartureBuilding().unitDeparture();
                    this.units.add(unit1);
                } else if (rankSize == 2) {
                    Path path1 = new Path(this.differentPaths[0]);
                    Path path2 = new Path(this.differentPaths[1]);
                    Unit unit1 = new Unit(this.owner, this.departureBuilding, this.destinationBuilding, path1, this);
                    Unit unit2 = new Unit(this.owner, this.departureBuilding, this.destinationBuilding, path2, this);
                    this.numberSpawned++;
                    this.numberSpawned++;
                    unit1.getDepartureBuilding().unitDeparture();
                    unit2.getDepartureBuilding().unitDeparture();
                    this.units.add(unit1);
                    this.units.add(unit2);
                }
            } catch (NotEnoughKnightsException e) {
            }
        }

        if (!this.allSent && (this.departureBuilding.getKnights() <= this.numberOfUnitsStayingAtHome
                || this.numberSpawned >= this.number)) {
            this.allSent = true;
        }

    }

}
