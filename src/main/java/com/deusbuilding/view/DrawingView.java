package com.deusbuilding.view;

import com.deusbuilding.controller.WallController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class DrawingView {

    private Pane drawingPane;
    private WallController wallController;
    private Scene scene;

    public DrawingView(Scene scene) {
        this.scene = scene;
    }

    public void setup() {
        drawingPane = new Pane();
        wallController = new WallController(scene, drawingPane);
        wallController.createWallDrawingEvent();
    }

    public Scene getScene() {
        return scene;
    }

    public Pane getDrawingPane() {
        return drawingPane;
    }

}
