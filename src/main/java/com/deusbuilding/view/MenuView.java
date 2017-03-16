package com.deusbuilding.view;

import com.deusbuilding.controller.WallController;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static com.deusbuilding.view.DrawingView.drawingPane;

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
