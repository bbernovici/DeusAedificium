package com.deusbuilding.model;

import javafx.scene.shape.Line;

public class Wall extends DrawingElement {

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
