package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HotelFactory implements Serializable,IHotelFactory {

    @Override
    public IHotel create() throws FileNotFoundException {
        File configFile = new File("C:\\Users\\Robert\\Desktop\\HotelManagement\\src\\HotelConfig");
        Scanner input = new Scanner(configFile);
        List<IRoom> roomList = new ArrayList<>();
        System.out.println(input.nextLine());
        System.out.println(input.nextLine());
        while(input.hasNextInt()){
            int roomNumber = input.nextInt();
            int roomBeds = input.nextInt();
            roomList.add(new Room(roomNumber, roomBeds));
        }
        IPrice price = new Price();
        IHotel hotel = new Hotel(roomList,price);

        return hotel;
    }
}
