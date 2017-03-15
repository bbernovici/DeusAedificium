package com.deusbuilding.controller;

import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;

public class ElementNavigatorController {

    private Scene scene;
    public static TreeItem<String> wallRoot = new TreeItem<> ("Walls");

    public ElementNavigatorController(Scene scene) {
        this.scene = scene;
    }

    public static void updateWalls() {
        wallRoot.getChildren().remove(0, wallRoot.getChildren().size());
        wallRoot.setExpanded(true);
        for (int i = 0; i < WallController.walls.size(); i++) {
            TreeItem<String> item = new TreeItem<> ("Wall " + i);
            wallRoot.getChildren().add(item);
        }
    }
}
