package com.deusbuilding.model;

import javafx.scene.shape.Line;

public class Door extends DrawingElement {

    private Line line;
    private Measurement doorMeasurement;

    public Door(Line line, Measurement doorMeasurement) {
        this.line = line;
        this.doorMeasurement = doorMeasurement;
    }

    public Line getLine() {
        return line;
    }

    public Measurement getDoorMeasurement() {
        return doorMeasurement;
    }
}
