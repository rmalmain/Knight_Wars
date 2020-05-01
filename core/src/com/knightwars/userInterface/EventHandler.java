package com.knightwars.userInterface;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;
import com.knightwars.userInterface.gameActors.GameActorBuildings;
import com.knightwars.userInterface.gameActors.GameActorHUD;

import java.util.List;

public class EventHandler {

    private final KnightWarsGame gameState;
    private final Viewport viewport;
    private final GameActorHUD actorHUD;
    private final GameActorBuildings actorBuildings;

    public EventHandler(KnightWarsGame gameState, Viewport viewport, GameActorHUD actorHUD,
                        GameActorBuildings actorBuildings) {
        this.gameState = gameState;
        this.viewport = viewport;
        this.actorHUD = actorHUD;
        this.actorBuildings = actorBuildings;
    }

    /**
     * Handle TouchUp event to select two buildings and move the units to the selected building
     *
     * @param lastTouchDown The coordinates of the last TouchDown event
     * @param lastTouchUp   The coordinates of the last TouchUp event
     */
    public void handleTouchUp(Vector2 lastTouchDown, Vector2 lastTouchUp) {
        // Delete last drawn arrow
        actorHUD.deleteArrow();

        // Hide building information
        actorBuildings.hideInformation();

        // Project the screen coordinates in the game coordinates
        Vector2 selectedBuildingCoords = unprojectVector2(lastTouchDown);
        Vector2 destinationBuildingCoords = unprojectVector2(lastTouchUp);

        // Get the selected buildings
        Building selectedBuilding = getSelectedBuilding(selectedBuildingCoords);
        Building destinationBuilding = getSelectedBuilding(destinationBuildingCoords);

        if (selectedBuilding != null && destinationBuilding != null) {
            // TODO Extract this code to fit the principle of an event-based game (we'll use the very same
            //  events for the AI to play)
            if (selectedBuilding == destinationBuilding) {
                // Display more information about this building
                actorBuildings.showInformation(selectedBuilding);
            }
            else {
                // Move the units from one building to another
                gameState.getMap().sendUnit(selectedBuilding, destinationBuilding,
                        gameState.getHumanPlayer().getUnitPercentage());
            }
        }
    }


    /**
     * Handle drag event to draw an arrow between the selected building and current mouse position
     * @param posTouchDown The coordinates of last TouchDown event
     * @param posTouchDragged The coordinates of the last TouchDragged event
     */
    public void handleDrag(Vector2 posTouchDown, Vector2 posTouchDragged) {
        // Hide building information
        actorBuildings.hideInformation();

        // Project the screen coordinates in the game coordinates
        Vector2 selectedBuildingCoords = unprojectVector2(posTouchDown);
        Vector2 currentCoords = unprojectVector2(posTouchDragged);

        // Get the selected building
        Building selectedBuilding = getSelectedBuilding(selectedBuildingCoords);

        // If the blue player selects a buidling, draw an arrow
        if (selectedBuilding != null && selectedBuilding.getOwner().getColor() == Player.ColorPlayer.BLUE) {
            actorHUD.createArrow(selectedBuilding.getCoordinates(), currentCoords);
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
     * @return The corresponding building, or null if no corresponding building was found
     */
    private Building getSelectedBuilding(Vector2 coords) {
        // Fetch the coordinates of the buildings
        List<Building> buildings = gameState.getMap().getBuildings();

        // Search for the selected building
        for (Building building : buildings) {
            if (building.isSelected(coords)) {
                return building;
            }
        }
        return null;
    }

    /**
     * Handle scroll to change the percentage of unit to send to battle
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     */
    public void handleScroll(int amount) {
        int currentIndex = actorHUD.getButtonGroup().getCheckedIndex();
        int length = actorHUD.getButtonGroup().getButtons().size;
        if (amount < 0 && currentIndex > 0) {
            actorHUD.getButtonGroup().getButtons().get(currentIndex - 1).setChecked(true);
        }
        else if (amount > 0 && currentIndex < length - 1) {
            actorHUD.getButtonGroup().getButtons().get(currentIndex + 1).setChecked(true);
        }
    }
}
