package com.deusbuilding.model;

import javafx.scene.shape.Line;


public class Window {

    private Line line;
    private Measurement windowMeasurement;

    public Window(Line line, Measurement windowMeasurement) {
        this.line = line;
        this.windowMeasurement = windowMeasurement;
    }

    public Line getLine() {
        return line;
    }

    public Measurement getWindowMeasurement() {
        return windowMeasurement;
    }
}

