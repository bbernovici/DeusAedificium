package com.deusbuilding.controller;

import com.deusbuilding.model.Property;
import com.deusbuilding.model.Wall;
import com.deusbuilding.util.Functions;
import com.deusbuilding.view.ElementNavigatorView;
import com.deusbuilding.view.PropertiesView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;


public class PropertiesController {


    public static void createPropertiesEvents(final Scene scene) {
        clickOnWallEvent();
    }

    public static void clickOnWallEvent() {
        ElementNavigatorView.tree.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!ElementNavigatorView.tree.getSelectionModel().isEmpty()) {
                    TreeItem<String> element = ElementNavigatorView.tree.getSelectionModel().getSelectedItem();
                    if(element.getParent().getValue().equals("Walls")) {
                        PropertiesView.propertyTable.getItems().clear();
                        Wall w = DrawingController.walls.get(element.getParent().getChildren().indexOf(element));
                        final ObservableList<Property> data = FXCollections.observableArrayList(
                                new Property("Wall start (X)", String.valueOf(Functions.transformFromPixelsToMeters(w.getLine().getStartX())) + "m"),
                                new Property("Wall start (Y)", String.valueOf(Functions.transformFromPixelsToMeters(w.getLine().getStartY()))+ "m"),
                                new Property("Wall end (X)", String.valueOf(Functions.transformFromPixelsToMeters(w.getLine().getEndX()))+ "m"),
                                new Property("Wall end (Y)", String.valueOf(Functions.transformFromPixelsToMeters(w.getLine().getEndY()))+ "m"),
                                new Property("Wall length", String.valueOf(Functions.transformFromPixelsToMeters(Math.sqrt(Math.pow(w.getLine().getEndX()-w.getLine().getStartX(), 2)+Math.pow(w.getLine().getEndY()-w.getLine().getStartY(), 2)))) + "m")
                        );
                        PropertiesView.propertyTable.setItems(data);
                    }
                }
            }
        });

    }
}
