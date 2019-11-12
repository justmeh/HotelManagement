package models;

import java.util.Date;

public interface ICost {

    int getCost();

    void addToCost(int value);

    void computeCost(int daysStayed, int beds);
}