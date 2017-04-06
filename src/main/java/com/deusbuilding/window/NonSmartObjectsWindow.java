package com.deusbuilding.window;

import com.deusbuilding.controller.DrawingController;
import com.deusbuilding.controller.ElementNavigatorController;
import com.deusbuilding.model.Measurement;
import com.deusbuilding.model.NonSmartObject;
import com.deusbuilding.model.Vertex;
import com.deusbuilding.view.DrawingView;
import com.deusbuilding.view.ToolboxView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.tools.Tool;
import java.util.ArrayList;
import java.util.HashMap;

public class NonSmartObjectsWindow {

    public Stage stage;
    public BorderPane genericNonSmartView;
    VBox leftNonSmartView;
    static Pane drawingNonSmartPane;
    static ListView<String> nonSmartObjectsList = new ListView<>();
    ObservableList objectsList;
    Button newButton, removeButton, placeButton;
    static HashMap nonSmartObjects = new HashMap<String, NonSmartObject>();
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
        drawingNonSmartPane = new Pane();
        nonSmartObjectsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + nonSmartObjectsList.getSelectionModel().getSelectedItem());
                drawingNonSmartPane.getChildren().clear();
                NonSmartObject selectedObject = ((NonSmartObject) nonSmartObjects.get(nonSmartObjectsList.getSelectionModel().getSelectedItem()));
                for (Vertex v : selectedObject.getVertices()) {
                    drawingNonSmartPane.getChildren().add(v);

                }
            }
        });

        objectsList = FXCollections.observableArrayList();
        nonSmartObjectsList.setItems(objectsList);
        newButton = new Button("New object");
        newButton.setMaxWidth(Double.MAX_VALUE);
        newButton.setPrefHeight(50);
        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                final Stage stage = new Stage();
                VBox vbox = new VBox();
                Label nameLabel;
                final TextField nameField;
                Label typeLabel;
                final ComboBox typeField;
                nameLabel = new Label("Non-smart object name:");
                nameField = new TextField();
                typeLabel = new Label("Non-smart object type:");
                ObservableList<String> types = FXCollections.observableArrayList(
                        "Static object",
                        "Bed",
                        "Shower",
                        "Sink",
                        "Toilet seat",
                        "Stove",
                        "Fridge",
                        "Washing Machine",
                        "Table",
                        "Chair",
                        "Wardrobe"
                );

                typeField = new ComboBox(types);
                typeField.setValue(types.get(0));
                typeField.setMaxWidth(Double.MAX_VALUE);
                Button createButton = new Button("Create object");
                createButton.setPrefHeight(50);
                createButton.setMaxWidth(Double.MAX_VALUE);
                createButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if(!nonSmartObjects.containsKey(nameField.getText())) {
                            nonSmartObjects.put(nameField.getText(), new NonSmartObject(nameField.getText(), typeField.getSelectionModel().getSelectedItem().toString()));
                            objectsList.add(nameField.getText());
                            System.out.println(nonSmartObjects.size());
                            stage.close();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Warning");
                            alert.setHeaderText("An object with that name already exists.");
                            alert.setContentText("Try again with a different name.");
                            alert.showAndWait();
                        }
                    }
                });
                vbox.setSpacing(10);
                vbox.getChildren().addAll(nameLabel, nameField, typeLabel, typeField, createButton);
                stage.setScene(new Scene(vbox, 300, 180));
                stage.setTitle("Create Non-Smart Object");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node) e.getSource()).getScene().getWindow());
                stage.show();
            }
        });

        removeButton = new Button("Remove object");
        removeButton.setMaxWidth(Double.MAX_VALUE);
        removeButton.setPrefHeight(50);

        placeButton = new Button("Place object");
        placeButton.setMaxWidth(Double.MAX_VALUE);
        placeButton.setPrefHeight(100);
        placeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DrawingView.drawingPane.setCursor(Cursor.OPEN_HAND);
                NonSmartObject selectedObject = ((NonSmartObject) nonSmartObjects.get(nonSmartObjectsList.getSelectionModel().getSelectedItem()));
                DrawingController.objectToBePlaced = selectedObject;
                stage.close();
            }
        });

        drawingNonSmartPane.setStyle("-fx-background-color: antiquewhite");
        drawingNonSmartPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        leftNonSmartView.getChildren().addAll(nonSmartObjectsList, newButton, removeButton, placeButton);
        genericNonSmartView.setLeft(leftNonSmartView);
        genericNonSmartView.setCenter(drawingNonSmartPane);
        //nonSmartObjects.put("currentKey", new NonSmartObject("currentKey", "Static object"));

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
                if(!nonSmartObjectsList.getSelectionModel().isEmpty()) {
                    NonSmartObject nonSmartObject = ((NonSmartObject) nonSmartObjects.get(nonSmartObjectsList.getSelectionModel().getSelectedItem()));
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        Measurement measurement = new Measurement(scene, null, drawingNonSmartPane, Color.GREY);
                        NonSmartObject selectedObject = ((NonSmartObject) nonSmartObjects.get(nonSmartObjectsList.getSelectionModel().getSelectedItem()));
                        Vertex vertex = new Vertex(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY(), measurement, selectedObject.getObjectName());
                        measurement.setLine(vertex);
                        vertex.setStroke(Color.BLACK);
                        nonSmartObject.getVertices().add(vertex);
                        drawingNonSmartPane.getChildren().add(vertex);
                        measurements.add(measurement);
                        vertex.setStrokeWidth(10);
                        vertex.setStartX(mouseEvent.getX());
                        vertex.setStartY(mouseEvent.getY());
                        vertex.setVisible(true);
                        //vertex.getLine().addEventFilter(MouseEvent.MOUSE_CLICKED, new DrawingController.LineModifyEventHandler());
                    }
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).isVisible() && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).setEndX(mouseEvent.getX());
                        nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).setEndY(mouseEvent.getY());
                        nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).getVertexMeasurementMeasurement().updateMeasurement();
                        double mx = Math.max(nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).getStartX(), nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).getEndX());
                        double my = Math.max(nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).getStartY(), nonSmartObject.getVertices().get(nonSmartObject.getVertices().size() - 1).getEndY());

//                        if (mx > drawingPane.getMinWidth()) {
//                            drawingPane.setMinWidth(mx);
//                        }
//
//                        if (my > drawingPane.getMinHeight()) {
//                            drawingPane.setMinHeight(my);
//                        }
                    }
                }
            }
        });
    }



}
