package models;

import java.time.LocalDate;

public interface IInterval {
    boolean contains(LocalDate startingDate, LocalDate endingDate);

    ICost createCost();

    void setPrice(int pricePerNight);

    int getPrice();

}
