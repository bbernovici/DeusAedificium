package com.deusbuilding.view;


import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class GenericView {

    public static GenericView genericView = new GenericView();
    public static BorderPane genericPane = new BorderPane();
    final static Scene theScene = new Scene(genericPane, 1280, 720);

    private GenericView() {

    }

    public static GenericView getInstance() {
        return genericView;
    }

    public void setup() {
        MenuView menuView = new MenuView(theScene);
        menuView.setup();
        genericPane.setTop(menuView.getMenuPane());

        DrawingView.setup(theScene);
        genericPane.setCenter(DrawingView.drawingScrollPane);

        ToolboxView toolboxView = new ToolboxView(theScene);
        toolboxView.setup();
        genericPane.setLeft(toolboxView.getToolBoxPane());

        GridPane rightPane = new GridPane();

        ElementNavigatorView elementNavigatorView = new ElementNavigatorView(theScene);
        elementNavigatorView.setup();
        rightPane.setConstraints(elementNavigatorView.getNavPane(), 1, 1);
        rightPane.getChildren().addAll(elementNavigatorView.getNavPane());

        PropertiesView propertiesView = new PropertiesView(theScene);
        propertiesView.setup();
        rightPane.setConstraints(propertiesView.getPropertiesTable(), 1, 2);
        rightPane.getChildren().addAll(propertiesView.getPropertiesTable());

        genericPane.setRight(rightPane);

    }

    public BorderPane getGenericPane() {
        return genericPane;
    }

    public Scene getTheScene() {
        return theScene;
    }
}
