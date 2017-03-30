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

public class Measurement {

    private Scene scene;
    private Pane root;
    private Line line;
    private Text unit;

    public Measurement(Scene scene, Line line, Pane root, Color color) {
        this.scene = scene;
        this.line = line;
        this.root = root;
        this.unit = new Text();
        unit.setFill(color);
        unit.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        if(line != null) {
            updateMeasurement();
        }
        root.getChildren().add(unit);
    }

    public void setLine(Line line) {
        this.line = line;
        updateMeasurement();
    }

    public void updateMeasurement() {

        Double x1 = line.getStartX();
        Double x2 = line.getEndX();
        Double y1 = line.getStartY();
        Double y2 = line.getEndY();

        //line length
        Double lineLength = Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
        Double lineLengthMeters = lineLength/50; // transform to meters
        lineLengthMeters = BigDecimal.valueOf(lineLengthMeters)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();


        unit.setX((x1+x2)/2+25);
        unit.setY((y1+y2)/2+25);
        unit.setText(lineLengthMeters.toString() + " meters");
        unit.toFront();
    }
}
