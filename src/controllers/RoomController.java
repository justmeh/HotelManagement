package controllers;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import models.*;

import java.util.List;

public class RoomController {

    public ListView roomBookingListView;
    public Label showRoomNumber;
    public Label showRoomBeds;
    public Label showRoomStatus;

    public void setRoomInfo(IRoom roomToShow) {
        showRoomNumber.setText(Integer.toString(roomToShow.getRoomNumber()));
        showRoomBeds.setText(Integer.toString(roomToShow.getBedsNumber()));
        showRoomStatus.setText(roomToShow.getStatus());
        fillBookingList(roomToShow.getBookingList());
    }

    private void fillBookingList(List<IBooking> bookingList) {
        roomBookingListView.getItems().clear();
        for(IBooking booking : bookingList){
            ICustomer currentCustomer = booking.getCustomer();
            roomBookingListView.getItems().add( "" +
                    booking.getStartingDate() + "     " +
                    booking.getEndingDate() + "     " +
                    currentCustomer.getCustomerName() + "     " +
                    currentCustomer.getCustomerSurname() + "     " +
                    currentCustomer.getCustomerPhoneNumber ()
            );
        }
    }
}
