package com.deusbuilding.controller;

import com.deusbuilding.view.ElementNavigatorView;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;

public class ElementNavigatorController {

    private Scene scene;


    public ElementNavigatorController(Scene scene) {
        this.scene = scene;
    }

    public static void updateWalls() {
        ElementNavigatorView.wallRoot.getChildren().remove(0, ElementNavigatorView.wallRoot.getChildren().size());
        ElementNavigatorView.wallRoot.setExpanded(true);
        for (int i = 0; i < DrawingController.walls.size(); i++) {
            TreeItem<String> item = new TreeItem<> ("Wall " + i);
            ElementNavigatorView.wallRoot.getChildren().add(item);
        }
    }

    public static void updateDoors() {
        ElementNavigatorView.doorRoot.getChildren().remove(0, ElementNavigatorView.doorRoot.getChildren().size());
        ElementNavigatorView.doorRoot.setExpanded(true);
        for (int i = 0; i < DrawingController.doors.size(); i++) {
            TreeItem<String> item = new TreeItem<> ("Wall " + i);
            ElementNavigatorView.doorRoot.getChildren().add(item);
        }
    }

    public static void updateWindows() {
        ElementNavigatorView.windowRoot.getChildren().remove(0, ElementNavigatorView.windowRoot.getChildren().size());
        ElementNavigatorView.windowRoot.setExpanded(true);
        for (int i = 0; i < DrawingController.windows.size(); i++) {
            TreeItem<String> item = new TreeItem<> ("Window " + i);
            ElementNavigatorView.windowRoot.getChildren().add(item);
        }
    }

}
