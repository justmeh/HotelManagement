package controllers;

import javafx.scene.control.ListView;
import models.IRoom;

import java.time.LocalDate;
import java.util.List;

public class FindRoomsController {

    public ListView findRoomsList;

    public void setFindRooms(List<IRoom> roomList, LocalDate startingDate, LocalDate endingDate) {
        for(IRoom room : roomList){
            if(room.isFree(startingDate,endingDate)){
                findRoomsList.getItems().add( "          " +
                        room.getRoomNumber() + "                          " +
                        room.getBedsNumber()
                );
            }
        }
    }
}
