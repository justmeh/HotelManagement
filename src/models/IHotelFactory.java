package models;

import java.io.FileNotFoundException;

public interface IHotelFactory {

    IHotel create() throws FileNotFoundException;
}
