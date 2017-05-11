package com.deusbuilding.controller;

import com.deusbuilding.util.Vault;
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
        for (int i = 0; i < Vault.walls.size(); i++) {
            TreeItem<String> item = new TreeItem<> ("Wall (ID: " + i + ")");
            ElementNavigatorView.wallRoot.getChildren().add(item);
        }
    }

    public static void updateDoors() {
        ElementNavigatorView.doorRoot.getChildren().remove(0, ElementNavigatorView.doorRoot.getChildren().size());
        ElementNavigatorView.doorRoot.setExpanded(true);
        for (int i = 0; i < Vault.doors.size(); i++) {
            TreeItem<String> item = new TreeItem<> ("Door (ID: " + i + ")");
            ElementNavigatorView.doorRoot.getChildren().add(item);
        }
    }

    public static void updateWindows() {
        ElementNavigatorView.windowRoot.getChildren().remove(0, ElementNavigatorView.windowRoot.getChildren().size());
        ElementNavigatorView.windowRoot.setExpanded(true);
        for (int i = 0; i < Vault.windows.size(); i++) {
            TreeItem<String> item = new TreeItem<> ("Window (ID: " + i + ")");
            ElementNavigatorView.windowRoot.getChildren().add(item);
        }
    }

    public static void updateNonSmartObjects() {
        ElementNavigatorView.nonSmartRoot.getChildren().remove(0, ElementNavigatorView.nonSmartRoot.getChildren().size());
        ElementNavigatorView.nonSmartRoot.setExpanded(true);
        for (int i = 0; i < Vault.nonSmartObjects.size(); i++) {
            TreeItem<String> item = new TreeItem<> (Vault.nonSmartObjects.get(i).getObjectType() + " (ID: " + i + ")");
            ElementNavigatorView.nonSmartRoot.getChildren().add(item);
        }
    }

}
