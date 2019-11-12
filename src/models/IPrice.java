package models;

import java.time.LocalDate;

public interface IPrice {

    ICost generateCost(LocalDate startingDate, LocalDate endingDate);

    void createDiscountInterval(LocalDate startingDate, LocalDate endingDate, int pricePerNight);

    void setSummerPrice(int pricePerNight);

    void setSpringPrice(int pricePerNight);

    void setFallPrice(int pricePerNight);

    void setWinterPrice(int pricePerNight);

    int getSummerPrice();

    int getSpringPrice();

    int getFallPrice();

    int getWinterPrice();
}
