package models;

import java.io.Serializable;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

public class Booking implements IBooking, Serializable {

    long id;
    private LocalDate startingDate, endingDate;
    private LocalDate today = LocalDate.now();
    private ICustomer customer;
    private ICost cost;

    public Booking(long bookingId, ICustomer customer, LocalDate startingDate, LocalDate endingDate, ICost cost) {
        this.id = bookingId;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.customer = customer;
        this.cost = cost;
    }

    @Override
    public void checkOut(int beds) {
        if(today.isBefore(endingDate)){
            endingDate = today;
        }
        computeCost(beds);
    }

    @Override
    public boolean isCustomer(String customerSurname) {
        return customer.areYou(customerSurname);
    }

    @Override
    public boolean intersects(LocalDate startingDate, LocalDate endingDate) {
        return (startingDate.isAfter(this.startingDate) && startingDate.isBefore(this.endingDate)) ||
                (endingDate.isAfter(this.startingDate) && endingDate.isBefore(this.endingDate));
    }

    @Override
    public boolean areYou(long bookingId) {
        return id == bookingId;
    }

    @Override
    public void computeCost(int beds) {
        cost.computeCost(daysStayed(),beds);
    }

    @Override
    public int daysStayed() {
        return (int) DAYS.between(startingDate,endingDate);
    }

    @Override
    public int getCost() {
        return cost.getCost();
    }

    @Override
    public ICustomer getCustomer() {
        return customer;
    }

    @Override
    public LocalDate getStartingDate() {
        return startingDate;
    }

    @Override
    public LocalDate getEndingDate() {
        return endingDate;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean startsToday() {
        return (int)DAYS.between(today, startingDate) == 0;
    }


}
