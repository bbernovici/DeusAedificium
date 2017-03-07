package com.deusbuilding.view;

import com.deusbuilding.controller.ElementNavigatorController;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;

public class ElementNavigatorView {

    private Pane navPane;
    private Scene scene;

    public ElementNavigatorView(Scene scene) {
        this.scene = scene;
    }

    public void setup() {
        navPane = new Pane();

        TreeView<String> tree = new TreeView<> (ElementNavigatorController.wallRoot);
        navPane.getChildren().add(tree);
    }

    public Pane getNavPane() {
        return navPane;
    }
}
