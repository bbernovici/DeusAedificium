package com.deusbuilding.model;

import javafx.scene.shape.Line;

public class Vertex extends Line{
    private Measurement vertexMeasurement;
    private String group;

    public Vertex(double startX, double startY, double endX, double endY, Measurement vertexMeasurement, String group) {
        super(startX, startY, endX, endY);
        this.vertexMeasurement = vertexMeasurement;
        this.group = group;
    }

    public Measurement getVertexMeasurementMeasurement() {
        return vertexMeasurement;
    }

    public String getGroup() {
        return group;
    }
}