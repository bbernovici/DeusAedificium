package com.deusbuilding.model;

import javafx.scene.shape.Circle;

public class CircularSensor extends Circle {

    public String name;
    public Double range;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRange() {
        return range;
    }

    public void setRange(Double range) {
        this.range = range;
    }
}
