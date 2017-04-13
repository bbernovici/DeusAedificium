package com.deusbuilding.view;

import com.deusbuilding.controller.PropertiesController;
import com.deusbuilding.model.Property;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PropertiesView {

    public static TableView<Property> propertyTable;
    public static TableColumn propertyNameColumn;
    public static TableColumn propertyValueColumn;

    private Scene scene;

    public PropertiesView(Scene scene) {
        this.scene = scene;
    }

    public void setup() {
        propertyTable = new TableView<Property>();
        propertyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        propertyNameColumn = new TableColumn("Property name");
        propertyNameColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("propertyName"));
        propertyValueColumn = new TableColumn("Value");
        propertyValueColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("propertyValue"));

        propertyTable.getColumns().addAll(propertyNameColumn, propertyValueColumn);
        PropertiesController.createPropertiesEvents(scene);
    }

    public Scene getScene() {
        return scene;
    }

    public TableView getPropertiesTable() {
        return propertyTable;
    }
}
