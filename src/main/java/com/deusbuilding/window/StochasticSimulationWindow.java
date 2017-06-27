package com.deusbuilding.window;

import com.deusbuilding.model.*;
import com.deusbuilding.util.*;
import com.deusbuilding.view.GenericView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Deque;

public class StochasticSimulationWindow {

    public Stage stage;
    public BorderPane genericStochasticView;
    public VBox rightStochasticView;
    static Pane drawingStochasticPane;
    static ScrollPane drawingStochasticScrollPane;
    static ListView<String> agentsList = new ListView<>();
    public static String status = "idle";
    final static Slider hungerSlider = new Slider(0, 10, 5);
    final static Slider energySlider = new Slider(0, 10, 5);
    final static Slider hygieneSlider = new Slider(0, 10, 5);
    final static Slider bladderSlider = new Slider(0, 10, 5);
    final static Button placeAgentButton = new Button("Place Agent");
    public int[][] schemaMatrix = new int[2000][2000];
    public int[][] scannedNodes = new int[2000][2000];
    ArrayList<ArrayList<Node>> nodes = new ArrayList<>();


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


        final Label hungerLabel = new Label("Hunger decay:");
        final Label energyLabel = new Label("Energy decay:");
        final Label hygieneLabel = new Label("Hygiene decay:");
        final Label bladderLabel = new Label("Bladder decay:");

        energySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                energyLabel.setText("Energy decay (" + newValue.intValue() + "):");
            }
        });

        hygieneSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                hygieneLabel.setText("Hygiene decay (" + newValue.intValue() + "):");
            }
        });

        bladderSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                bladderLabel.setText("Bladder decay (" + newValue.intValue() + "):");
            }
        });

        hungerSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                hungerLabel.setText("Hunger decay (" + newValue.intValue() + "):");
            }
        });

        placeAgentButton.setMaxWidth(Double.MAX_VALUE);
        placeAgentButton.setPrefWidth(200);
        placeAgentButton.setPrefHeight(50);
        placeAgentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                status = "place";
                placeAgentButton.setDisable(true);
            }
        });

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


        //zero out the matrix
        for (int i = 0; i < schemaMatrix.length; i++) {
            for (int j = 0; j < schemaMatrix.length; j++) {
                schemaMatrix[i][j] = 0;
            }
        }
        for (int i = 0; i < scannedNodes.length; i++) {
            for (int j = 0; j < scannedNodes.length; j++) {
                scannedNodes[i][j] = 0;
            }
        }


        ArrayList<Wall> walls = (ArrayList) Vault.walls.clone();
        ArrayList<Door> doors = (ArrayList) Vault.doors.clone();
        ArrayList<Window> windows = (ArrayList) Vault.windows.clone();
        ArrayList<ArrayList<AStarNode>> aStarNodes = new ArrayList<>();

        //add walls/windows/doors
        for (int i = 0; i < walls.size(); i++) {
            Line line = new Line();
            line.setStartX(walls.get(i).getLine().getStartX());
            line.setStartY(walls.get(i).getLine().getStartY());
            line.setEndX(walls.get(i).getLine().getEndX());
            line.setEndY(walls.get(i).getLine().getEndY());
            Bresenham.putLineIntoMatrix(schemaMatrix, line.getStartX(), line.getEndX(), line.getStartY(), line.getEndY(), 1);
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

        for (int i = 0; i < doors.size(); i++) {
            Line line = new Line();
            line.setStartX(doors.get(i).getLine().getStartX());
            line.setStartY(doors.get(i).getLine().getStartY());
            line.setEndX(doors.get(i).getLine().getEndX());
            line.setEndY(doors.get(i).getLine().getEndY());
            Bresenham.putLineIntoMatrix(schemaMatrix, line.getStartX(), line.getEndX(), line.getStartY(), line.getEndY(), 3);
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

        for (int i = 0; i < windows.size(); i++) {
            Line line = new Line();
            line.setStartX(windows.get(i).getLine().getStartX());
            line.setStartY(windows.get(i).getLine().getStartY());
            line.setEndX(windows.get(i).getLine().getEndX());
            line.setEndY(windows.get(i).getLine().getEndY());
            Bresenham.putLineIntoMatrix(schemaMatrix, line.getStartX(), line.getEndX(), line.getStartY(), line.getEndY(), 2);
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
        for (int i = 0; i < nonSmartObjects.size(); i++) {
            for (int j = 0; j < nonSmartObjects.get(i).getVertices().size(); j++) {
                Line line = new Line();
                line.setStartX(nonSmartObjects.get(i).getVertices().get(j).getStartX());
                line.setStartY(nonSmartObjects.get(i).getVertices().get(j).getStartY());
                line.setEndX(nonSmartObjects.get(i).getVertices().get(j).getEndX());
                line.setEndY(nonSmartObjects.get(i).getVertices().get(j).getEndY());
                Bresenham.putLineIntoMatrix(schemaMatrix, line.getStartX(), line.getEndX(), line.getStartY(), line.getEndY(), i + 10);
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

        for (int i = 0; i < 500; i++) {
            ArrayList<Node> columns = new ArrayList<>();
            for (int j = 0; j < 500; j++) {
                Node node = new Node(i,j, schemaMatrix[j][i]);
                columns.add(node);
            }
            nodes.add(columns);
        }
        createGraphFromMatrix(nodes);

        Deque<Node> deques = AStarImpl.getRoute(nodes.get(5).get(5), nodes.get(100).get(100));
        System.out.println(deques.size());
        while (deques.size() != 0) {
            Node curNode = deques.pop();
            Text t = new Text(curNode.getPosX(), curNode.getPosY(), ".");
            schemaMatrix[curNode.getPosX()][curNode.getPosY()] = 5;
            drawingStochasticPane.getChildren().add(t);
        }


        for(int i = 0; i < 500; i++) {
            for(int j = 0; j < 500; j++) {
                System.out.print(schemaMatrix[j][i]);
            }
            System.out.println();
        }
        genericStochasticView.setCenter(drawingStochasticScrollPane);
        genericStochasticView.setRight(rightStochasticView);

        Scene stochasticScene = new Scene(genericStochasticView, 1000, 600);
        createDrawingEvent(stochasticScene);

        stage.setTitle("Stochastic Simulation");
        stage.setScene(stochasticScene);
        stage.show();
    }

//    public void putLineIntoMatrix(double startX, double endX, double startY, double endY, int type) {
//        int x1, x2, y1, y2, y;
//        if(startX <= endX) {
//            x1 = (int) startX;
//            x2 = (int) endX;
//        } else {
//            x1 = (int) endX;
//            x2 = (int) startX;
//        }
//        if(startY <= endY) {
//            y1 = (int) startY;
//            y2 = (int) endY;
//        } else {
//            y1 = (int) endY;
//            y2 = (int) startY;
//        }
//
//        int dx = x2-x1;
//        int dy = y2-y1;
//        int D = 2*dy - dx;
//        y = y1;
//        for (int x = x1; x <= x2; x++) {
//            schemaMatrix[x][y] = type;
//            if(D>0) {
//                y = y+1;
//                D = D - 2*dx;
//            }
//            D = D + 2*dy;
//        }
//    }


    public void createGraphFromMatrix(ArrayList<ArrayList<Node>> nodes) {

        for(int i = 1; i < 499; i++) {
            for(int j = 1; j < 499; j++) {
                if(nodes.get(i).get(j).getType() != 1) {
                    if(nodes.get(i-1).get(j).getType() != 1) {
                        nodes.get(i).get(j).addNeighbor(nodes.get(i - 1).get(j));
                    }
                    if(nodes.get(i+1).get(j).getType() != 1) {
                        nodes.get(i).get(j).addNeighbor(nodes.get(i + 1).get(j));
                    }
                    if(nodes.get(i).get(j-1).getType() != 1) {
                        nodes.get(i).get(j).addNeighbor(nodes.get(i).get(j - 1));
                    }
                    if(nodes.get(i).get(j+1).getType() != 1) {
                        nodes.get(i).get(j).addNeighbor(nodes.get(i).get(j + 1));
                    }
                }
            }
        }
    }

    public static void createDrawingEvent(final Scene scene) {
        drawingStochasticPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (status.equals("place")) {
                        StochasticAgent agent = new StochasticAgent();
                        agent.setHunger(100);
                        agent.setBladder(100);
                        agent.setEnergy(100);
                        agent.setHygiene(100);
                        agent.setBladderDecay((int) bladderSlider.getValue());
                        agent.setEnergyDecay((int) energySlider.getValue());
                        agent.setHungerDecay((int) hungerSlider.getValue());
                        agent.setHygieneDecay((int) hygieneSlider.getValue());
                        agent.setRadius(10);
                        agent.setCenterX(mouseEvent.getX());
                        agent.setCenterY(mouseEvent.getY());
                        Vault.stochasticAgents.add(agent);
                        status = "idle";
                        placeAgentButton.setDisable(false);
                        drawingStochasticPane.getChildren().add(agent);
                    }
                }
            }
        });
    }
}
