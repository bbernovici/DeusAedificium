package com.deusbuilding.view;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PropertiesView {

    public static TableView propertyTable;
    public static TableColumn propertyNameColumn;
    public static TableColumn propertyValueColumn;

    private Scene scene;

    public PropertiesView(Scene scene) {
        this.scene = scene;
    }

    public void setup() {
        propertyTable = new TableView();
        propertyNameColumn = new TableColumn("Property name");
        propertyValueColumn = new TableColumn("Value");

        propertyTable.getColumns().addAll(propertyNameColumn, propertyValueColumn);
    }

    public Scene getScene() {
        return scene;
    }

    public TableView getPropertiesTable() {
        return propertyTable;
    }
}
