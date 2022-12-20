package managers;

import exceptions.ClientException;
import exceptions.LogicException;
import exceptions.ReservationException;
import exceptions.RoomException;
import model.Client;
import model.EquipmentType;
import model.Reservation;
import model.Room;
import model.equipmentType.Deluxe;
import model.equipmentType.Extended;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HotelManagerTest extends BasicManagerTest {

    private static HotelManager hotelManager = new HotelManager();

    @Test
    public void addClientTest() throws ClientException {
        Client client = randomClient();
        hotelManager.addClientToHotel(client);

        assertEquals(client, hotelManager.aboutClient(client.getPersonalID()));
        assertThrows(ClientException.class, () -> hotelManager.aboutClient(randomString()));
        assertThrows(ClientException.class,
                () -> hotelManager.addClientToHotel(client));

    }

    @Test
    public void addRoomTest() throws RoomException {
        int roomNumber = randomInt();
        EquipmentType equipmentType = new Deluxe();
        equipmentType.setId(UUID.randomUUID());

        hotelManager.addRoom(roomNumber, randomInt(), randomDouble(), equipmentType);
        Room room = hotelManager.aboutRoom(roomNumber);

        assertEquals(room, hotelManager.aboutRoom(room.getRoomNumber()));
        assertThrows(RoomException.class, () -> hotelManager.aboutRoom(randomInt()));
        assertThrows(RoomException.class,
                () -> hotelManager.addRoom(room.getRoomNumber(), randomInt(), randomDouble(), equipmentType));
    }

    @Test
    public void addReservationTest() throws LogicException {
        Client client = randomClient();
        int roomNumber = randomInt();
        EquipmentType equipmentType = new Deluxe();
        equipmentType.setId(UUID.randomUUID());
        LocalDate beginTime = LocalDate.now().plusDays(1);
        LocalDate endTime = LocalDate.now().plusDays(2);

        hotelManager.addClientToHotel(client);
        hotelManager.addRoom(roomNumber, randomInt(), randomDouble(), equipmentType);

        UUID reservationId = hotelManager.reserveRoom(roomNumber, beginTime, endTime, client.getPersonalID());
        Room room = hotelManager.aboutRoom(roomNumber);
        Reservation addedReservation = hotelManager.aboutReservation(reservationId);

        assertEquals(client.getPersonalID(), addedReservation.getClientId());
        assertEquals(room.getRoomNumber(), addedReservation.getRoomNumber());
        assertEquals(beginTime, addedReservation.getBeginTime());
        assertEquals(endTime, addedReservation.getEndTime());

        assertThrows(ReservationException.class,
                () -> hotelManager.reserveRoom(roomNumber,
                        LocalDate.now().plusDays(1), LocalDate.now().plusDays(2),
                        client.getPersonalID()));
        assertThrows(ReservationException.class,
                () -> hotelManager.reserveRoom(roomNumber,
                        LocalDate.now().plusDays(3), LocalDate.now().plusDays(2),
                        client.getPersonalID()));
        assertThrows(ReservationException.class,
                () -> hotelManager.reserveRoom(roomNumber,
                        LocalDate.now().minusDays(10), LocalDate.now().plusDays(2),
                        client.getPersonalID()));
        assertThrows(ReservationException.class,
                () -> hotelManager.reserveRoom(roomNumber,
                        LocalDate.now().plusDays(10), LocalDate.now().minusDays(2),
                        client.getPersonalID()));

        assertDoesNotThrow(() -> hotelManager.reserveRoom(room.getRoomNumber(), endTime.plusDays(10),
                endTime.plusDays(15), client.getPersonalID()));
    }

    @Test
    public void testRemoveClient() throws ClientException {
        Client client = randomClient();
        hotelManager.addClientToHotel(client);

        hotelManager.removeClientFormHotel(client.getPersonalID());
        assertThrows(ClientException.class, () -> hotelManager.aboutClient(client.getPersonalID()));
    }

    @Test
    public void testRemoveRoom() throws RoomException {
        int roomNumber = randomInt();
        EquipmentType equipmentType = new Deluxe();
        equipmentType.setId(UUID.randomUUID());

        hotelManager.addRoom(roomNumber, randomInt(), randomDouble(), equipmentType);
        Room room = hotelManager.aboutRoom(roomNumber);

        assertEquals(room, hotelManager.aboutRoom(roomNumber));

        hotelManager.removeRoom(roomNumber);

        assertThrows(RoomException.class, () -> hotelManager.aboutRoom(roomNumber));
    }

    @Test
    public void testUpdateRoomEquipment() throws RoomException {
        int roomNumber = randomInt();
        EquipmentType equipmentType = new Deluxe();
        equipmentType.setId(UUID.randomUUID());

        hotelManager.addRoom(roomNumber, randomInt(), randomDouble(), equipmentType);
        EquipmentType updatedEquipmentType = new Extended();
        updatedEquipmentType.setId(UUID.randomUUID());

        hotelManager.updateRoomEquipment(roomNumber, updatedEquipmentType);

        assertEquals(updatedEquipmentType.getId(), hotelManager.aboutRoom(roomNumber).getEquipmentTypeId());
    }

    @AfterAll
    public static void endSession() {
        HotelManager.closeSession();
    }
}