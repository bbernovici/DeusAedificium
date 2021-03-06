package com.deusbuilding.controller;

import com.deusbuilding.model.*;
import com.deusbuilding.util.Functions;
import com.deusbuilding.util.Vault;
import com.deusbuilding.view.DrawingView;
import com.deusbuilding.view.ToolboxView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

import static com.deusbuilding.view.ToolboxView.resetOtherButtons;
import static com.deusbuilding.view.ToolboxView.selectButton;

public class DrawingController {

    public static void createDrawingEvents(final Scene scene) {
        createWallDrawingEvent(scene);
        createDoorDrawingEvent(scene);
        createWindowDrawingEvent(scene);
        createNonSmartObjectDrawingEvent(scene);
        createSensorDrawingEvent(scene);
        createMoveEvent();
    }

    static HashMap hm = new HashMap();

    public static Line drawLine(MouseEvent mouseEvent) {
        final Line line = new Line(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());

        // select tool behavior
        line.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (ToolboxView.selectedTool.equals("select")) {
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        if (mouseEvent.getSource().getClass() == Line.class) {
                            System.out.println("A INTRAT!");
                            Line currentSelected = (Line) mouseEvent.getSource();
                            if (mouseEvent.isControlDown()) {
                                if (currentSelected.getStroke() == Color.RED) {
                                    currentSelected.setStroke(Vault.selectedElements.get(currentSelected));
                                    Vault.selectedElements.remove(currentSelected);
                                } else {
                                    Vault. selectedElements.put(currentSelected, currentSelected.getStroke());
                                    currentSelected.setStroke(Color.RED);
                                }
                            } else {
                                if (currentSelected.getStroke() == Color.RED) {
                                    for (Shape s : Vault.selectedElements.keySet()) {
                                        s.setStroke(Vault.selectedElements.get(s));
                                    }
                                    Vault.selectedElements.clear();
                                } else {
                                    for (Shape s : Vault.selectedElements.keySet()) {
                                        s.setStroke(Vault.selectedElements.get(s));
                                    }
                                    Vault.selectedElements.clear();
                                    Vault.selectedElements.put(currentSelected, currentSelected.getStroke());
                                    currentSelected.setStroke(Color.RED);
                                }
                            }
                        }
                    }
                }
            }
        });

        return line;
    }

    public static void createMoveEvent() {
        DrawingView.drawingScrollPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (ToolboxView.selectedTool.equals("move")) {
                    double previousX = 0, previousY = 0;

                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        previousX = mouseEvent.getX();
                        previousY = mouseEvent.getY();
                        System.out.println("org: " + Vault.selectedElements.size());
                        hm = (HashMap) Vault.selectedElements.clone();
                        System.out.println("clone: " + hm.size());
                    }
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        //get minimum start x and y from selection
                        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
                        for (Shape s : Vault.selectedElements.keySet()) {
                            if (s.getClass() == Line.class || s.getClass() == Vertex.class) {
                                Line l = (Line) s;
                                if (l.getStartX() < minX) {
                                    minX = l.getStartX();
                                }
                                if (l.getStartY() < minY) {
                                    minY = l.getStartY();
                                }
                            }
                        }
                        for (Shape s : Vault.selectedElements.keySet()) {
                            if (s.getClass() == Line.class || s.getClass() == Vertex.class) {
                                Line l = (Line) s;
                                System.out.println(hm.size());
                                double dirX = minX > mouseEvent.getX() ? -5 : 5;
                                double dirY = minY > mouseEvent.getY() ? -5 : 5;
                                l.setStartX(l.getStartX() + dirX);
                                l.setEndX(l.getEndX() + dirX);
                                l.setStartY(l.getStartY() + dirY);
                                l.setEndY(l.getEndY() + dirY);
                                System.out.println((mouseEvent.getX() - previousX));
                                System.out.println((mouseEvent.getY() - previousY));
                                double mx = Math.max(l.getStartX(), l.getEndX());
                                double my = Math.max(l.getStartY(), l.getEndY());

                                if (mx > DrawingView.drawingPane.getMinWidth()) {
                                    DrawingView.drawingPane.setMinWidth(mx);
                                }

                                if (my > DrawingView.drawingPane.getMinHeight()) {
                                    DrawingView.drawingPane.setMinHeight(my);
                                }
                            }
                        }
                        redrawMeasurements();
                    }
                }
            }
        });
    }

    public static void createWallDrawingEvent(final Scene scene) {
        final Pane drawingPane = DrawingView.drawingPane;
        DrawingView.drawingPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (ToolboxView.selectedTool.equals("wall")) {
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        Line line = drawLine(mouseEvent);
                        line.setStroke(Color.BLACK);
                        Measurement measurement = new Measurement(scene, line, drawingPane, Color.GREY);
                        Wall wall = new Wall(line, measurement);
                        Vault.walls.add(wall);
                        drawingPane.getChildren().add(wall.getLine());
                        Vault.measurements.add(measurement);
                        wall.getLine().setStrokeWidth(10);
                        wall.getLine().setStartX(mouseEvent.getX());
                        wall.getLine().setStartY(mouseEvent.getY());
                        wall.getLine().setVisible(true);
                        wall.getLine().addEventFilter(MouseEvent.MOUSE_CLICKED, new LineModifyEventHandler());
                    }
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && Vault.walls.get(Vault.walls.size() - 1).getLine().isVisible() && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        Vault.walls.get(Vault.walls.size() - 1).getLine().setEndX(mouseEvent.getX());
                        Vault.walls.get(Vault.walls.size() - 1).getLine().setEndY(mouseEvent.getY());
                        Vault.walls.get(Vault.walls.size() - 1).getWallMeasurement().updateMeasurement();
                        double mx = Math.max(Vault.walls.get(Vault.walls.size() - 1).getLine().getStartX(), Vault.walls.get(Vault.walls.size() - 1).getLine().getEndX());
                        double my = Math.max(Vault.walls.get(Vault.walls.size() - 1).getLine().getStartY(), Vault.walls.get(Vault.walls.size() - 1).getLine().getEndY());

                        if (mx > drawingPane.getMinWidth()) {
                            drawingPane.setMinWidth(mx);
                        }

                        if (my > drawingPane.getMinHeight()) {
                            drawingPane.setMinHeight(my);
                        }
                    }
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        ElementNavigatorController.updateWalls();
                    }
                }
            }
        });
    }

    public static void createDoorDrawingEvent(final Scene scene) {
        final Pane drawingPane = DrawingView.drawingPane;
        DrawingView.drawingPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (ToolboxView.selectedTool.equals("door")) {
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        Line line = drawLine(mouseEvent);
                        line.setStroke(Color.SADDLEBROWN);
                        Measurement measurement = new Measurement(scene, line, drawingPane, Color.BROWN);
                        Door door = new Door(line, measurement);
                        Vault.doors.add(door);
                        drawingPane.getChildren().add(door.getLine());
                        Vault.measurements.add(measurement);
                        door.getLine().setStrokeWidth(10);
                        door.getLine().setStartX(mouseEvent.getX());
                        door.getLine().setStartY(mouseEvent.getY());
                        door.getLine().setVisible(true);
                        door.getLine().addEventFilter(MouseEvent.MOUSE_CLICKED, new LineModifyEventHandler());
                    }
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && Vault.doors.get(Vault.doors.size() - 1).getLine().isVisible() && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        Vault.doors.get(Vault.doors.size() - 1).getLine().setEndX(mouseEvent.getX());
                        Vault.doors.get(Vault.doors.size() - 1).getLine().setEndY(mouseEvent.getY());
                        Vault.doors.get(Vault.doors.size() - 1).getDoorMeasurement().updateMeasurement();
                        double mx = Math.max(Vault.doors.get(Vault.doors.size() - 1).getLine().getStartX(), Vault.doors.get(Vault.doors.size() - 1).getLine().getEndX());
                        double my = Math.max(Vault.doors.get(Vault.doors.size() - 1).getLine().getStartY(), Vault.doors.get(Vault.doors.size() - 1).getLine().getEndY());

                        if (mx > drawingPane.getMinWidth()) {
                            drawingPane.setMinWidth(mx);
                        }

                        if (my > drawingPane.getMinHeight()) {
                            drawingPane.setMinHeight(my);
                        }
                    }
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        ElementNavigatorController.updateDoors();
                    }
                }
            }
        });
    }

    public static void createWindowDrawingEvent(final Scene scene) {
        final Pane drawingPane = DrawingView.drawingPane;
        DrawingView.drawingPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (ToolboxView.selectedTool.equals("window")) {
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        Line line = drawLine(mouseEvent);
                        line.setStroke(Color.LIGHTBLUE);
                        Measurement measurement = new Measurement(scene, line, drawingPane, Color.BLUE);
                        Window window = new Window(line, measurement);
                        Vault.windows.add(window);
                        drawingPane.getChildren().add(window.getLine());
                        Vault.measurements.add(measurement);
                        window.getLine().setStrokeWidth(10);
                        window.getLine().setStartX(mouseEvent.getX());
                        window.getLine().setStartY(mouseEvent.getY());
                        window.getLine().setVisible(true);
                        window.getLine().addEventFilter(MouseEvent.MOUSE_CLICKED, new LineModifyEventHandler());
                    }
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && Vault.windows.get(Vault.windows.size() - 1).getLine().isVisible() && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        Vault.windows.get(Vault.windows.size() - 1).getLine().setEndX(mouseEvent.getX());
                        Vault.windows.get(Vault.windows.size() - 1).getLine().setEndY(mouseEvent.getY());
                        Vault.windows.get(Vault.windows.size() - 1).getWindowMeasurement().updateMeasurement();
                        double mx = Math.max(Vault.windows.get(Vault.windows.size() - 1).getLine().getStartX(), Vault.windows.get(Vault.windows.size() - 1).getLine().getEndX());
                        double my = Math.max(Vault.windows.get(Vault.windows.size() - 1).getLine().getStartY(), Vault.windows.get(Vault.windows.size() - 1).getLine().getEndY());

                        if (mx > drawingPane.getMinWidth()) {
                            drawingPane.setMinWidth(mx);
                        }

                        if (my > drawingPane.getMinHeight()) {
                            drawingPane.setMinHeight(my);
                        }
                    }
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        ElementNavigatorController.updateWindows();
                    }
                }
            }
        });
    }


    public static void createNonSmartObjectDrawingEvent(final Scene scene) {
        final Pane drawingPane = DrawingView.drawingPane;
        DrawingView.drawingPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (ToolboxView.selectedTool.equals("non-smart")) {
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        NonSmartObject newObject = new NonSmartObject(Vault.objectToBePlaced.getObjectName(), Vault.objectToBePlaced.getObjectType());
                        newObject.setVertices(Vault.objectToBePlaced.getVertices());
                        Vault.nonSmartObjects.add(newObject);
                        String hash = new BigInteger(130, new SecureRandom()).toString(32);
                        int i = 0;
                        for (Vertex v : Vault.nonSmartObjects.get(Vault.nonSmartObjects.size() - 1).getVertices()) {
                            Vertex newVertex = new Vertex(v.getStartX(), v.getStartY(), v.getEndX(), v.getEndY(), v.getVertexMeasurementMeasurement(), v.getGroup());
                            newVertex.setHash(hash);
                            Vault.nonSmartObjects.get(Vault.nonSmartObjects.size() - 1).getVertices().set(i, newVertex);
                            i++;
                            drawingPane.getChildren().add(newVertex);
                            newVertex.setStrokeWidth(5);
                            newVertex.setVisible(true);
                            //select tool for vertex
                            newVertex.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    if (ToolboxView.selectedTool.equals("select")) {
                                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                                            if (mouseEvent.getSource().getClass() == Vertex.class) {
                                                Vertex currentSelected = (Vertex) mouseEvent.getSource();
                                                if (mouseEvent.isControlDown()) {
                                                    if (currentSelected.getStroke() == Color.RED) {
                                                        for (NonSmartObject nso : Vault.nonSmartObjects) {
                                                            for (Vertex v : nso.getVertices()) {
                                                                if (currentSelected.getHash().equals(v.getHash())) {
                                                                    v.setStroke(Vault.selectedElements.get(v));
                                                                    Vault.selectedElements.remove(v);
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        for (NonSmartObject nso : Vault.nonSmartObjects) {
                                                            for (Vertex v : nso.getVertices()) {
                                                                if (currentSelected.getHash().equals(v.getHash())) {
                                                                    Vault.selectedElements.put(v, v.getStroke());
                                                                    v.setStroke(Color.RED);
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    if (currentSelected.getStroke() == Color.RED) {
                                                        for (Shape s : Vault.selectedElements.keySet()) {
                                                            s.setStroke(Vault.selectedElements.get(s));
                                                        }
                                                        Vault.selectedElements.clear();
                                                    } else {
                                                        for (Shape s : Vault.selectedElements.keySet()) {
                                                            s.setStroke(Vault.selectedElements.get(s));
                                                        }
                                                        Vault.selectedElements.clear();
                                                        for (NonSmartObject nso : Vault.nonSmartObjects) {
                                                            for (Vertex v : nso.getVertices()) {
                                                                System.out.println(v.getHash());
                                                                if (currentSelected.getHash().equals(v.getHash())) {
                                                                    System.out.println(v.getHash());
                                                                    Vault.selectedElements.put(v, v.getStroke());
                                                                    v.setStroke(Color.RED);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                            resetOtherButtons();
                            ToolboxView.selectedTool = "select";
                            DrawingView.drawingPane.setCursor(Cursor.DEFAULT);
                            selectButton.setStyle("-fx-background-color: lightcoral");
                        }
                    }
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        ElementNavigatorController.updateNonSmartObjects();
                    }
                }
            }
        });
    }

    public static void createSensorDrawingEvent(final Scene scene) {
        final Pane drawingPane = DrawingView.drawingPane;
        DrawingView.drawingPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (ToolboxView.selectedTool.equals("sensor")) {
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        if(Vault.sensorTypeToBePlaced.equals("circular")) {
                            Vault.circularSensorToBePlaced.setCenterX(mouseEvent.getX());
                            Vault.circularSensorToBePlaced.setCenterY(mouseEvent.getY());
                            drawingPane.getChildren().add(Vault.circularSensorToBePlaced);
                            Vault.circularSensors.add(Vault.circularSensorToBePlaced);
                            resetOtherButtons();
                            ToolboxView.selectedTool = "select";
                            DrawingView.drawingPane.setCursor(Cursor.DEFAULT);
                            selectButton.setStyle("-fx-background-color: lightcoral");

                        }
                        if(Vault.sensorTypeToBePlaced.equals("directional")) {
                            Vault.directionalSensorToBePlaced.setCenterX(mouseEvent.getX());
                            Vault.directionalSensorToBePlaced.setCenterY(mouseEvent.getY());
                            drawingPane.getChildren().add(Vault.directionalSensorToBePlaced);
                            Vault.directionalSensors.add(Vault.directionalSensorToBePlaced);
                            resetOtherButtons();
                            ToolboxView.selectedTool = "select";
                            DrawingView.drawingPane.setCursor(Cursor.DEFAULT);
                            selectButton.setStyle("-fx-background-color: lightcoral");
                        }
                        System.out.println("EMENDEMS!!!");
                    }
                }
            }
        });
    }

    public static void redrawMeasurements() {
        for (int i = 0; i < Vault.walls.size(); i++) {
            Vault.walls.get(i).getWallMeasurement().updateMeasurement();
        }
        for (int i = 0; i < Vault.doors.size(); i++) {
            Vault.doors.get(i).getDoorMeasurement().updateMeasurement();
        }
        for (int i = 0; i < Vault.windows.size(); i++) {
            Vault.windows.get(i).getWindowMeasurement().updateMeasurement();
        }
    }

    private static class CircularSensorModifyEventHandler implements EventHandler {

        @Override
        public void handle(Event event) {

        }
    }

    private static class LineModifyEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY && ToolboxView.selectedTool.equals("select")) {
                Stage stage = new Stage();
                VBox wallStack = new VBox();
                final Line element = (Line) event.getSource();
                final Label lineStartXLabel = new Label("Element start (X):");
                final TextField lineStartXText = new TextField(String.valueOf(Functions.transformFromPixelsToMeters(element.getStartX())));
                Label lineStartYLabel = new Label("Element start (Y):");
                final TextField lineStartYText = new TextField(String.valueOf(Functions.transformFromPixelsToMeters(element.getStartY())));
                Label lineEndXLabel = new Label("Element end (X):");
                final TextField lineEndXText = new TextField(String.valueOf(Functions.transformFromPixelsToMeters(element.getEndX())));
                Label lineEndYLabel = new Label("Element end (Y):");
                final TextField lineEndYText = new TextField(String.valueOf(Functions.transformFromPixelsToMeters(element.getEndY())));
                Button applyWallChangesButton = new Button("Apply changes");
                applyWallChangesButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        element.setStartX(Functions.transformFromMetersToPixels(Double.valueOf(lineStartXText.getText())));
                        element.setStartY(Functions.transformFromMetersToPixels(Double.valueOf(lineStartYText.getText())));
                        element.setEndX(Functions.transformFromMetersToPixels(Double.valueOf(lineEndXText.getText())));
                        element.setEndY(Functions.transformFromMetersToPixels(Double.valueOf(lineEndYText.getText())));
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
                stage.setTitle("Modify element");
                stage.setScene(new Scene(wallStack, 250, 400));
                stage.show();
            }
        }
    }
}
