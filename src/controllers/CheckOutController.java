package controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.IBooking;

import javafx.event.ActionEvent;

public class CheckOutController {

    private IBooking booking;

    public TextField extraCostField;
    public Button checkOutBtn;
    public Label roomNumberLabel;
    public Label customerNameLabel;
    public Label customerSurnameLabel;
    public Label customerNumberLabel;
    public Label startingDateLabel;
    public Label endingDateLabel;
    public Label stayCostLabel;
    public Label totalCostLabel;


    public void setBooking(int roomNumber,IBooking bookingToCheckOut) {
        booking = bookingToCheckOut;
        setBookingInfo(roomNumber);
    }

    private void setBookingInfo(int roomNumber) {
        roomNumberLabel.setText(Integer.toString(roomNumber));
        customerNameLabel.setText(booking.getCustomer().getCustomerName());
        customerSurnameLabel.setText(booking.getCustomer().getCustomerSurname());
        customerNumberLabel.setText(booking.getCustomer().getCustomerPhoneNumber());
        startingDateLabel.setText(booking.getStartingDate().toString());
        endingDateLabel.setText(booking.getEndingDate().toString());
        stayCostLabel.setText(Integer.toString(booking.getCost()));
    }

    public void updateTotalCost(ActionEvent actionEvent) {
        int stayCost = booking.getCost();
        int inputCost = Integer.parseInt(extraCostField.getText());
        totalCostLabel.setText(Integer.toString(stayCost + inputCost));
    }
}
