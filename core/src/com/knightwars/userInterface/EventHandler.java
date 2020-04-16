package com.knightwars.userInterface;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.game.environment.Building;
import com.knightwars.game.environment.NotEnoughKnightsException;
import com.knightwars.game.environment.Unit;

import java.util.List;

import static com.knightwars.userInterface.GameScreen.SCALE;

public class EventHandler {

    private KnightWarsGame gameState;
    private Viewport viewport;

    public EventHandler(KnightWarsGame gameState, Viewport viewport) {
        this.gameState = gameState;
        this.viewport = viewport;
    }

    /**
     * Handle drag event to select two buildings and move the units to the selected building
     *
     * @param lastTouchDown The coordinates of the last TouchDown event
     * @param lastTouchUp   The coordinates of the last TouchUp event
     */
    public void handleDrag(Vector2 lastTouchDown, Vector2 lastTouchUp) {
        // Unproject the screen coordinates in the game coordinates
        Vector2 selectedBuildingCoords = unprojectVector2(lastTouchDown);
        Vector2 destinationBuildingCoords = unprojectVector2(lastTouchUp);

        // Get the selected buildings
        Building selectedBuilding = getSelectedBuilding(selectedBuildingCoords);
        Building destinationBuilding = getSelectedBuilding(destinationBuildingCoords);

        // Move the units from one building to another
        if (selectedBuilding != null && destinationBuilding != null) {
            System.out.println("Move from building at " + selectedBuildingCoords + " to building at " + destinationBuildingCoords);
            try {
                while(selectedBuilding.getKnights() > 0) {
                    gameState.getMap().getUnits().add(new Unit(selectedBuilding.getOwner(),
                            selectedBuilding.getCoordinates(), 1f, destinationBuilding));
                    selectedBuilding.unitDeparture();
                }
            } catch (NotEnoughKnightsException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Unproject a 2D vector in the game coordinates
     *
     * @param vec2 The 2D vector in the screen coordinates
     * @return The 2D vector in the game coordinates
     */
    private Vector2 unprojectVector2(Vector2 vec2) {
        Vector3 vec3 = new Vector3(vec2.x, vec2.y, 0);
        viewport.unproject(vec3);
        return new Vector2(vec3.x / GameScreen.SCALE, vec3.y / GameScreen.SCALE);
    }

    /**
     * Get the building corresponding the given coordinates
     *
     * @param coords The coordinates of the building
     * @return The corresponding building, or null if no corresponding was found
     */
    private Building getSelectedBuilding(Vector2 coords) {
        // Fetch the coordinates of the buildings
        List<Building> buildings = gameState.getMap().getBuildings();

        // Draw the buildings
        for (Building building : buildings) {
            if (building.isSelected(coords)) {
                return building;
            }
        }
        return null;
    }
}
