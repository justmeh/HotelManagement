package models;
import serializer.HotelSerializer;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel implements IHotel, Serializable {

    private List<IRoom> roomList;
    private IPrice price;
    private long bookIdIdentitySimulator;
    private HotelSerializer hotelSerializer = new HotelSerializer();

    public Hotel() {

    }

    public Hotel(List<IRoom> roomList, IPrice price) {
        this.roomList = roomList;
        this.price = price;
        this.bookIdIdentitySimulator = 0;
    }

    @Override
    public void book(int roomNumber, String customerName, String customerSurname, String customerPhoneNumber, LocalDate startingDate, LocalDate endingDate) {
        IRoom room = findRoom(roomNumber);
        ICost cost = price.generateCost(startingDate, endingDate);
        room.book(bookIdIdentitySimulator,customerName,customerSurname,customerPhoneNumber,startingDate,endingDate, cost);
        bookIdIdentitySimulator++;
    }

    @Override
    public void cancelBooking(int roomNumber, long bookingId) {
        IRoom room = findRoom(roomNumber);
        room.cancelBooking(bookingId);
    }

    @Override
    public void checkIn(int roomNumber, long bookingId) {
        IRoom room = findRoom(roomNumber);
        room.checkIn(bookingId);
    }

    @Override
    public void checkOut(int roomNumber) {
        IRoom room = findRoom(roomNumber);
        room.checkOut();
    }

    @Override
    public List<IRoom> findFreeRooms(LocalDate startingDate, LocalDate endingDate) {
        List<IRoom> freeRoomsList = new ArrayList();
        for (IRoom room : roomList){
            if(room.isFree(startingDate,endingDate)){
                freeRoomsList.add(room);
            }
        }
        return freeRoomsList;
    }

    @Override
    public int getCost(int roomNumber) {
        IRoom room = findRoom(roomNumber);
        return room.getCost();
    }

    @Override
    public IRoom findRoom(int roomNumber) {
        for (IRoom room : roomList)
        {
            if(room.areYou(roomNumber))
                return room;
        }
        return null;
    }

    @Override
    public IPrice getPrice() {
        return price;
    }

    @Override
    public List<IRoom> getRoomList() {
        return roomList;
    }

    @Override
    public void addRoom(IRoom aRoom) {
        roomList.add(aRoom);
    }

    @Override
    public long getBookId() {
        return bookIdIdentitySimulator;
    }

}
