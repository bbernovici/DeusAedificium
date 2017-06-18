package com.deusbuilding.window;

import com.deusbuilding.model.CircularSensor;
import com.deusbuilding.model.DirectionalSensor;
import com.deusbuilding.util.Functions;
import com.deusbuilding.util.Vault;
import com.deusbuilding.view.DrawingView;
import com.deusbuilding.view.GenericView;
import com.deusbuilding.view.ToolboxView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SensorWindow {
    public Stage stage;
    public TabPane sensorPane;
    public Tab circularTab;
    public Tab directionalTab;
    public VBox circularBox;
    public VBox directionalBox;

    public SensorWindow() {
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(GenericView.getInstance().getTheScene().getWindow());
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ToolboxView.resetOtherButtons();
            }
        });
        sensorPane = new TabPane();
        circularTab = new Tab();
        directionalTab = new Tab();

        circularTab.setClosable(false);
        circularTab.setText("Circular");
        directionalTab.setClosable(false);
        directionalTab.setText("Directional");

        circularBox = new VBox();
        circularBox.setAlignment(Pos.CENTER);
        directionalBox = new VBox();
        directionalBox.setAlignment(Pos.CENTER);

        //circular
        Label circularSensorNameLabel = new Label("Circular sensor name");
        final TextField circularSensorNameField = new TextField();
        Label circularSensorRangeLabel = new Label("Circular sensor range (meters)");
        final TextField circularSensorRangeField = new TextField();
        Button circularSensorButton = new Button("Place circular sensor");
        circularSensorButton.setPrefHeight(50);
        circularSensorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(circularSensorNameField.getText().length() != 0 && circularSensorRangeField.getText().length() != 0) {
                    Vault.sensorTypeToBePlaced = "circular";
                    Vault.circularSensorToBePlaced = new CircularSensor();
                    Vault.circularSensorToBePlaced.setFill(Color.rgb(200, 200, 200, 0.5));
                    Vault.circularSensorToBePlaced.setName(circularSensorNameField.getText());
                    Vault.circularSensorToBePlaced.setRadius(Functions.transformFromMetersToPixels(Double.valueOf(circularSensorRangeField.getText())));
                    stage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText("You must complete all fields!");
                    alert.setContentText("Fill in and try again.");
                    alert.showAndWait();
                }
            }
        });

        circularBox.setSpacing(10);
        circularBox.getChildren().addAll(circularSensorNameLabel,
                circularSensorNameField,
                circularSensorRangeLabel,
                circularSensorRangeField,
                circularSensorButton);

        circularTab.setContent(circularBox);

        //directional
        Label directionalSensorNameLabel = new Label("Directional sensor name");
        final TextField directionalSensorNameField = new TextField();
        Label directionalSensorStartAngleLabel = new Label("Directional sensor start angle");
        final TextField directionalSensorStartAngleField = new TextField();
        Label directionalSensorLengthLabel = new Label("Directional sensor length angle");
        final TextField directionalSensorLengthField = new TextField();
        Label directionalSensorRangeLabel = new Label("Directional sensor range (meters)");
        final TextField directionalSensorRangeField = new TextField();
        Button directionalSensorButton = new Button("Place directional sensor");
        directionalSensorButton.setPrefHeight(50);
        directionalSensorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(directionalSensorNameField.getText().length() != 0 &&
                        directionalSensorLengthField.getText().length() != 0 &&
                        directionalSensorRangeField.getText().length() != 0 &&
                        directionalSensorStartAngleField.getText().length() != 0) {
                    Vault.sensorTypeToBePlaced = "directional";
                    Vault.directionalSensorToBePlaced = new DirectionalSensor();
                    Vault.directionalSensorToBePlaced.setFill(Color.rgb(200, 200, 200, 0.5));
                    Vault.directionalSensorToBePlaced.setName(directionalSensorNameField.getText());
                    Vault.directionalSensorToBePlaced.setStartAngle(Double.valueOf(directionalSensorStartAngleField.getText()));
                    Vault.directionalSensorToBePlaced.setLength(Double.valueOf(directionalSensorLengthField.getText()));
                    Vault.directionalSensorToBePlaced.setRadiusX(Functions.transformFromMetersToPixels(Double.valueOf(directionalSensorRangeField.getText())));
                    Vault.directionalSensorToBePlaced.setRadiusY(Functions.transformFromMetersToPixels(Double.valueOf(directionalSensorRangeField.getText())));
                    Vault.directionalSensorToBePlaced.setType(ArcType.ROUND);
                    stage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText("You must complete all fields!");
                    alert.setContentText("Fill in and try again.");
                    alert.showAndWait();
                }
            }
        });

        directionalBox.setSpacing(10);
        directionalBox.getChildren().addAll(directionalSensorNameLabel,
                directionalSensorNameField,
                directionalSensorStartAngleLabel,
                directionalSensorStartAngleField,
                directionalSensorLengthLabel,
                directionalSensorLengthField,
                directionalSensorRangeLabel,
                directionalSensorRangeField,
                directionalSensorButton);
        directionalTab.setContent(directionalBox);

        sensorPane.getTabs().addAll(circularTab, directionalTab);

        Scene sensorScene = new Scene(sensorPane, 250, 350);
        stage.setTitle("Place sensor");
        stage.setScene(sensorScene);
        stage.show();
    }

}
