package models;

import java.time.LocalDate;

public interface IBooking {

    void checkOut(int beds);

    boolean isCustomer(String customerSurname);

    boolean intersects(LocalDate startingDate, LocalDate endingDate);

    boolean areYou(long bookingId);

    void computeCost(int beds);

    int daysStayed();

    int getCost();

    ICustomer getCustomer();

    LocalDate getStartingDate();

    LocalDate getEndingDate();

    long getId();

    boolean startsToday();
}
