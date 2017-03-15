package com.deusbuilding.view;

import com.deusbuilding.controller.WallController;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static com.deusbuilding.view.DrawingView.drawingPane;

public class MenuView {

    public static VBox menuPane;
    public static MenuBar menuBar;
    public static Menu menuFile;
    public static Menu menuEdit;
    public static Menu menuView;
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
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        menuPane.getChildren().add(menuBar);
    }

    public Scene getScene() {
        return scene;
    }

    public Pane getMenuPane() {
        return menuPane;
    }

}
