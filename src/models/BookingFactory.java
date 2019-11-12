package models;

import java.io.Serializable;
import java.time.LocalDate;

public class BookingFactory implements Serializable,IBookingFactory {



    @Override
    public IBooking create(long bookingId, String customerName, String customerSurname, String customerPhoneNumber, LocalDate startingDate, LocalDate endingDate, ICost cost) {
        ICustomer customer = new Customer(customerName, customerSurname, customerPhoneNumber);
        return new Booking(bookingId, customer,startingDate,endingDate, cost);
    }
}
