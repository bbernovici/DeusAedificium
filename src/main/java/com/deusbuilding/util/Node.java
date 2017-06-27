package com.deusbuilding.util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by bbernovici on 27.06.2017.
 */
public class Node implements AStarNode {

    private final Collection<Node> neighbors = new ArrayList<>();
    private int posX;
    private int posY;
    private int type;

    @Override
    public <T extends AStarNode> Collection<T> getNeighbors() {
        return (Collection<T>) neighbors;
    }

    public void addNeighbor(Node node) {
        neighbors.add(node);
    }


    public double distance(Point target) {
        Point current = new Point(getPosX(), getPosY());
        return Math.abs(current.getX() - target.getX()) + Math.abs(current.getY() - target.getY());
    }

    @Override
    public <T extends AStarNode> double getDistance(T node) throws RuntimeException {
        if (node instanceof Node) {
            Node nodeTemp = (Node) node;
            double distance = nodeTemp.distance(new Point(this.getPosX(), this.getPosY()));
                return distance;
        } else {
            throw new RuntimeException("Incompatible class type" + node.getClass());
        }

    }

    public Node() {

    }

    public Node(int x, int y, int type) {
        this.posX = x;
        this.posY = y;
        this.type = type;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
