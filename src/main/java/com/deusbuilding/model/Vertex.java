package com.deusbuilding.model;

import javafx.scene.shape.Line;


public class Vertex {
    private Line line;
    private Measurement vertexMeasurement;

    public Vertex(Line line, Measurement vertexMeasurement) {
        this.line = line;
        this.vertexMeasurement = vertexMeasurement;
    }

    public Line getLine() {
        return line;
    }

    public Measurement getVertexMeasurementMeasurement() {
        return vertexMeasurement;
    }
}