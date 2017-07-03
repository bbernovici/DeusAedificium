package com.deusbuilding.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

import java.util.ArrayList;

public class DirectionalSensor extends Arc {

    public String name;

    public String getName() {
        return name;
    }

    public ArrayList<StochasticAgent> insideAgents = new ArrayList<>();


    public void setName(String name) {
        this.name = name;
    }

    public void addAgentInside(StochasticAgent agent) {
        insideAgents.add(agent);
    }

    public void removeAgentInside(StochasticAgent agent) {
        insideAgents.remove(agent);
    }

    public Boolean isAgentInside(StochasticAgent agent) {
        if (insideAgents.contains(agent)) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean doesAgentIntersect(StochasticAgent agent) {
        if (this.getBoundsInParent().intersects(agent.getBoundsInParent())) {
            if(!isAgentInside(agent)) {
                addAgentInside(agent);
                agent.setFill(Color.RED);
                return true;
            } else {
                agent.setFill(Color.RED);
                return true;
            }
        } else {
            removeAgentInside(agent);
            agent.setFill(Color.BLACK);
            return false;
        }
    }
}
