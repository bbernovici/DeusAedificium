package com.deusbuilding.util;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Collection;

/**
 * Any node in the graph network should have these functions
 *
 * @author andre
 */
public interface AStarNode {

        /**
         * A collect
         * @param <T>
         * @return node's neighbors
         */
        <T extends AStarNode> Collection<T> getNeighbors();

        /**
         * Finds the optimal route based on the distance between the nodes
         * @param <T>
         * @param node to compare against
         * @return the distance to target node as a double
         * @throws DistanceOutOfRangeException
         */
        public <T extends AStarNode> double getDistance(T node) throws DistanceOutOfRangeException;
}