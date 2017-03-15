package com.deusbuilding;

import com.deusbuilding.model.Wall;
import com.deusbuilding.view.GenericView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;

public class App extends Application
{
    Pane root = new Pane();
    final Integer PIXELS_PER_METER = 50;


    public static void main( String[] args )
    {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Deus Aedificium (Alpha)");
        GenericView genericView = GenericView.getInstance();
        genericView.setup();
        primaryStage.setScene(genericView.getTheScene());
        primaryStage.show();
    }
}
