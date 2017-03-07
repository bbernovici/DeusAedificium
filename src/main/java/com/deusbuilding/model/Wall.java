package com.deusbuilding.model;

import javafx.scene.Node;
import javafx.scene.shape.Line;

public class Wall {

    private Line line;
    private WallMeasurement wallMeasurement;

    public Wall(Line line, WallMeasurement wallMeasurement) {
        this.line = line;
        this.wallMeasurement = wallMeasurement;
    }

    public Line getLine() {
        return line;
    }

    public WallMeasurement getWallMeasurement() {
        return wallMeasurement;
    }
}
