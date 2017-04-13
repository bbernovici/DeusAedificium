package com.deusbuilding.model;

import javafx.beans.property.SimpleStringProperty;

public class Property {
    private SimpleStringProperty propertyName;
    private SimpleStringProperty propertyValue;

    public Property(String propertyName, String propertyValue) {
        this.propertyName = new SimpleStringProperty(propertyName);
        this.propertyValue = new SimpleStringProperty(propertyValue);
    }

    public String getPropertyName() {
        return propertyName.get();
    }

    public void setPropertyName(String propertyName) {
        this.propertyName.set(propertyName);
    }

    public String getPropertyValue() {
        return propertyValue.get();
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue.set(propertyValue);
    }
}
