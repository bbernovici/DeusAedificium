package com.deusbuilding.view;


import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

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

        ElementNavigatorView elementNavigatorView = new ElementNavigatorView(theScene);
        elementNavigatorView.setup();
        genericPane.setRight(elementNavigatorView.getNavPane());
    }

    public BorderPane getGenericPane() {
        return genericPane;
    }

    public Scene getTheScene() {
        return theScene;
    }
}
