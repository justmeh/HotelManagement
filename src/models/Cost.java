package models;

import java.io.Serializable;

public class Cost implements ICost, Serializable {

    int costPerNight, totalCost;

    public Cost(int costPerNight) {
        this.costPerNight = costPerNight;
        this.totalCost = 0;
    }

    @Override
    public int getCost() {
        return totalCost;
    }

    @Override
    public void addToCost(int value) {
        totalCost = totalCost + value;
    }

    @Override
    public void computeCost(int daysStayed, int beds) {
        totalCost = totalCost + costPerNight * daysStayed * beds;
    }
}
