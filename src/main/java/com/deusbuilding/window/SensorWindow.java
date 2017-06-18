package com.deusbuilding.window;

import com.deusbuilding.view.GenericView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        TextField circularSensorNameField = new TextField();
        Label circularSensorRangeLabel = new Label("Circular sensor range");
        TextField circularSensorRangeField = new TextField();
        Button circularSensorButton = new Button("Place circular sensor");
        circularSensorButton.setPrefHeight(50);

        circularBox.setSpacing(10);
        circularBox.getChildren().addAll(circularSensorNameLabel,
                circularSensorNameField,
                circularSensorRangeLabel,
                circularSensorRangeField,
                circularSensorButton);

        circularTab.setContent(circularBox);

        //directional
        Label directionalSensorNameLabel = new Label("Directional sensor name");
        TextField directionalSensorNameField = new TextField();
        Label directionalSensorRangeLabel = new Label("Directional sensor range");
        TextField directionalSensorRangeField = new TextField();
        Button directionalSensorButton = new Button("Place directional sensor");
        circularSensorButton.setPrefHeight(50);

        circularBox.setSpacing(10);
        directionalTab.setContent(directionalBox);

        sensorPane.getTabs().addAll(circularTab, directionalTab);

        Scene sensorScene = new Scene(sensorPane, 250, 250);
        stage.setTitle("Place sensor");
        stage.setScene(sensorScene);
        stage.show();
    }

}
