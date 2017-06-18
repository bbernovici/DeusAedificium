package com.deusbuilding.window;

import com.deusbuilding.model.Door;
import com.deusbuilding.model.NonSmartObject;
import com.deusbuilding.model.Wall;
import com.deusbuilding.model.Window;
import com.deusbuilding.util.Vault;
import com.deusbuilding.view.GenericView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StochasticSimulationWindow {

    public Stage stage;
    public BorderPane genericStochasticView;
    public VBox rightStochasticView;
    static Pane drawingStochasticPane;
    static ScrollPane drawingStochasticScrollPane;
    static ListView<String> agentsList = new ListView<>();

    public StochasticSimulationWindow() {
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(GenericView.getInstance().getTheScene().getWindow());
        genericStochasticView = new BorderPane();
        rightStochasticView = new VBox();
        rightStochasticView.setSpacing(5);
        rightStochasticView.setPadding(new Insets(5, 5, 5, 5));
        drawingStochasticPane = new Pane();
        drawingStochasticScrollPane = new ScrollPane(drawingStochasticPane);
        drawingStochasticScrollPane.setStyle("-fx-background-color: darkgrey");
        drawingStochasticScrollPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        drawingStochasticScrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        drawingStochasticScrollPane.setFitToWidth(true);
        drawingStochasticScrollPane.setFitToHeight(true);
        drawingStochasticScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        drawingStochasticScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


        final Label hungerLabel = new Label("Hunger:");
        final Label energyLabel = new Label("Energy:");
        final Label hygieneLabel = new Label("Hygiene");
        final Label bladderLabel = new Label("Bladder");

        final Slider hungerSlider = new Slider();
        final Slider energySlider = new Slider();
        final Slider hygieneSlider = new Slider();
        final Slider bladderSlider = new Slider();

        Button placeAgentButton = new Button("Place Agent");
        placeAgentButton.setMaxWidth(Double.MAX_VALUE);
        placeAgentButton.setPrefWidth(200);
        placeAgentButton.setPrefHeight(50);
        Button modifyAgentButton = new Button("Modify Agent");
        modifyAgentButton.setMaxWidth(Double.MAX_VALUE);
        modifyAgentButton.setPrefWidth(200);
        modifyAgentButton.setPrefHeight(50);
        Button removeAgentButton = new Button("Remove Agent");
        removeAgentButton.setMaxWidth(Double.MAX_VALUE);
        removeAgentButton.setPrefWidth(200);
        removeAgentButton.setPrefHeight(50);


        rightStochasticView.getChildren().addAll(hungerLabel,
                hungerSlider,
                energyLabel,
                energySlider,
                hygieneLabel,
                hygieneSlider,
                bladderLabel,
                bladderSlider,
                placeAgentButton,
                modifyAgentButton,
                removeAgentButton);

        ArrayList<Wall> walls = (ArrayList) Vault.walls.clone();
        ArrayList<Door> doors = (ArrayList) Vault.doors.clone();
        ArrayList<Window> windows = (ArrayList) Vault.windows.clone();

        //add walls/windows/doors
        for(int i = 0; i< walls.size(); i++) {
            Line line = new Line();
            line.setStartX(walls.get(i).getLine().getStartX());
            line.setStartY(walls.get(i).getLine().getStartY());
            line.setEndX(walls.get(i).getLine().getEndX());
            line.setEndY(walls.get(i).getLine().getEndY());
            line.setStrokeWidth(walls.get(i).getLine().getStrokeWidth());
            line.setStroke(walls.get(i).getLine().getStroke());
            drawingStochasticPane.getChildren().add(line);
            double mx = Math.max(line.getStartX(), line.getEndX());
            double my = Math.max(line.getStartY(), line.getEndY());

            if (mx > drawingStochasticPane.getMinWidth()) {
                drawingStochasticPane.setMinWidth(mx);
            }

            if (my > drawingStochasticPane.getMinHeight()) {
                drawingStochasticPane.setMinHeight(my);
            }
        }

        for(int i = 0; i< doors.size(); i++) {
            Line line = new Line();
            line.setStartX(doors.get(i).getLine().getStartX());
            line.setStartY(doors.get(i).getLine().getStartY());
            line.setEndX(doors.get(i).getLine().getEndX());
            line.setEndY(doors.get(i).getLine().getEndY());
            line.setStrokeWidth(doors.get(i).getLine().getStrokeWidth());
            line.setStroke(doors.get(i).getLine().getStroke());
            drawingStochasticPane.getChildren().add(line);
            double mx = Math.max(line.getStartX(), line.getEndX());
            double my = Math.max(line.getStartY(), line.getEndY());

            if (mx > drawingStochasticPane.getMinWidth()) {
                drawingStochasticPane.setMinWidth(mx);
            }

            if (my > drawingStochasticPane.getMinHeight()) {
                drawingStochasticPane.setMinHeight(my);
            }
        }

        for(int i = 0; i< windows.size(); i++) {
            Line line = new Line();
            line.setStartX(windows.get(i).getLine().getStartX());
            line.setStartY(windows.get(i).getLine().getStartY());
            line.setEndX(windows.get(i).getLine().getEndX());
            line.setEndY(windows.get(i).getLine().getEndY());
            line.setStrokeWidth(windows.get(i).getLine().getStrokeWidth());
            line.setStroke(windows.get(i).getLine().getStroke());
            drawingStochasticPane.getChildren().add(line);
            double mx = Math.max(line.getStartX(), line.getEndX());
            double my = Math.max(line.getStartY(), line.getEndY());

            if (mx > drawingStochasticPane.getMinWidth()) {
                drawingStochasticPane.setMinWidth(mx);
            }

            if (my > drawingStochasticPane.getMinHeight()) {
                drawingStochasticPane.setMinHeight(my);
            }
        }

        //add non-smart objects
        ArrayList<NonSmartObject> nonSmartObjects = (ArrayList<NonSmartObject>) Vault.nonSmartObjects.clone();
        for(int i = 0; i < nonSmartObjects.size(); i++) {
            for(int j = 0; j < nonSmartObjects.get(i).getVertices().size(); j++) {
                Line line = new Line();
                line.setStartX(nonSmartObjects.get(i).getVertices().get(j).getStartX());
                line.setStartY(nonSmartObjects.get(i).getVertices().get(j).getStartY());
                line.setEndX(nonSmartObjects.get(i).getVertices().get(j).getEndX());
                line.setEndY(nonSmartObjects.get(i).getVertices().get(j).getEndY());
                line.setStrokeWidth(nonSmartObjects.get(i).getVertices().get(j).getStrokeWidth());
                line.setStroke(nonSmartObjects.get(i).getVertices().get(j).getStroke());
                drawingStochasticPane.getChildren().add(line);
                double mx = Math.max(line.getStartX(), line.getEndX());
                double my = Math.max(line.getStartY(), line.getEndY());

                if (mx > drawingStochasticPane.getMinWidth()) {
                    drawingStochasticPane.setMinWidth(mx);
                }

                if (my > drawingStochasticPane.getMinHeight()) {
                    drawingStochasticPane.setMinHeight(my);
                }
            }
        }

        genericStochasticView.setCenter(drawingStochasticScrollPane);
        genericStochasticView.setRight(rightStochasticView);

        Scene stochasticScene = new Scene(genericStochasticView, 1000, 600);

        stage.setTitle("Stochastic Simulation");
        stage.setScene(stochasticScene);
        stage.show();
    }
}
