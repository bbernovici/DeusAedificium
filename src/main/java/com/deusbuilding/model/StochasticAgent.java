package com.deusbuilding.model;

import javafx.scene.shape.Circle;

public class StochasticAgent extends Circle {

    private Integer hunger;
    private Integer hungerDecay;
    private Integer energy;
    private Integer energyDecay;
    private Integer hygiene;
    private Integer hygieneDecay;
    private Integer bladder;
    private Integer bladderDecay;

    public Integer getHunger() {
        return hunger;
    }

    public void setHunger(Integer hunger) {
        this.hunger = hunger;
    }

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public Integer getHygiene() {
        return hygiene;
    }

    public void setHygiene(Integer hygiene) {
        this.hygiene = hygiene;
    }

    public Integer getBladder() {
        return bladder;
    }

    public void setBladder(Integer bladder) {
        this.bladder = bladder;
    }

    public Integer getHungerDecay() {
        return hungerDecay;
    }

    public void setHungerDecay(Integer hungerDecay) {
        this.hungerDecay = hungerDecay;
    }

    public Integer getEnergyDecay() {
        return energyDecay;
    }

    public void setEnergyDecay(Integer energyDecay) {
        this.energyDecay = energyDecay;
    }

    public Integer getHygieneDecay() {
        return hygieneDecay;
    }

    public void setHygieneDecay(Integer hygieneDecay) {
        this.hygieneDecay = hygieneDecay;
    }

    public Integer getBladderDecay() {
        return bladderDecay;
    }

    public void setBladderDecay(Integer bladderDecay) {
        this.bladderDecay = bladderDecay;
    }
}
