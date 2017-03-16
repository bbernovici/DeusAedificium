package com.deusbuilding.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ToolboxView {

    public static GridPane toolBoxPane;
    public static Button selectButton;
    public static Button moveButton;
    public static Button wallButton;
    public static Button doorButton;
    public static Button windowButton;
    public static Button nonSmartButton;
    public static Button smartButton;
    public static Button sensorButton;

    private Scene scene;

    public ToolboxView(Scene scene) {
        this.scene = scene;
    }

    public void setup() {
        toolBoxPane = new GridPane();

        //select button
        Image selectIcon = new Image(getClass().getResourceAsStream("/icons/select.png"), 25d, 25d, false, false);
        selectButton = new Button("", new ImageView(selectIcon));
        Tooltip selectTooltip = new Tooltip("Select Tool");
        selectTooltip.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        selectButton.setTooltip(selectTooltip);
        toolBoxPane.setConstraints(selectButton, 1, 1);
        toolBoxPane.getChildren().addAll(selectButton);

        //move button
        Image moveIcon = new Image(getClass().getResourceAsStream("/icons/move.png"), 25d, 25d, false, false);
        moveButton = new Button("", new ImageView(moveIcon));
        Tooltip moveTooltip = new Tooltip("Move Tool");
        moveTooltip.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        moveButton.setTooltip(moveTooltip);
        toolBoxPane.setConstraints(moveButton, 2, 1);
        toolBoxPane.getChildren().addAll(moveButton);


        //wall button
        Image wallIcon = new Image(getClass().getResourceAsStream("/icons/wall.png"), 25d, 25d, false, false);
        wallButton = new Button("", new ImageView(wallIcon));
        Tooltip wallTooltip = new Tooltip("Place Wall");
        wallTooltip.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        wallButton.setTooltip(wallTooltip);
        toolBoxPane.setConstraints(wallButton, 1, 2);
        toolBoxPane.getChildren().addAll(wallButton);

        //door button
        Image doorIcon = new Image(getClass().getResourceAsStream("/icons/door.png"), 25d, 25d, false, false);
        doorButton = new Button("", new ImageView(doorIcon));
        Tooltip doorTooltip = new Tooltip("Place Door");
        doorTooltip.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        doorButton.setTooltip(doorTooltip);
        toolBoxPane.setConstraints(doorButton, 2, 2);
        toolBoxPane.getChildren().addAll(doorButton);

        //window button
        Image windowIcon = new Image(getClass().getResourceAsStream("/icons/window.png"), 25d, 25d, false, false);
        windowButton = new Button("", new ImageView(windowIcon));
        Tooltip windowTooltip = new Tooltip("Place Window");
        windowTooltip.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        windowButton.setTooltip(windowTooltip);
        toolBoxPane.setConstraints(windowButton, 1, 3);
        toolBoxPane.getChildren().addAll(windowButton);

        //non smart button
        Image nonSmartIcon = new Image(getClass().getResourceAsStream("/icons/nonsmart.png"), 25d, 25d, false, false);
        nonSmartButton = new Button("", new ImageView(nonSmartIcon));
        Tooltip nonSmartTooltip = new Tooltip("Place Non-Smart Object");
        nonSmartTooltip.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        nonSmartButton.setTooltip(nonSmartTooltip);
        toolBoxPane.setConstraints(nonSmartButton, 2, 3);
        toolBoxPane.getChildren().addAll(nonSmartButton);

        //smart button
        Image smartIcon = new Image(getClass().getResourceAsStream("/icons/smart.png"), 25d, 25d, false, false);
        smartButton = new Button("", new ImageView(smartIcon));
        Tooltip smartTooltip = new Tooltip("Place Smart Object");
        smartTooltip.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        smartButton.setTooltip(smartTooltip);
        toolBoxPane.setConstraints(smartButton, 1, 4);
        toolBoxPane.getChildren().addAll(smartButton);

        //sensor button
        Image sensorIcon = new Image(getClass().getResourceAsStream("/icons/sensor.png"), 25d, 25d, false, false);
        sensorButton = new Button("", new ImageView(sensorIcon));
        Tooltip sensorTooltip = new Tooltip("Place Sensor");
        sensorTooltip.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        sensorButton.setTooltip(sensorTooltip);
        toolBoxPane.setConstraints(sensorButton, 2, 4 );
        toolBoxPane.getChildren().addAll(sensorButton);

    }

    public Scene getScene() {
        return scene;
    }

    public GridPane getToolBoxPane() {
        return toolBoxPane;
    }
}
