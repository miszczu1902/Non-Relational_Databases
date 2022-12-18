package main;

import cassandra.CassandraNamespaces;
import com.datastax.oss.driver.api.core.CqlSession;
import exceptions.ClientException;
import exceptions.RoomException;
import managers.HotelManager;
import model.*;

import java.util.UUID;

public class Main {
    public static void main(String[] args) throws ClientException, RoomException {
        HotelManager hotelManager = new HotelManager();
        Client client = new Client("personalID1", "Jan", "Kowalski", UUID.randomUUID());

        hotelManager.addClientToHotel(client);
//        Room room = new Room(1, 2, 30.0, new Extended());

//        hotelManager.addClientToHotel(client);
//        hotelManager.addRoom(1, 2, 30.0, new Extended());
//        Client client1 = hotelManager.aboutClient("id1");
//        System.out.println(client1.toString());
    }
}
