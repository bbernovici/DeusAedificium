package com.deusbuilding.view;

import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;

public class ElementNavigatorView {

    public static Pane navPane;
    private Scene scene;
    public static TreeItem<String> elementsRoot = new TreeItem<> ("Elements");
    public static TreeView<String> tree = new TreeView<>(elementsRoot);
    public static TreeItem<String> wallRoot = new TreeItem<> ("Walls");
    public static TreeItem<String> doorRoot = new TreeItem<>("Doors");
    public static TreeItem<String> windowRoot = new TreeItem<>("Windows");
    public static TreeItem<String> nonSmartRoot = new TreeItem<>("Non-Smart Objects");


    public ElementNavigatorView(Scene scene) {
        this.scene = scene;
    }

    public void setup() {
        navPane = new Pane();
        elementsRoot.setExpanded(true);
        elementsRoot.getChildren().addAll(wallRoot, doorRoot, windowRoot, nonSmartRoot);
        navPane.getChildren().add(tree);
    }

    public Pane getNavPane() {
        return navPane;
    }
}
