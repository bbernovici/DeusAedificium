package com.deusbuilding.model;

import javafx.scene.Node;
import javafx.scene.shape.Line;

public class Wall {

    private Line line;
    private Measurement wallMeasurement;

    public Wall(Line line, Measurement wallMeasurement) {
        this.line = line;
        this.wallMeasurement = wallMeasurement;
    }

    public Line getLine() {
        return line;
    }

    public Measurement getWallMeasurement() {
        return wallMeasurement;
    }
}
