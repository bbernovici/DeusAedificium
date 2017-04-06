package com.deusbuilding.model;

import javafx.scene.shape.Line;

public class Vertex extends Line{
    private Measurement vertexMeasurement;
    private String group;
    private String hash;

    public Vertex(double startX, double startY, double endX, double endY, Measurement vertexMeasurement, String group) {
        super(startX, startY, endX, endY);
        this.vertexMeasurement = vertexMeasurement;
        this.group = group;
    }


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Measurement getVertexMeasurementMeasurement() {
        return vertexMeasurement;
    }

    public String getGroup() {
        return group;
    }
}