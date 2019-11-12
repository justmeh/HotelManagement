package models;

import java.time.LocalDate;

public interface IBookingFactory {

    IBooking create(long bookingId, String customerName, String customerSurname, String customerPhoneNumber, LocalDate startingDate, LocalDate endingDate, ICost cost);
}
