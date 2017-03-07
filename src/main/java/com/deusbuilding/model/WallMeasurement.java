package com.deusbuilding.model;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WallMeasurement {

    private Scene scene;
    private Pane root;
    private Line wall;
    private Text unit;

    public WallMeasurement(Scene scene, Line wall, Pane root) {
        this.scene = scene;
        this.wall = wall;
        this.root = root;
        this.unit = new Text();
        unit.setFill(Color.RED);
        unit.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        updateMeasurement();
        root.getChildren().add(unit);
    }

    public void updateMeasurement() {

        Double x1 = wall.getStartX();
        Double x2 = wall.getEndX();
        Double y1 = wall.getStartY();
        Double y2 = wall.getEndY();

        //line length
        Double wallLength = Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
        Double wallLengthMeters = wallLength/50; // transform to meters
        wallLengthMeters = BigDecimal.valueOf(wallLengthMeters)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();


        unit.setX((x1+x2)/2+25);
        unit.setY((y1+y2)/2+25);
        unit.setText(wallLengthMeters.toString() + " meters");
        unit.toFront();
    }
}
