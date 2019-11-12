package models;

import java.io.Serializable;
import java.time.LocalDate;

public class Season implements IInterval, Serializable {

    private LocalDate startingDate, endingDate;
    private int price;

    public Season(LocalDate startingDate, LocalDate endingDate) {
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.price = 0;
    }

    @Override
    public boolean contains(LocalDate startingDate, LocalDate endingDate) {
        return this.startingDate.getMonth().getValue() <= startingDate.getMonth().getValue() && startingDate.getMonth().getValue() <= this.endingDate.getMonth().getValue();
    }

    @Override
    public ICost createCost() {
        ICost cost = new Cost(price);
        return cost;
    }

    @Override
    public void setPrice(int pricePerNight) {
        price = pricePerNight;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
