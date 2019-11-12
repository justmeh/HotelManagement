package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public interface IHotel {

    void book(int roomNumber, String customerName, String customerSurname, String customerPhoneNumber, LocalDate startingDate, LocalDate endingDate);

    void cancelBooking(int roomNumber, long bookingId);

    void checkIn(int roomNumber, long bookingId);

    void checkOut(int roomNumber);

    List<IRoom> findFreeRooms(LocalDate startingDate, LocalDate endingDate);

    int getCost(int roomNumber);

    IRoom findRoom(int roomNumber);

    IPrice getPrice();

    List<IRoom> getRoomList();

    void addRoom(IRoom aRoom);

    long getBookId();
}
