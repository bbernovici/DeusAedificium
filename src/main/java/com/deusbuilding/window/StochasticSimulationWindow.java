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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;

public class StochasticSimulationWindow {

    public Stage stage;
    public BorderPane genericStochasticView;
    public VBox rightStochasticView;
    static Pane drawingStochasticPane;
    static ScrollPane drawingStochasticScrollPane;
    static ListView<String> agentsList = new ListView<>();
    public static String status = "idle";
    final static Slider hungerSlider = new Slider(0, 4, 2);
    final static Slider energySlider = new Slider(0, 4, 2);
    final static Slider hygieneSlider = new Slider(0, 4, 2);
    final static Slider bladderSlider = new Slider(0, 4, 2);
    final static Button placeAgentButton = new Button("Place Agent");
    public int[][] schemaMatrix = new int[2000][2000];
    public int[][] scannedNodes = new int[2000][2000];
    ArrayList<ArrayList<Node>> nodes = new ArrayList<>();
    ArrayList<Line> heatLines = new ArrayList<>();
    final static TextArea console = new TextArea();


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
        Button runSimulationButton = new Button("Run Simulation");
        runSimulationButton.setMaxWidth(Double.MAX_VALUE);
        runSimulationButton.setPrefWidth(200);
        runSimulationButton.setPrefHeight(100);
        runSimulationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(runSimulationButton.getText().equals("Run Simulation")) {
                    for (StochasticAgent stochasticAgent : Vault.stochasticAgents) {
                        stochasticAgent.startAgent(nodes, stochasticAgent);
                        runSimulationButton.setText("Stop Simulation");
                    }
                } else if (runSimulationButton.getText().equals("Stop Simulation")) {
                    for (StochasticAgent stochasticAgent : Vault.stochasticAgents) {
                        stochasticAgent.stopTimer();
                        runSimulationButton.setText("Run Simulation");
                    }
                }
            }
        });

        Button runOptimization = new Button("Optimization (Heat map)");
        runOptimization.setMaxWidth(Double.MAX_VALUE);
        runOptimization.setPrefWidth(200);
        runOptimization.setPrefHeight(50);
        runOptimization.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (runOptimization.getText().equals("Optimization (Heat map)")) {
                    for (int i = 0; i < Vault.nonSmartObjects.size(); i++) {
                        for (int j = 0; j < Vault.nonSmartObjects.size(); j++) {
                            Tuple startTuple = getObjectCentroidAndPath(Vault.nonSmartObjects.get(i).getVertices());
                            Tuple goalTuple = getObjectCentroidAndPath(Vault.nonSmartObjects.get(j).getVertices());
                            Deque deque = AStarImpl.getRoute(nodes.get((int) goalTuple.x / 10).get((int) goalTuple.y / 10), nodes.get((int) startTuple.x / 10).get((int) startTuple.y / 10));
                            while (deque.size() > 1) {
                                Line line = new Line();
                                Node n = (Node) deque.pop();
                                line.setStartX(n.getPosX());
                                line.setStartY(n.getPosY());
                                Node n2 = (Node) deque.pop();
                                line.setEndX(n2.getPosX());
                                line.setEndY(n2.getPosY());
                                line.setStrokeWidth(10);
                                line.setStroke(Color.FUCHSIA);
                                heatLines.add(line);
                                drawingStochasticPane.getChildren().add(line);
                            }
                        }
                    }
                    runOptimization.setText("HIDE PATHS");
                } else {
                    for(Line l : heatLines) {
                        drawingStochasticPane.getChildren().remove(l);
                        heatLines.remove(l);
                    }
                    runOptimization.setText("Optimization (Heat map)");
                }
            }
        });



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
                removeAgentButton,
                runSimulationButton,
                runOptimization);


        console.clear();
        console.setPrefWidth(Double.MAX_VALUE);
        console.setPrefHeight(50);
        genericStochasticView.setBottom(console);

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

        for (int i = 0; i < 1000; i = i + 10) {
            ArrayList<Node> columns = new ArrayList<>();
            for (int j = 0; j < 1000; j = j + 10) {
                int status = 0;
                for(int k = i; k < i + 10; k++) {
                    for (int l = j; l < j + 10; l++) {
                        if(schemaMatrix[k][l] != 0 && schemaMatrix[k][l] != 3) {
                            status = 1;
                        }
                        if(schemaMatrix[k][l] >= 10) {
                            status = schemaMatrix[k][l];
                        }
                    }
                }
                Node node = new Node(i,j, status);
                Text t = new Text(i, j, String.valueOf(status));
                drawingStochasticPane.getChildren().add(t);
                columns.add(node);
            }
            nodes.add(columns);
        }
        createGraphFromMatrix(nodes);

