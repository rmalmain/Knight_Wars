package com.knightwars.game.environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Path {
    private Vector2 currentPosition;
    private Vector2 arrivalPoint;

    private List<Vector2> pointList;
    private List<Float> markerList;
    private float totalTime;

    /**
     * Current advancement on the path.
     */
    private float time;

    /**
     * Path constructor.
     *
     * @param pointList  A list of points
     * @param markerList And their time markers, used for interpolation
     * @param totalTime  Time it'll take to travel the path
     */
    public Path(List<Vector2> pointList, List<Float> markerList, float totalTime) {
        this.pointList = pointList;
        this.markerList = markerList;
        this.totalTime = totalTime;
        this.currentPosition = pointList.get(0);
        this.arrivalPoint = pointList.get(pointList.size() - 1);
        this.time = 0;
    }

    public Vector2 getArrivalPoint() {
        return this.arrivalPoint;
    }

    public Vector2 getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * Update the position of the unit
     *
     * @param dt time parameter
     */
    public void update(float dt) {
        this.time += dt;
        float t = Math.min(1, this.time / this.totalTime);
        int i = 1;
        while (i < this.markerList.size() && this.markerList.get(i) <= t) {
            i++;
        }
        if (i >= this.markerList.size()) {
            this.currentPosition = new Vector2(this.arrivalPoint);
            return;
        }
        float start = this.markerList.get(i - 1);
        float end = this.markerList.get(i);
        // Linear interpolation between two points of the list
        this.currentPosition = new Vector2(this.pointList.get(i - 1)).lerp(this.pointList.get(i),
                (t - start) / (end - start));
    }

    /**
     * Find a path between two buildings, avoinding buildings on the way.
     *
     * @param map               The map
     * @param departureBuilding The starting point
     * @param arrivalBuilding   The destination point
     */
    static public Path findPath(Map map, Building departureBuilding, Building arrivalBuilding) {
        List<Building> sortedBuildings = map.getBuildings();
        final Vector2 startingPoint = departureBuilding.getCoordinates();
        final Vector2 destinationPoint = arrivalBuilding.getCoordinates();
        final float norm = startingPoint.dst(destinationPoint);
        final Vector2 direction = new Vector2(destinationPoint).sub(startingPoint).nor();
        final Vector2 orthogonal = new Vector2(direction.y, -direction.x);

        // Sort buildings, from the closest to the furthest
        Collections.sort(sortedBuildings, new Comparator<Building>() {
            @Override
            public int compare(Building lhs, Building rhs) {
                float diff = startingPoint.dst2(lhs.getCoordinates()) - startingPoint.dst2(rhs.getCoordinates());
                return (int) Math.signum(diff);
            }
        });

        List<Vector2> pointList = new ArrayList<>();
        float totalDistance = 0;
        pointList.add(startingPoint);
        float radius = Building.SELECTION_THRESHOLD * 1.5f;
        for (Building building : sortedBuildings) {
            if (building == departureBuilding) {
                continue;
            }
            Vector2 center = building.getCoordinates();
            Vector2 relativeCenter = new Vector2(center).sub(startingPoint);
            if (building == arrivalBuilding || relativeCenter.len() >= norm) {
                break; // We've tested all the buildings that might have been on the way
            }
            if (relativeCenter.dot(direction) <= 0) {
                continue; // The building is in the wrong direction
            }
            float dot = orthogonal.dot(relativeCenter);
            // There's a building on the way
            if (Math.abs(dot) < radius) {
                Vector2 orthoProj = new Vector2(direction).scl(direction.dot(relativeCenter));
                float unit = Math.signum(orthogonal.dot(relativeCenter));
                if (unit == 0.0f) {
                    unit = 1.0f;
                }
                float cos = dot / radius;
                float sin = (float) Math.sqrt(1 - cos * cos);
                // Three point deviation
                Vector2 first = new Vector2(direction).scl(-sin * radius).add(orthoProj).add(startingPoint);
                Vector2 second = new Vector2(orthogonal).scl(-unit * radius).add(center);
                Vector2 third = new Vector2(direction).scl(sin * radius).add(orthoProj).add(startingPoint);
                totalDistance += pointList.get(pointList.size() - 1).dst(first);
                totalDistance += 2 * 1.41421 * Math.sqrt(1 - cos) * radius; // Did the math once
                pointList.add(first);
                pointList.add(second);
                pointList.add(third);
            }
        }
        totalDistance += pointList.get(pointList.size() - 1).dst(destinationPoint);
        pointList.add(destinationPoint);
        // Our path is `totalDistance` long

        Vector2 previous = null;
        float distance = 0;
        // Let's compute how far from the start a point is.
        List<Float> markerList = new ArrayList<>();
        markerList.add(0f);
        for (Vector2 point : pointList) {
            if (previous == null) {
                previous = point;
                continue;
            }
            distance += previous.dst(point);
            markerList.add(Math.min(1, distance / totalDistance)); // Clamp values > 1
            previous = point;
        }

        float totalTime = totalDistance / Unit.DEFAULT_UNIT_SPEED;
        return new Path(pointList, markerList, totalTime);
    }
}
