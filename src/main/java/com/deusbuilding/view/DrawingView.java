package com.deusbuilding.view;

import com.deusbuilding.controller.DrawingController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DrawingView {

    public static ScrollPane drawingScrollPane;
    public static Pane drawingPane;
    public static DrawingController drawingController;
    public static Scene scene;

    public static void setup(Scene theScene) {
        scene = theScene;
        drawingPane = new Pane();
        drawingPane.setOnMouseEntered(new EventHandler() {
            public void handle(Event event) {
                scene.setCursor(Cursor.CROSSHAIR); //Change cursor to hand
            }
        });
        drawingPane.setOnMouseExited(new EventHandler() {
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
        drawingPane.setStyle("-fx-background-color: antiquewhite");
        drawingScrollPane = new ScrollPane(drawingPane);
        drawingScrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        drawingScrollPane.setFitToWidth(true);
        drawingScrollPane.setFitToHeight(true);
        drawingScrollPane.setStyle("-fx-focus-color: transparent;");
        drawingController = new DrawingController();
        drawingScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        drawingScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        drawingController.createDrawingEvents(GenericView.theScene);
        //drawingPane.toFront();
    }

    public static Scene getScene() {
        return scene;
    }

}
