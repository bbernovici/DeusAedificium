package com.deusbuilding.model;

import javafx.scene.shape.Line;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NonSmartObject {

    private ArrayList<Vertex> vertices;
    private String objectName;
    private String objectType;

    public NonSmartObject() {
        vertices = new ArrayList<>();
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
}
