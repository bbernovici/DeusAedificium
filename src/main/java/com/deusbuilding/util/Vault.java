package com.deusbuilding.util;

import com.deusbuilding.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.HashMap;


public class Vault {

    public static ArrayList<Wall> walls = new ArrayList<Wall>();
    public static ArrayList<Door> doors = new ArrayList<Door>();
    public static ArrayList<Window> windows = new ArrayList<Window>();
    public static ArrayList<NonSmartObject> nonSmartObjects = new ArrayList<>();
    public static NonSmartObject objectToBePlaced = new NonSmartObject("empty", "empty");
    public static ArrayList<CircularSensor> circularSensors = new ArrayList<>();
    public static CircularSensor circularSensorToBePlaced = new CircularSensor();
    public static ArrayList<Measurement> measurements = new ArrayList<Measurement>();
    public static HashMap<Shape, Paint> selectedElements = new HashMap();
    public static ObservableList obsNonSmartobjectsList = FXCollections.observableArrayList();
    public static HashMap mapNonSmartObjects = new HashMap<String, NonSmartObject>();


}
