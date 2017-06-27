package com.deusbuilding.util;
import java.util.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A* implementation that allows specifying of one or more goals
 *
 * @author andre
 */
public class AStarImpl {

    /**
     * Returns the route from the start to point to a single goal
     *
     * @param <Node>
     * @param goal the goals needed to be performed
     * @param start the start point
     * @return
     */
    public static <Node extends AStarNode> Deque<Node> getRoute(Node goal, Node start) {
        Collection<Node> goals = new ArrayList<>();
        goals.add(goal);
        return getRoute(goals, start);
    }

    /**
     * Returns the route from the start point to multiple goals
     *
     * @param <Node>
     * @param goals
     * @param start
     * @return
     */
    public static <Node extends AStarNode> Deque<Node> getRoute(Collection<Node> goals, Node start) {
        if (goals.isEmpty()) {
            throw new RuntimeException("No goals in the list");
        }
        Set closedSet = new HashSet();
        Set openSet = new HashSet();
        Map<Node, Node> cameFrom = new HashMap<>();
        openSet.add(start);
        Map<Node, Double> gScore = new HashMap<>();
        gScore.put(start, 0.0);
        Map<Node, Double> fScore = new HashMap<>();
        fScore.put(start, getShortestDistance(start, goals));

        while (!openSet.isEmpty()) {
            Node current = (Node) getLowest(fScore, openSet);
            for (Node goal : goals) {
                if (current.equals(goal)) {
                    return reconstructPath(cameFrom, goal);
                }
            }
            openSet.remove(current);
            closedSet.add(current);

            Collection<Node> neighbors = current.getNeighbors();
            for (Node neighbor : neighbors) {
                if (((com.deusbuilding.util.Node) neighbor).getType() == 1) {
                    closedSet.add(neighbor);
                }
                if (closedSet.contains(neighbor))  {
                    continue;
                }
                double currentDistance = current.getDistance(neighbor);
                if (currentDistance < 0.0) {
                    throw new DistanceOutOfRangeException("Distance from the current neighbor is not a positive number");
                }
                double tentativeG = gScore.get(current) + currentDistance;
                if (!openSet.contains(neighbor) || tentativeG < gScore.get(current)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeG);
                    fScore.put(neighbor, tentativeG + getShortestDistance(neighbor, goals));
                    openSet.add(neighbor);
                }
            }
        }
        return null;
    }

    /**
     * Reconstructs the path recursively from the current node up to the
     * original node
     *
     * @param <Node>
     * @param cameFrom the origin node
     * @param current the current node
     * @return
     */
    public static <Node extends AStarNode> Deque<Node> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        Deque<Node> nodes = new ArrayDeque<>();
        if (cameFrom.containsKey(current)) {
            nodes.addAll(reconstructPath(cameFrom, cameFrom.get(current)));
        }
        nodes.add(current);
        return nodes;
    }

    /**
     * Returns the closest node from the open set.
     *
     * @param <Node>
     * @param scores the map of scores assigned to each node
     * @param openSet the set containing the visited & completed nodes (all
     * neighbors are known)
     * @return
     */
    public static <Node extends AStarNode> Node getLowest(Map<Node, Double> scores, Set<Node> openSet) {
        double shortestDistance = Const.MAX_DISTANCE;
        Node shortestNode = null;
        for (Node node : scores.keySet()) {
            if (scores.get(node) < shortestDistance && openSet.contains(node)) {
                shortestNode = node;
                shortestDistance = scores.get(node);
            }
        }
        return shortestNode;
    }

    /**
     * Returns the shortest distance between the start node and goals. Used to
     * select which goal will be executed first.
     *
     * @param <Node>
     * @param start the start node
     * @param goals the collection of goals
     * @return
     */
    public static <Node extends AStarNode> double getShortestDistance(Node start, Collection<Node> goals) {
        double distance = Const.MAX_DISTANCE;
        Iterator<Node> iterator = goals.iterator();
        while (iterator.hasNext()) {
            double tempDistance = start.getDistance(iterator.next());
            if (tempDistance < 0.0) {
                throw new DistanceOutOfRangeException("The Distance between nodes cannot be negative");
            }
            if (distance > tempDistance) {
                distance = tempDistance;
            }
        }
        return distance;
    }
}