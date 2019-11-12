package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.*;
import serializer.HotelSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController {

    private IHotel hotel = new Hotel();
    private HotelSerializer hotelSerializer = new HotelSerializer();
    private InputVerifier inputVerifier = new InputVerifier();

    public void initialize(){
        File serFile = new File("hotel.ser");
        if(serFile.exists() && !serFile.isDirectory()) {
            hotel = hotelSerializer.deserialize();
        }
        if(!(hotel.getRoomList() == null || hotel.getPrice() == null)) {
            allRoomsInRoomList();
            allBookingInCancelBookingList();
            allBookingInCheckInBookingList();
            allBookingInCheckOutBookingList();
            updateSeasonalPricesView();
        }
    }

    private int getRoomNumberFromSelection(ListView aListView) {
        String roomInfo = (String)aListView.getSelectionModel().getSelectedItem();
        Matcher matcher = Pattern.compile("\\d+").matcher(roomInfo);
        matcher.find();
        return Integer.valueOf(matcher.group());
    }

    private long getBookingIdFromSelection(ListView aListView) {
        String bookingInfo = (String) aListView.getSelectionModel().getSelectedItem();
        Matcher matcher = Pattern.compile("\\d+").matcher(bookingInfo);
        matcher.find();
        return Integer.valueOf(matcher.group());
    }

    private int getRoomNumberFromBookingSelection(ListView aListView) {
        String bookingInfo = (String) aListView.getSelectionModel().getSelectedItem();
        Matcher matcher = Pattern.compile("\\d+").matcher(bookingInfo);
        matcher.find();
        long bookingId = Integer.valueOf(matcher.group());

        matcher.find();
        return Integer.valueOf(matcher.group());
    }

    private void bookingsToListViewer(int roomNumber, List<IBooking> aBookingList, ListView aListView){
        if(aBookingList.isEmpty()){
            return;
        }
        for(IBooking booking : aBookingList){
            ICustomer currentCustomer = booking.getCustomer();
            aListView.getItems().add( "" +
                    booking.getId() + "     " +
                    roomNumber + "     " +
                    booking.getStartingDate() + "     " +
                    booking.getEndingDate() + "     " +
                    currentCustomer.getCustomerName() + "     " +
                    currentCustomer.getCustomerSurname() + "     " +
                    currentCustomer.getCustomerPhoneNumber ()
            );
        }
    }

    private boolean exists(Object aObject){
        return aObject != null;
    }

    //Rooms tab
    public ListView roomListView;
    public TextField searchRoomText;
    public Button searchRoomButton;
    public Button showRoomButton;

    public void showRoomInfo(ActionEvent actionEvent){
        if(!roomListView.getSelectionModel().getSelectedItems().isEmpty()) {
            Pane root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/RoomView.fxml"));
                root = loader.load();
                RoomController roomController = (RoomController) loader.getController();
                roomController.setRoomInfo(hotel.findRoom(getRoomNumberFromSelection(roomListView)));
                Stage stage = new Stage();
                stage.setTitle("Room info");
                stage.setScene(new Scene(root, 500, 565));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void searchRoom(ActionEvent actionEvent) {
        roomListView.getItems().clear();
        if(searchRoomText.getText().isEmpty()){
            allRoomsInRoomList();
        }
        else{
            if(inputVerifier.onlyDigits(searchRoomText.getText())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid input");
                alert.setContentText("Room number can only contain digits");
                alert.show();
                return;
            }
            searchRoomInRoomList();
        }
    }

    private void allRoomsInRoomList() {
        for (IRoom room : hotel.getRoomList()) {
            roomListView.getItems().add("            " +
                    room.getRoomNumber() + "                                            " +
                    room.getBedsNumber() + "                                                  " +
                    room.getStatus());
        }
    }

    private void searchRoomInRoomList() {
        IRoom room = hotel.findRoom(Integer.parseInt(searchRoomText.getText()));
        roomListView.getItems().add("            " +
                room.getRoomNumber() + "                                            " +
                room.getBedsNumber() + "                                                  " +
                room.getStatus());
    }

    //Book tab
    public DatePicker bookingStartDate;
    public DatePicker bookingEndDate;
    public TextField bookingRoomNumber;
    public TextField bookingName;
    public TextField bookingSurname;
    public TextField bookingPhoneNumber;
    public Button bookButton;
    public Button findRoomForBookingBtn;

    public void findRooms(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Missing or invalid input");
        if(verifyDates(alert)){
            Pane root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/FindRoomsView.fxml"));
                root = loader.load();
                FindRoomsController findRoomsController = (FindRoomsController)loader.getController();
                findRoomsController.setFindRooms(hotel.getRoomList(),bookingStartDate.getValue(),bookingEndDate.getValue());
                Stage stage = new Stage();
                stage.setTitle("Room info");
                stage.setScene(new Scene(root, 216, 326));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void bookNow(ActionEvent actionEvent){
        if(verifyBookingInput()) {
            int roomNumber = Integer.parseInt(bookingRoomNumber.getText());
            LocalDate startingDate = bookingStartDate.getValue();
            LocalDate endingDate = bookingEndDate.getValue();
            hotel.book(roomNumber, bookingName.getText(), bookingSurname.getText(), bookingPhoneNumber.getText(), startingDate, endingDate);
            hotelSerializer.serialize(hotel);
        }
    }

    private boolean verifyBookingInput() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Missing or invalid input");
        if(bookingRoomNumber.getText().isEmpty()){
            alert.setContentText("Please enter a room number");
            alert.show();
            return false;
        }
        else {
            if(inputVerifier.onlyDigits(bookingRoomNumber.getText()))
            {
                alert.setContentText("Room number can only contain digits");
                alert.show();
                return false;
            }
        }
        if(bookingName.getText().isEmpty()){
            alert.setContentText("Please enter a name");
            alert.show();
            return false;
        }
        else {
            if(inputVerifier.onlyLetters(bookingName.getText())){
                alert.setContentText("Name can only contain letters");
                alert.show();
                return false;
            }
        }
        if(bookingSurname.getText().isEmpty()){
            alert.setContentText("Please enter a surname");
            alert.show();
            return false;
        }
        else {
            if(inputVerifier.onlyLetters(bookingSurname.getText())){
                alert.setContentText("Surname can only contain letters");
                alert.show();
                return false;
            }
        }
        if(bookingPhoneNumber.getText().isEmpty()){
            alert.setContentText("Please enter a phone number");
            alert.show();
            return false;
        }
        else {
            if(inputVerifier.onlyDigits(bookingPhoneNumber.getText()))
            {
                alert.setContentText("Phone number can only contain digits");
                alert.show();
                return false;
            }
        }
        verifyDates(alert);
        return true;
    }

    private boolean verifyDates(Alert aAlert){
        if(bookingStartDate.getValue() == null || bookingEndDate.getValue() == null){
            aAlert.setContentText("Dates cannot be empty");
            aAlert.show();
            return false;
        }
        else {
            if(bookingEndDate.getValue().isBefore(bookingStartDate.getValue())){
                aAlert.setContentText("Starting date cannot be after ending date");
                aAlert.show();
                return false;
            }
        }
        return true;
    }


    //Check-in tab
    public ListView checkInBookingListView;
    public Button checkInBookingSearchBtn;
    public TextField checkInCustomerSurname;
    public Button checkInBtn;

    public void searchCheckInBooking(ActionEvent actionEvent){
        if(checkInCustomerSurname.getText().isEmpty()){
            allBookingInCheckInBookingList();
        }
        else {
            if(inputVerifier.onlyLetters(checkInCustomerSurname.getText())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid input");
                alert.setContentText("Surname can only contain letters");
                alert.show();
                return;
            }
            searchBookingInCheckInBookingList();
        }
    }

    private void allBookingInCheckInBookingList() {
        checkInBookingListView.getItems().clear();
        for(IRoom room : hotel.getRoomList()){
            List<IBooking> listToView = new ArrayList();
            if(exists(room.getTodayBooking())){
                listToView.add(room.getTodayBooking());
            }
            bookingsToListViewer(room.getRoomNumber(),listToView,checkInBookingListView);
        }
    }

    private void searchBookingInCheckInBookingList() {
        checkInBookingListView.getItems().clear();
        for(IRoom room : hotel.getRoomList()) {
            List<IBooking> listToView = new ArrayList();
            if (room.getTodayBooking().isCustomer(checkInCustomerSurname.getText()) && exists(room.getTodayBooking())) {
                listToView.add(room.getTodayBooking());
            }
            bookingsToListViewer(room.getRoomNumber(), listToView, checkInBookingListView);
        }
    }

    public void checkInBooking(ActionEvent actionEvent){
        long bookingId = getBookingIdFromSelection(checkInBookingListView);
        int roomNumber = getRoomNumberFromBookingSelection(checkInBookingListView);
        hotel.checkIn(roomNumber,bookingId);
        hotelSerializer.serialize(hotel);
        allBookingInCheckOutBookingList();
    }

    //Check-out tab
    public ListView checkOutBookingListView;
    public Button checkOutBookingSearchBtn;
    public TextField checkOutCustomerSurname;
    public Button checkOutBtn;

    public void searchCheckOutBooking(ActionEvent actionEvent){
        if(checkOutCustomerSurname.getText().isEmpty()){
            allBookingInCheckOutBookingList();
        }
        else {
            if(inputVerifier.onlyLetters(checkOutCustomerSurname.getText())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid input");
                alert.setContentText("Surname can only contain letters");
                alert.show();
                return;
            }
            searchBookingInCheckOutBookinglist();
        }
    }

    private void searchBookingInCheckOutBookinglist() {
        checkOutBookingListView.getItems().clear();
        for(IRoom room : hotel.getRoomList()){
            List<IBooking> listToView = new ArrayList();
            if(room.getCheckedInBooking().isCustomer(checkOutCustomerSurname.getText()) && exists(room.getCheckedInBooking())){
                listToView.add(room.getCheckedInBooking());
            }
            bookingsToListViewer(room.getRoomNumber(),listToView,checkOutBookingListView);
        }
    }

    private void allBookingInCheckOutBookingList() {
        checkOutBookingListView.getItems().clear();
        for(IRoom room : hotel.getRoomList()){
            List<IBooking> listToView = new ArrayList();
            if(exists(room.getCheckedInBooking()))
            {
                listToView.add(room.getCheckedInBooking());
            }
            bookingsToListViewer(room.getRoomNumber(),listToView,checkOutBookingListView);
        }

    }

    public void checkOutBooking(ActionEvent actionEvent){
        int roomNumber = getRoomNumberFromBookingSelection(checkOutBookingListView);
        IRoom room = hotel.findRoom(roomNumber);
        hotel.checkOut(roomNumber);
        Pane root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/CheckOutView.fxml"));
            root = loader.load();
            CheckOutController checkOutController = (CheckOutController)loader.getController();
            checkOutController.setBooking(roomNumber, room.getCheckedOutBooking());
            Stage stage = new Stage();
            stage.setTitle("Room info");
            stage.setScene(new Scene(root, 500, 565));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        allBookingInCheckOutBookingList();
        hotelSerializer.serialize(hotel);
    }

    //Cancel tab
    public Button cancelBookingSearchBtn;
    public TextField cancelBookingSurname;
    public ListView cancelBookingListView;
    public Button cancelBookingBtn;
    public Button editBookingBtn;

    public void searchCancelBooking(ActionEvent actionEvent){
        if(cancelBookingSurname.getText().isEmpty()){
            allBookingInCancelBookingList();
        }
        else{
            if(inputVerifier.onlyLetters(cancelBookingSurname.getText())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid input");
                alert.setContentText("Surname can only contain letters");
                alert.show();
                return;
            }
            searchBookingInCancelBookingList();
        }
    }

    private void allBookingInCancelBookingList(){
        cancelBookingListView.getItems().clear();
        for(IRoom room : hotel.getRoomList()){
            List<IBooking> listToView = room.getBookingList();
            bookingsToListViewer(room.getRoomNumber(),listToView, cancelBookingListView);
        }

    }

    private void searchBookingInCancelBookingList() {
        cancelBookingListView.getItems().clear();
        for(IRoom room : hotel.getRoomList()){
            List<IBooking> listToView = room.findAllBooking(cancelBookingSurname.getText());
            bookingsToListViewer(room.getRoomNumber(),listToView, cancelBookingListView);
        }
    }

    public void cancelBooking(ActionEvent actionEvent){
        String bookingInfo = (String) cancelBookingListView.getSelectionModel().getSelectedItem();
        Matcher matcher = Pattern.compile("\\d+").matcher(bookingInfo);
        matcher.find();
        long bookingId = Integer.valueOf(matcher.group());

        matcher.find();
        int roomNumber = Integer.valueOf(matcher.group());

        hotel.cancelBooking(roomNumber,bookingId);
        hotelSerializer.serialize(hotel);

        allBookingInCancelBookingList();
    }

    //Config tab
    public Button priceConfigButton;
    public Pane pricePane;
    public Button hotelConfigButton;
    public Pane hotelPane;

    public void priceConfigPane(ActionEvent actionEvent){
        pricePane.setVisible(true);
        hotelPane.setVisible(false);
    }

    public void hotelConfigPane(ActionEvent actionEvent){
        pricePane.setVisible(false);
        hotelPane.setVisible(true);
    }

    //Hotel
    public TextField addRoomNumber;
    public TextField addRoomBeds;
    public Button addRoomButton;
    public Button importButton;

    public void addRoom(ActionEvent actionEvent){
        IRoom newRoom = new Room(Integer.parseInt(addRoomNumber.getText()), Integer.parseInt(addRoomBeds.getText()));
        hotel.addRoom(newRoom);
        hotelSerializer.serialize(hotel);
    }

    public void importFromConfig(ActionEvent actionEvent){
        IHotelFactory hotelFactory = new HotelFactory();
        try {
            hotel = hotelFactory.create();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        hotelSerializer.serialize(hotel);
    }

    //Price
    public TextField summerPrice;
    public TextField springPrice;
    public TextField fallPrice;
    public TextField winterPrice;
    public TextField discountPeriodPrice;
    public DatePicker discountPeriodStart;
    public DatePicker discountPeriodEnd;
    public Button saveSeasonPricesChangesBtn;
    public Button addDiscountPeriodBtn;

    public void saveSeasonalChanges(ActionEvent actionEvent){
        int summerPriceToSet = Integer.parseInt(summerPrice.getText());
        int springPriceToSet = Integer.parseInt(springPrice.getText());
        int fallPriceToSet = Integer.parseInt(fallPrice.getText());
        int winterPriceToSet = Integer.parseInt(winterPrice.getText());

        IPrice price = hotel.getPrice();

        price.setSummerPrice(summerPriceToSet);
        price.setSpringPrice(springPriceToSet);
        price.setFallPrice(fallPriceToSet);
        price.setWinterPrice(winterPriceToSet);

        updateSeasonalPricesView();

        hotelSerializer.serialize(hotel);
    }

    private void updateSeasonalPricesView(){
        IPrice price = hotel.getPrice();

        String summerPriceToView = Integer.toString(price.getSummerPrice());
        String springPriceToView = Integer.toString(price.getSpringPrice());
        String fallPriceToView = Integer.toString(price.getFallPrice());
        String winterPriceToView = Integer.toString(price.getWinterPrice());

        summerPrice.setText(summerPriceToView);
        springPrice.setText(springPriceToView);
        fallPrice.setText(fallPriceToView);
        winterPrice.setText(winterPriceToView);
    }

    public void addDiscountPeriod(ActionEvent actionEvent){
        LocalDate discountStartingDate = discountPeriodStart.getValue();
        LocalDate discountEndingDate = discountPeriodEnd.getValue();
        int discountPricePerNight = Integer.parseInt(discountPeriodPrice.getText());

        IPrice price = hotel.getPrice();

        price.createDiscountInterval(discountStartingDate,discountEndingDate,discountPricePerNight);

        hotelSerializer.serialize(hotel);
    }

    public Button serializeBtn;

    public void serializeHotel(ActionEvent actionEvent){
        hotelSerializer.serialize(hotel);
    }

}
