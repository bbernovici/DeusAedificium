package com.deusbuilding.view;

import com.deusbuilding.controller.WallController;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class DrawingView {

    public static ScrollPane drawingScrollPane;
    public static Pane drawingPane;
    public static WallController wallController;
    public static Scene scene;

    public static void setup(Scene theScene) {
        scene = theScene;
        drawingScrollPane = new ScrollPane();
        drawingPane = new Pane();
        wallController = new WallController();
        drawingScrollPane.setContent(drawingPane);
        drawingScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        drawingScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        wallController.createWallDrawingEvent(GenericView.theScene);
        //drawingPane.toFront();
    }

    public static Scene getScene() {
        return scene;
    }

}
