package com.deusbuilding.view;

import com.deusbuilding.window.StochasticSimulationWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class MenuView {

    public static VBox menuPane;
    public static MenuBar menuBar;
    public static Menu menuFile;
    public static Menu menuView;
    public static Menu menuSimulation;
    private Scene scene;

    public MenuView(Scene scene) {
        this.scene = scene;
    }

    public void setup() {
        menuPane = new VBox();
        menuBar = new MenuBar();
        menuFile = new Menu("File");
        menuView = new Menu("View");
        menuSimulation = new Menu("Simulation");


        MenuItem newProject = new MenuItem("New Scenario");
        MenuItem openProject = new MenuItem("Open Scenario");
        MenuItem closeProject = new MenuItem("Close Scenario");
        menuFile.getItems().addAll(newProject, openProject, closeProject);

        final MenuItem toolbox = new MenuItem("Hide toolbox");
        toolbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(GenericView.toolboxView.getToolBoxPane().isVisible()) {
                    GenericView.toolboxView.getToolBoxPane().setVisible(false);
                    GenericView.genericPane.setLeft(null);
                    toolbox.setText("Show Toolbox");
                } else {
                    GenericView.toolboxView.getToolBoxPane().setVisible(true);
                    GenericView.genericPane.setLeft(GenericView.toolboxView.getToolBoxPane());
                    toolbox.setText("Hide Toolbox");
                }
            }
        });
        final MenuItem elementNavigator = new MenuItem("Hide Element Navigator");
        elementNavigator.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(GenericView.rightPane.isVisible()) {
                    GenericView.rightPane.setVisible(false);
                    GenericView.genericPane.setRight(null);
                    elementNavigator.setText("Show Element Navigator");
                } else {
                    GenericView.rightPane.setVisible(true);
                    GenericView.genericPane.setRight(GenericView.rightPane);
                    elementNavigator.setText("Hide Element Navigator");
                }
            }
        });
        menuView.getItems().addAll(toolbox, elementNavigator);

        MenuItem stochasticSimulation = new MenuItem("Run Stochastic (AI-powered) Simulation");
        stochasticSimulation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StochasticSimulationWindow stochasticWindow = new StochasticSimulationWindow();
            }
        }) ;
        MenuItem deterministicSimulation = new MenuItem("Run Deterministic Simulation");
        menuSimulation.getItems().addAll(stochasticSimulation, deterministicSimulation);

        menuBar.getMenus().addAll(menuFile, menuView, menuSimulation);
        menuPane.getChildren().add(menuBar);
    }

    public Scene getScene() {
        return scene;
    }

    public Pane getMenuPane() {
        return menuPane;
    }

}
