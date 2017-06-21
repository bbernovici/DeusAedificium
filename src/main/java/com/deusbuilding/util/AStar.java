package com.deusbuilding.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AStar {

    public List<AStarNode> aStar(AStarNode start, AStarNode goal, ArrayList<ArrayList<AStarNode>> aStarNodes) {
        Set<AStarNode> open = new HashSet<AStarNode>();
        Set<AStarNode> closed = new HashSet<AStarNode>();

        start.g = 0;
        start.h = estimateDistance(start, goal);
        start.f = start.h;

        open.add(start);

        while (true) {
            AStarNode current = null;
            if (open.size() == 0) {
                throw new RuntimeException("no route");
            }

            for (AStarNode node : open) {
                if (current == null || node.f < current.f) {
                    current = node;
                }
            }

            if (current == goal) {
                break;
            }

            open.remove(current);
            closed.add(current);

            for (AStarNode neighborPos : current.neighbors) {
                // IF our neighbor is null or it is a wall (1) or it is a window(2) or it is a special object (>3) !(neighbor.type == 1 || neighbor.type == 2 || neighbor.type > 3)
                if(neighborPos.x >= 999 || neighborPos.y >= 999) {
                    continue;
                }
                AStarNode neighbor = aStarNodes.get(neighborPos.x).get(neighborPos.y);
                neighbor.parent = neighborPos.parent;
                neighbor.g = neighborPos.g;
                neighbor.h = neighborPos.h;
                neighbor.f = neighborPos.f;
                neighbor.cost = neighborPos.cost;
                if(neighbor.type == 1) {
                    System.out.println("da");
                }
                if (neighbor == null) {
                    continue;
                }

                int nextG = current.g + neighbor.cost;

                if (nextG < neighbor.g) {
                    open.remove(neighbor);
                    closed.remove(neighbor);
                }

                if (!open.contains(neighbor) && !closed.contains(neighbor)) {
                    if(neighbor.type != 1) {
                        neighbor.g = nextG;
                        neighbor.h = estimateDistance(neighbor, goal);
                        neighbor.f = neighbor.g + neighbor.h;
                        neighbor.parent = current;
                        open.add(neighbor);
                    }
                }
            }
        }

        List<AStarNode> nodes = new ArrayList<AStarNode>();
        AStarNode current = goal;
        while (current.parent != null) {
            nodes.add(current);
            current = current.parent;
        }
        nodes.add(start);

        return nodes;
    }

    public int estimateDistance(AStarNode node1, AStarNode node2) {
        return Math.abs(node1.x - node2.x) + Math.abs(node1.y - node2.y);
    }
}
