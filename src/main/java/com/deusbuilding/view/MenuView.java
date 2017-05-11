package com.deusbuilding.view;

import com.deusbuilding.window.StochasticSimulationWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class MenuView {

    public static VBox menuPane;
    public static MenuBar menuBar;
    public static Menu menuFile;
    public static Menu menuEdit;
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
        menuEdit = new Menu("Edit");
        menuView = new Menu("View");
        menuSimulation = new Menu("Simulation");


        MenuItem newProject = new MenuItem("New Scenario");
        MenuItem openProject = new MenuItem("Open Scenario");
        MenuItem closeProject = new MenuItem("Close Scenario");
        menuFile.getItems().addAll(newProject, openProject, closeProject);

        MenuItem stochasticSimulation = new MenuItem("Run Stochastic (AI-powered) Simulation");
        stochasticSimulation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StochasticSimulationWindow stochasticWindow = new StochasticSimulationWindow();
            }
        }) ;
        MenuItem deterministicSimulation = new MenuItem("Run Deterministic Simulation");
        menuSimulation.getItems().addAll(stochasticSimulation, deterministicSimulation);

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuSimulation);
        menuPane.getChildren().add(menuBar);
    }

    public Scene getScene() {
        return scene;
    }

    public Pane getMenuPane() {
        return menuPane;
    }

}
