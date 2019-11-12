package models;

import java.time.LocalDate;
import java.util.List;

public interface IRoom {

    void book(long bookingId, String customerName, String customerSurname, String customerPhoneNumber, LocalDate startingDate, LocalDate endingDate, ICost cost);

    void cancelBooking(long bookingId);

    void checkIn(long bookingId);

    void checkOut();

    boolean isFree(LocalDate startingDate, LocalDate endingDate);

    boolean areYou(int roomNumber);

    IBooking findBooking(long bookingId);

    List<IBooking> findAllBooking(String customerSurname);

    int getCost();

    int getRoomNumber();

    int getBedsNumber();

    String getStatus();

    List<IBooking> getBookingList();

    IBooking getCheckedInBooking();

    IBooking getCheckedOutBooking();

    IBooking getTodayBooking();
}
