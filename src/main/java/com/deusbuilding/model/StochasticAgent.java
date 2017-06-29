package com.deusbuilding.model;

import com.deusbuilding.util.AStarImpl;
import com.deusbuilding.util.Node;
import com.deusbuilding.util.Vault;
import com.deusbuilding.window.StochasticSimulationWindow;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.application.Platform;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StochasticAgent extends Circle {

    private Integer hunger;
    private Integer hungerDecay;
    private Integer energy;
    private Integer energyDecay;
    private Integer hygiene;
    private Integer hygieneDecay;
    private Integer bladder;
    private Integer bladderDecay;
    private ExecutorService executor;
    private String lastNeedSatisfied;
    private String status;
    private Integer goalX;
    private Integer goalY;
    private Deque<Node> deque;

    public StochasticAgent() {
        this.hunger = 100;
        this.energy = 100;
        this.hygiene = 100;
        this.bladder = 100;
        this.lastNeedSatisfied = "none";
        this.status = "idle";
        this.executor = Executors.newSingleThreadExecutor();
    }

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

    public void startAgent(ArrayList<ArrayList<Node>> nodes, StochasticAgent agent) {
        executor.submit(() -> {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (status.equals("moving")) {
                        if(deque.size() != 0) {
                            Node curNode = deque.pop();
                            Platform.runLater(() -> setCenterX(curNode.getPosX()));
                            Platform.runLater(() -> setCenterY(curNode.getPosY()));
                            hunger -= hungerDecay;
                            energy -= energyDecay;
                            hygiene -= hygieneDecay;
                            bladder -= bladderDecay;
//                            StochasticSimulationWindow.updateAgent(agent, curNode.getPosX(), curNode.getPosY());
                        } else {
                            status = "idle";
                            if(lastNeedSatisfied == "hunger") {
                                hunger = 100;
                            } else if(lastNeedSatisfied == "energy") {
                                energy = 100;
                            } else if(lastNeedSatisfied == "hygiene") {
                                hygiene = 100;
                            } else if(lastNeedSatisfied == "bladder") {
                                bladder = 100;
                            }
                        }
                        System.out.println("Agent: " + hunger + " " + energy + " " + hygiene + " " + bladder);
                    }
                    if (status.equals("idle")) {
                        if (getLowestNeed().equals("hunger")) {
                            for (int i = 0; i < Vault.nonSmartObjects.size(); i++) {
                                if (Vault.nonSmartObjects.get(i).getObjectType().equals("Hunger")) {
                                    getObjectCentroidAndPath(Vault.nonSmartObjects.get(i).getVertices(), nodes);
                                    break;
                                }
                                                            }
                        } else if (getLowestNeed().equals("energy")) {
                            for (int i = 0; i < Vault.nonSmartObjects.size(); i++) {
                                if (Vault.nonSmartObjects.get(i).getObjectType().equals("Energy")) {
                                    getObjectCentroidAndPath(Vault.nonSmartObjects.get(i).getVertices(), nodes);
                                    break;
                                }
                            }
                        } else if (getLowestNeed().equals("hygiene")) {
                            for (int i = 0; i < Vault.nonSmartObjects.size(); i++) {
                                if (Vault.nonSmartObjects.get(i).getObjectType().equals("Hygiene")) {
                                    getObjectCentroidAndPath(Vault.nonSmartObjects.get(i).getVertices(), nodes);
                                    break;
                                }
                            }
                        } else if  (getLowestNeed().equals("bladder")) {
                            for (int i = 0; i < Vault.nonSmartObjects.size(); i++) {
                                if (Vault.nonSmartObjects.get(i).getObjectType().equals("Bladder")) {
                                    getObjectCentroidAndPath(Vault.nonSmartObjects.get(i).getVertices(), nodes);
                                    break;
                                }
                            }
                        }

                    }
                    System.out.println("Merge");
                }
            }, 1000, 250);
        });
    }

    public void getObjectCentroidAndPath(ArrayList<Vertex> vertices, ArrayList<ArrayList<Node>> nodes) {
        double ox = 0, oy = 0;
        int onum = 0;
        for (Vertex v : vertices) {
            ox = ox + v.getStartX() + v.getEndX();
            oy = oy + v.getStartY() + v.getEndY();
            onum += 2;
        }
        status = "moving";
        goalX = (int) ox/onum;
        goalY = (int) oy/onum;
        deque = AStarImpl.getRoute(nodes.get(goalX/10).get(goalY/10),nodes.get((int) getCenterX()/10).get((int) getCenterY()/10));
    }

    public String getLowestNeed() {
        int index = 0;
        int[] array = new int[]{hunger, energy, hygiene, bladder};

        for (int i = 1; i < 4; i++) {
            if (array[i] < array[index]) {
                index = i;
            }
        }

        if (index == 0) {
            lastNeedSatisfied = "hunger";
            return "hunger";
        }

        if (index == 1) {
            lastNeedSatisfied = "energy";
            return "energy";
        }

        if (index == 2) {
            lastNeedSatisfied = "hygiene";
            return "hygiene";
        }

        if (index == 3) {
            lastNeedSatisfied = "bladder";
            return "bladder";
        }

        return "";
    }
}
