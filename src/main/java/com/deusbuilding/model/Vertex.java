package com.deusbuilding.model;

import javafx.scene.shape.Line;


public class Vertex extends Line{
    private Measurement vertexMeasurement;

    public Vertex(double startX, double startY, double endX, double endY, Measurement vertexMeasurement) {
        super(startX, startY, endX, endY);
        this.vertexMeasurement = vertexMeasurement;
    }

    public Measurement getVertexMeasurementMeasurement() {
        return vertexMeasurement;
    }
}