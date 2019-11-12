package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room implements IRoom, Serializable {

    private int number, beds;
    private IBookingFactory bookingFactory;
    private List<IBooking> bookingList;
    private IBooking checkedInBooking;
    private IBooking checkedOutBooking;

    public Room(int number, int beds) {
        this.number = number;
        this.beds = beds;
        this.bookingFactory = new BookingFactory();
        this.checkedInBooking = null;
        this.checkedOutBooking = null;
        this.bookingList = new ArrayList<>();
    }

    @Override
    public void book(long bookingId, String customerName, String customerSurname, String customerPhoneNumber, LocalDate startingDate, LocalDate endingDate, ICost cost) {
        if(isFree(startingDate,endingDate))
        {
            IBooking newBooking = bookingFactory.create(bookingId, customerName,customerSurname,customerPhoneNumber,startingDate,endingDate,cost);
            bookingList.add(newBooking);
        }
    }

    @Override
    public void cancelBooking(long bookingId) {
        IBooking bookingToCancel = findBooking(bookingId);
        bookingList.remove(bookingToCancel);
    }

    @Override
    public IBooking findBooking(long bookingId) {
        for(IBooking booking : bookingList){
            if(booking.areYou(bookingId)){
                return booking;
            }
        }
        return null;
    }

    @Override
    public void checkIn(long bookingId) {
        checkedInBooking = findBooking(bookingId);
        bookingList.remove(checkedInBooking);
    }

    @Override
    public void checkOut() {
        checkedOutBooking = checkedInBooking;
        checkedInBooking = null;
        checkedOutBooking.checkOut(beds);
    }

    @Override
    public boolean isFree(LocalDate startingDate, LocalDate endingDate) {
        for(IBooking booking : bookingList) {
            if(booking.intersects(startingDate,endingDate)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean areYou(int roomNumber) {
        return roomNumber == number;
    }

    @Override
    public List<IBooking> findAllBooking(String customerSurname) {
        List<IBooking> customerBookingList = new ArrayList();
        for(IBooking booking : bookingList) {
            if(booking.isCustomer(customerSurname)) {
                customerBookingList.add(booking);
            }
        }
        return customerBookingList;
    }

    @Override
    public int getCost() {
        return checkedOutBooking.getCost();
    }

    @Override
    public int getRoomNumber() {
        return number;
    }

    @Override
    public int getBedsNumber() {
        return beds;
    }

    @Override
    public String getStatus() {
        if(checkedInBooking == null){
            return "Free";
        }
        else {
            return "Occupied";
        }
    }

    @Override
    public List<IBooking> getBookingList() {
        return bookingList;
    }

    @Override
    public IBooking getCheckedInBooking() {
        return checkedInBooking;
    }

    @Override
    public IBooking getCheckedOutBooking() {
        return checkedOutBooking;
    }

    @Override
    public IBooking getTodayBooking() {
        for(IBooking booking : bookingList){
            if(booking.startsToday()){
                return booking;
            }
        }
        return null;
    }

}
