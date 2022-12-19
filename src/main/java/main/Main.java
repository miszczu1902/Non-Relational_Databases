package main;

import cassandra.CassandraNamespaces;
import com.datastax.oss.driver.api.core.CqlSession;
import exceptions.ClientException;
import exceptions.LogicException;
import exceptions.RoomException;
import managers.HotelManager;
import model.*;
import model.equipmentType.Extended;

import java.time.LocalDate;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws LogicException {
//        HotelManager hotelManager = new HotelManager();
//        Client client = new Client("personalID1", "Jan", "Kowalski", UUID.randomUUID());
//        Client client2 = new Client("personalID2", "Jan", "Kowalski", UUID.randomUUID());
//        Room room = new Room(1, 20, 55.3, UUID.randomUUID());
//        EquipmentType extended = new Extended();
//        extended.setId(UUID.randomUUID());
//        hotelManager.addRoom(1, 20, 55.3, extended);
//        hotelManager.addClientToHotel(client);
//        System.out.println(hotelManager.aboutRoom(1));
//
//        UUID reservationId = hotelManager.reserveRoom(1, LocalDate.now(), LocalDate.now().plusDays(2), client.getPersonalID());
//        System.out.println(hotelManager.aboutReservation(reservationId));

//        System.out.println(hotelManager.aboutRoom(1));
//        hotelManager.addClientToHotel(client2);
//        System.out.println(hotelManager.aboutClient("personalID1").toString());

//        Room room = new Room(1, 2, 30.0, new Extended());

//        hotelManager.addClientToHotel(client);
//        hotelManager.addRoom(1, 2, 30.0, new Extended());
//        Client client1 = hotelManager.aboutClient("id1");
//        System.out.println(client1.toString());
    }
}
