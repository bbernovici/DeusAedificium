package com.deusbuilding.view;


import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class GenericView {

    private static GenericView genericView = new GenericView();
    private BorderPane genericPane = new BorderPane();
    final Scene theScene = new Scene(genericPane, 1280, 720);

    private GenericView() {

    }

    public static GenericView getInstance() {
        return genericView;
    }

    public void setup() {
        DrawingView drawingView = new DrawingView(theScene);
        drawingView.setup();
        genericPane.setCenter(drawingView.getDrawingPane());

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
