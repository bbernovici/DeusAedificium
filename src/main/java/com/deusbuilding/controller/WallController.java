package com.deusbuilding.controller;

import com.deusbuilding.model.Measurement;
import com.deusbuilding.model.Wall;
import com.deusbuilding.view.DrawingView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;

public class WallController {

    public static ArrayList<Wall> walls = new ArrayList<Wall>();
    public static ArrayList<Measurement> wallMeasurements = new ArrayList<Measurement>();

    public static void createWallDrawingEvent(final Scene scene) {
        final Pane drawingPane = DrawingView.drawingPane;
        DrawingView.drawingScrollPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ScrollPane sp = (ScrollPane) mouseEvent.getSource();
                if(mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY){
                    System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
                    System.out.println(sp.getHvalue() + " " + mouseEvent.getY());

                    Line wallLine = new Line(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
                    Measurement wallMeasurement = new Measurement(scene, wallLine, drawingPane);
                    Wall wall = new Wall(wallLine, wallMeasurement);
                    walls.add(wall);
                    drawingPane.getChildren().add(wall.getLine());
                    wallMeasurements.add(wallMeasurement);
                    wall.getLine().setStrokeWidth(10);
                    wall.getLine().setStartX(mouseEvent.getX());
                    wall.getLine().setStartY(mouseEvent.getY());
                    wall.getLine().setVisible(true);
                    wall.getLine().addEventFilter(MouseEvent.MOUSE_CLICKED, new WallModifyEventHandler());
                }
                if(mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED &&  walls.get(walls.size()-1).getLine().isVisible() && mouseEvent.getButton() == MouseButton.PRIMARY){
                    walls.get(walls.size()-1).getLine().setEndX(mouseEvent.getX());
                    walls.get(walls.size()-1).getLine().setEndY(mouseEvent.getY());
                    walls.get(walls.size()-1).getWallMeasurement().updateMeasurement();
                }
                if(mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                    ElementNavigatorController.updateWalls();
                }
            }
        });
    }

    private static class WallModifyEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("right clicked on it");
                Stage stage = new Stage();
                VBox wallStack = new VBox();
                final Line wallElement = (Line) event.getSource();
                final Label lineStartXLabel = new Label("Line start (X):");
                final TextField lineStartXText = new TextField(String.valueOf(wallElement.getStartX()));
                Label lineStartYLabel = new Label("Line start (Y):");
                final TextField lineStartYText = new TextField(String.valueOf(wallElement.getStartY()));
                Label lineEndXLabel = new Label("Line end (X):");
                final TextField lineEndXText = new TextField(String.valueOf(wallElement.getEndX()));
                Label lineEndYLabel = new Label("Line end (Y):");
                final TextField lineEndYText = new TextField(String.valueOf(wallElement.getEndY()));
                Button applyWallChangesButton = new Button("Apply changes");
                applyWallChangesButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        wallElement.setStartX(Double.valueOf(lineStartXText.getText()));
                        wallElement.setStartY(Double.valueOf(lineStartYText.getText()));
                        wallElement.setEndX(Double.valueOf(lineEndXText.getText()));
                        wallElement.setEndY(Double.valueOf(lineEndYText.getText()));
                    }
                });
                wallStack.getChildren().addAll(lineStartXLabel,
                        lineStartXText,
                        lineStartYLabel,
                        lineStartYText,
                        lineEndXLabel,
                        lineEndXText,
                        lineEndYLabel,
                        lineEndYText,
                        applyWallChangesButton);
                stage.setTitle("Modify wall");
                stage.setScene(new Scene(wallStack, 250, 400));
                stage.show();
            }
        }
    }
}
