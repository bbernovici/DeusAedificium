package com.deusbuilding.window;

import com.deusbuilding.controller.DrawingController;
import com.deusbuilding.controller.ElementNavigatorController;
import com.deusbuilding.model.Measurement;
import com.deusbuilding.model.NonSmartObject;
import com.deusbuilding.model.Vertex;
import com.deusbuilding.view.DrawingView;
import com.deusbuilding.view.ToolboxView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.tools.Tool;
import java.util.ArrayList;
import java.util.HashMap;

public class NonSmartObjectsWindow {

    public Stage stage;
    public BorderPane genericNonSmartView;
    VBox leftNonSmartView;
    VBox rightNonSmartView;
    static Pane drawingNonSmartPane;
    ListView<String> nonSmartObjectsList;
    Button newButton, removeButton;
    Label nameLabel;
    TextField nameField;
    Label typeLabel;
    ComboBox typeField;
    static HashMap nonSmartObjects;
    public static ArrayList<Measurement> measurements = new ArrayList<Measurement>();


    public NonSmartObjectsWindow() {
        stage = new Stage();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ToolboxView.resetOtherButtons();
            }
        });
        genericNonSmartView = new BorderPane();
        leftNonSmartView = new VBox();
        rightNonSmartView = new VBox();
        drawingNonSmartPane = new Pane();
        nonSmartObjectsList = new ListView<>();
        newButton = new Button("New object");
        newButton.setMaxWidth(Double.MAX_VALUE);
        newButton.setPrefHeight(50);
        removeButton = new Button("Remove object");
        removeButton.setMaxWidth(Double.MAX_VALUE);
        removeButton.setPrefHeight(50);
        nameLabel = new Label("Non-smart object name:");
        nameField = new TextField();
        typeLabel = new Label("Non-smart object type:");
        typeField = new ComboBox();
        typeField.setMaxWidth(Double.MAX_VALUE);

        drawingNonSmartPane.setStyle("-fx-background-color: antiquewhite");
        drawingNonSmartPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        leftNonSmartView.getChildren().addAll(nonSmartObjectsList, newButton, removeButton);
        rightNonSmartView.getChildren().addAll(nameLabel, nameField, typeLabel, typeField);
        genericNonSmartView.setLeft(leftNonSmartView);
        genericNonSmartView.setCenter(drawingNonSmartPane);
        genericNonSmartView.setTop(rightNonSmartView);
        nonSmartObjects = new HashMap<String, NonSmartObject>();
        nonSmartObjects.put("currentKey", new NonSmartObject());

        Scene nonSmartScene = new Scene(genericNonSmartView, 800, 600);
        createDrawingEvent(nonSmartScene);

        stage.setTitle("Non-Smart Object Tool");
        stage.setScene(nonSmartScene);
        stage.show();
    }

    public static void createDrawingEvent(final Scene scene) {
        drawingNonSmartPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                NonSmartObject nonSmartObject = (NonSmartObject) nonSmartObjects.get("currentKey");
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                    Line line = new Line(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
                    line.setStroke(Color.BLACK);
                    Measurement measurement = new Measurement(scene, line, drawingNonSmartPane, Color.GREY);
                    Vertex vertex = new Vertex(line, measurement);
                    nonSmartObject.getVertices().add(vertex);
                    drawingNonSmartPane.getChildren().add(vertex.getLine());
                    measurements.add(measurement);
                    vertex.getLine().setStrokeWidth(10);
                    vertex.getLine().setStartX(mouseEvent.getX());
                    vertex.getLine().setStartY(mouseEvent.getY());
                    vertex.getLine().setVisible(true);
                    //vertex.getLine().addEventFilter(MouseEvent.MOUSE_CLICKED, new DrawingController.LineModifyEventHandler());
                }
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).getLine().isVisible() && mouseEvent.getButton() == MouseButton.PRIMARY) {
                    nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).getLine().setEndX(mouseEvent.getX());
                    nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).getLine().setEndY(mouseEvent.getY());
                    nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).getVertexMeasurementMeasurement().updateMeasurement();
                    double mx = Math.max(nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).getLine().getStartX(), nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).getLine().getEndX());
                    double my = Math.max(nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).getLine().getStartY(), nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).getLine().getEndY());

//                        if (mx > drawingPane.getMinWidth()) {
//                            drawingPane.setMinWidth(mx);
//                        }
//
//                        if (my > drawingPane.getMinHeight()) {
//                            drawingPane.setMinHeight(my);
//                        }
                }

            }
        });
    }

}
