package com.deusbuilding.controller;

import com.deusbuilding.model.Door;
import com.deusbuilding.model.Measurement;
import com.deusbuilding.model.Wall;
import com.deusbuilding.model.Window;
import com.deusbuilding.view.DrawingView;
import com.deusbuilding.view.ToolboxView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class DrawingController {

    public static ArrayList<Wall> walls = new ArrayList<Wall>();
    public static ArrayList<Door> doors = new ArrayList<Door>();
    public static ArrayList<Window> windows = new ArrayList<Window>();
    public static ArrayList<Measurement> measurements = new ArrayList<Measurement>();
    public static HashMap<Shape, Paint> selectedElements = new HashMap();

    public static void createDrawingEvents(final Scene scene) {
        createWallDrawingEvent(scene);
        createDoorDrawingEvent(scene);
        createWindowDrawingEvent(scene);
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
                                    currentSelected.setStroke(selectedElements.get(currentSelected));
                                    selectedElements.remove(currentSelected);
                                } else {
                                    selectedElements.put(currentSelected, currentSelected.getStroke());
                                    currentSelected.setStroke(Color.RED);
                                }
                            } else {
                                if (currentSelected.getStroke() == Color.RED) {
                                    for (Shape s : selectedElements.keySet()) {
                                        s.setStroke(selectedElements.get(s));
                                    }
                                    selectedElements.clear();
                                } else {
                                    for (Shape s : selectedElements.keySet()) {
                                        s.setStroke(selectedElements.get(s));
                                    }
                                    selectedElements.clear();
                                    selectedElements.put(currentSelected, currentSelected.getStroke());
                                    currentSelected.setStroke(Color.RED);
                                }
                            }
                        }
                    }
                }
            }
        });

        //move tool behavior for lines
        DrawingView.drawingScrollPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (ToolboxView.selectedTool.equals("move")) {
                    double previousX = 0, previousY = 0;

                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        previousX = mouseEvent.getX();
                        previousY = mouseEvent.getY();
                        System.out.println("org: " + selectedElements.size());
                        hm = (HashMap) selectedElements.clone();
                        System.out.println("clone: " + hm.size());
                    }
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        //get minimum start x and y from selection
                        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
                        for (Shape s : selectedElements.keySet()) {
                            if (s.getClass() == Line.class) {
                                Line l = (Line) s;
                                if(l.getStartX() < minX) {
                                    minX = l.getStartX();
                                }
                                if(l.getStartY() < minY) {
                                    minY = l.getStartY();
                                }
                            }
                        }
                        for (Shape s : selectedElements.keySet()) {
                            if (s.getClass() == Line.class) {
                                Line l = (Line) s;
                                System.out.println(hm.size());
                                double dirX = minX > mouseEvent.getX() ? -5 : 5;
                                double dirY = minY > mouseEvent.getY() ? -5 : 5;
                                l.setStartX(l.getStartX() + dirX);
                                l.setEndX(l.getEndX() + dirX);
                                l.setStartY(l.getStartY() + dirY);
                                l.setEndY(l.getEndY() + dirY);
                                System.out.println((mouseEvent.getX()-previousX));
                                System.out.println((mouseEvent.getY()-previousY));
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

        return line;
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
                        walls.add(wall);
                        drawingPane.getChildren().add(wall.getLine());
                        measurements.add(measurement);
                        wall.getLine().setStrokeWidth(10);
                        wall.getLine().setStartX(mouseEvent.getX());
                        wall.getLine().setStartY(mouseEvent.getY());
                        wall.getLine().setVisible(true);
                        wall.getLine().addEventFilter(MouseEvent.MOUSE_CLICKED, new LineModifyEventHandler());
                    }
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && walls.get(walls.size() - 1).getLine().isVisible() && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        walls.get(walls.size() - 1).getLine().setEndX(mouseEvent.getX());
                        walls.get(walls.size() - 1).getLine().setEndY(mouseEvent.getY());
                        walls.get(walls.size() - 1).getWallMeasurement().updateMeasurement();
                        double mx = Math.max(walls.get(walls.size() - 1).getLine().getStartX(), walls.get(walls.size() - 1).getLine().getEndX());
                        double my = Math.max(walls.get(walls.size() - 1).getLine().getStartY(), walls.get(walls.size() - 1).getLine().getEndY());

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
                        doors.add(door);
                        drawingPane.getChildren().add(door.getLine());
                        measurements.add(measurement);
                        door.getLine().setStrokeWidth(10);
                        door.getLine().setStartX(mouseEvent.getX());
                        door.getLine().setStartY(mouseEvent.getY());
                        door.getLine().setVisible(true);
                        door.getLine().addEventFilter(MouseEvent.MOUSE_CLICKED, new LineModifyEventHandler());
                    }
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && doors.get(doors.size() - 1).getLine().isVisible() && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        doors.get(doors.size() - 1).getLine().setEndX(mouseEvent.getX());
                        doors.get(doors.size() - 1).getLine().setEndY(mouseEvent.getY());
                        doors.get(doors.size() - 1).getDoorMeasurement().updateMeasurement();
                        double mx = Math.max(doors.get(doors.size() - 1).getLine().getStartX(), doors.get(doors.size() - 1).getLine().getEndX());
                        double my = Math.max(doors.get(doors.size() - 1).getLine().getStartY(), doors.get(doors.size() - 1).getLine().getEndY());

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
                        windows.add(window);
                        drawingPane.getChildren().add(window.getLine());
                        measurements.add(measurement);
                        window.getLine().setStrokeWidth(10);
                        window.getLine().setStartX(mouseEvent.getX());
                        window.getLine().setStartY(mouseEvent.getY());
                        window.getLine().setVisible(true);
                        window.getLine().addEventFilter(MouseEvent.MOUSE_CLICKED, new LineModifyEventHandler());
                    }
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && windows.get(windows.size() - 1).getLine().isVisible() && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        windows.get(windows.size() - 1).getLine().setEndX(mouseEvent.getX());
                        windows.get(windows.size() - 1).getLine().setEndY(mouseEvent.getY());
                        windows.get(windows.size() - 1).getWindowMeasurement().updateMeasurement();
                        double mx = Math.max(windows.get(windows.size() - 1).getLine().getStartX(), windows.get(windows.size() - 1).getLine().getEndX());
                        double my = Math.max(windows.get(windows.size() - 1).getLine().getStartY(), windows.get(windows.size() - 1).getLine().getEndY());

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

    public static void redrawMeasurements() {
        for(int i = 0; i<walls.size(); i++) {
            walls.get(i).getWallMeasurement().updateMeasurement();
        }
        for(int i = 0; i<doors.size(); i++) {
            doors.get(i).getDoorMeasurement().updateMeasurement();
        }
        for(int i = 0; i<windows.size(); i++) {
            windows.get(i).getWindowMeasurement().updateMeasurement();
        }
    }

    private static class LineModifyEventHandler implements EventHandler<MouseEvent> {

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