//        Deque<Node> deques = AStarImpl.getRoute(nodes.get(5).get(5), nodes.get(66).get(66));
//        System.out.println(deques.size());
//        while (deques.size() != 0) {
//            Node curNode = deques.pop();
//            Text t = new Text(curNode.getPosX(), curNode.getPosY(), ".");
//            t.setFill(Color.RED);
//            t.setFont(Font.font("System", 30));
//            schemaMatrix[curNode.getPosX()][curNode.getPosY()] = 5;
//            drawingStochasticPane.getChildren().add(t);
//        }
//
//        Deque<Node> deques2 = AStarImpl.getRoute(nodes.get(50).get(98), nodes.get(5).get(98));
//        System.out.println(deques2.size());
//        while (deques2.size() != 0) {
//            Node curNode = deques2.pop();
//            Text t = new Text(curNode.getPosX(), curNode.getPosY(), ".");
//            t.setFill(Color.RED);
//            t.setFont(Font.font("System", 30));
//            schemaMatrix[curNode.getPosX()][curNode.getPosY()] = 5;
//            drawingStochasticPane.getChildren().add(t);
//        }


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

        for(int i = 1; i < 99; i++) {
            for(int j = 1; j < 99; j++) {
                if(nodes.get(i).get(j).getType() == 0 || nodes.get(i).get(j).getType() == 3 || nodes.get(i).get(j).getType() >= 10) {
                    if(nodes.get(i-1).get(j).getType() == 0 || nodes.get(i-1).get(j).getType() == 3 || nodes.get(i-1).get(j).getType() >= 10) {
                        nodes.get(i).get(j).addNeighbor(nodes.get(i - 1).get(j));
                    }
                    if(nodes.get(i+1).get(j).getType() == 0 || nodes.get(i+1).get(j).getType() == 3 || nodes.get(i+1).get(j).getType() >= 10) {
                        nodes.get(i).get(j).addNeighbor(nodes.get(i + 1).get(j));
                    }
                    if(nodes.get(i).get(j-1).getType() == 0 || nodes.get(i).get(j-1).getType() == 3 || nodes.get(i).get(j-1).getType() >= 10) {
                        nodes.get(i).get(j).addNeighbor(nodes.get(i).get(j - 1));
                    }
                    if(nodes.get(i).get(j+1).getType() == 0 || nodes.get(i).get(j+1).getType() == 3 || nodes.get(i).get(j+1).getType() >= 10) {
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

    public static void writeInConsole(String source, String message) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        console.setText(console.getText() + "[" + dateFormat.format(date) + "][" + source + "] " + message + System.lineSeparator());
    }

    public class Tuple<X, Y> {
        public final X x;
        public final Y y;
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }
    public Tuple getObjectCentroidAndPath(ArrayList<Vertex> vertices) {
        double ox = 0, oy = 0;
        Deque deque;
        int onum = 0;
        for (Vertex v : vertices) {
            ox = ox + v.getStartX() + v.getEndX();
            oy = oy + v.getStartY() + v.getEndY();
            onum += 2;
        }
        int goalX = (int) ox/onum;
        int goalY = (int) oy/onum;
        return new Tuple(goalX, goalY);
    }

//    public static void updateAgent(StochasticAgent agent, Integer x, Integer y) {
//        agent.setCenterX(x);
//        agent.setCenterY(y);
//    }
}
