package serializer;

import models.IHotel;

import java.io.*;

public class HotelSerializer implements Serializable {

    public void serialize(IHotel aHotel){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("hotel.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(aHotel);
            out.close();
            fileOut.close();
            System.out.printf("Hotel is serialized hotel.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public IHotel deserialize(){
        IHotel hotel = null;

        try {
            FileInputStream fileIn = new FileInputStream("hotel.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            hotel = (IHotel) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Hotel class not found");
            c.printStackTrace();
            return null;
        }

        return hotel;
    }
}
