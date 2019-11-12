package models;

import java.io.Serializable;
import java.time.LocalDate;

public class Interval implements IInterval, Serializable {

    private LocalDate startingDate, endingDate;
    int pricePerNight;

    public Interval(LocalDate startingDate, LocalDate endingDate, int pricePerNight) {
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.pricePerNight = pricePerNight;
    }

    @Override
    public boolean contains(LocalDate startingDate, LocalDate endingDate) {
        return (startingDate.isAfter(this.startingDate) && startingDate.isBefore(this.endingDate)) ||
                (endingDate.isAfter(this.startingDate) && endingDate.isBefore(this.endingDate));
    }

    @Override
    public ICost createCost() {
        ICost cost = new Cost(pricePerNight);
        return cost;
    }

    @Override
    public void setPrice(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    @Override
    public int getPrice() {
        return pricePerNight;
    }
}
