package com.deusbuilding.model;

import com.deusbuilding.window.StochasticSimulationWindow;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class CircularSensor extends Circle {

    public String name;

    public ArrayList<StochasticAgent> insideAgents = new ArrayList<>();

    public String getName() {
        return name;
    }

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

    public void checkAgentInteresct(StochasticAgent agent) {
        if (this.getBoundsInParent().intersects(agent.getBoundsInParent())) {
            if(!isAgentInside(agent)) {
                addAgentInside(agent);
                Platform.runLater(() -> agent.setFill(Color.RED));
                Platform.runLater(() -> StochasticSimulationWindow.writeInConsole("Sensor " + getName(), "Agent has entered the range at [X: " + agent.getCenterX() + "][Y: " + agent.getCenterY() + "]"));
            } else {
                Platform.runLater(() -> agent.setFill(Color.RED));
            }
        } else {
            if(isAgentInside(agent)) {
                removeAgentInside(agent);
                Platform.runLater(() -> agent.setFill(Color.BLACK));
                Platform.runLater(() -> StochasticSimulationWindow.writeInConsole("Sensor " + getName(), "Agent has left the range at [X: " + agent.getCenterX() + "][Y: " + agent.getCenterY() + "]"));
            }
        }
    }
}
