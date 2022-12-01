package managers;

import exceptions.ClientException;
import exceptions.LogicException;
import exceptions.ReservationException;
import exceptions.RoomException;
import model.Client;
import model.EquipmentType;
import model.Reservation;
import model.Room;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HotelManagerTest extends BasicManagerTest {

    private static final HotelManager hotelManager = new HotelManager();

    @Test
    public void addClientTest() throws ClientException {
        Client client = randomClient();
        hotelManager.addClientToHotel(client.getPersonalID());

        assertEquals(client, hotelManager.aboutClient(client.getPersonalID()));
        assertThrows(ClientException.class, () -> hotelManager.aboutClient(randomString()));
        assertThrows(ClientException.class,
                () -> hotelManager.addClientToHotel(client.getPersonalID()));

    }

    @Test
    public void addRoomTest() throws RoomException {
        Room room = randomRoom();
        hotelManager.addRoom(room.getRoomNumber(), room.getCapacity(), room.getPrice(),
                room.getEquipmentType());

        assertEquals(room, hotelManager.aboutRoom(room.getRoomNumber()));
        assertThrows(RoomException.class, () -> hotelManager.aboutRoom(randomInt()));
        assertThrows(RoomException.class,
                () -> hotelManager.addRoom(room.getRoomNumber(), randomInt(), randomDouble(),
                        room.getEquipmentType()));
    }

    @Test
    public void addReservationTest() throws LogicException {
        Client client = randomClient();
        Room room = randomRoom();
        LocalDateTime beginTime = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime endTime = LocalDateTime.now().plusDays(2).truncatedTo(ChronoUnit.MINUTES);

        hotelManager.addClientToHotel(client.getPersonalID());
        hotelManager.addRoom(room.getRoomNumber(), room.getCapacity(), room.getPrice(),
                room.getEquipmentType());

        UUID reservationId = hotelManager.reserveRoom(room.getRoomNumber(),
                beginTime, endTime, client.getPersonalID());

        Reservation addedReservation = hotelManager.aboutReservation(reservationId);

        assertEquals(client, addedReservation.getClient());
        assertEquals(room, addedReservation.getRoom());
        assertEquals(beginTime, addedReservation.getBeginTime());
        assertEquals(endTime, addedReservation.getEndTime());

        assertThrows(ReservationException.class,
                () -> hotelManager.reserveRoom(room.getRoomNumber(),
                        LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2),
                        client.getPersonalID()));
        assertThrows(ReservationException.class,
                () -> hotelManager.reserveRoom(randomRoom().getRoomNumber(),
                        LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(2),
                        client.getPersonalID()));
        assertThrows(ReservationException.class,
                () -> hotelManager.reserveRoom(randomRoom().getRoomNumber(),
                        LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(2),
                        client.getPersonalID()));
        assertThrows(ReservationException.class,
                () -> hotelManager.reserveRoom(randomRoom().getRoomNumber(),
                        LocalDateTime.now().plusDays(10), LocalDateTime.now().minusDays(2),
                        client.getPersonalID()));

        assertDoesNotThrow(() -> hotelManager.reserveRoom(room.getRoomNumber(), endTime.plusDays(10),
                endTime.plusDays(15), client.getPersonalID()));
    }

    @Test
    public void testRemoveClient() throws ClientException {
        String personalId = randomString();
        hotelManager.addClientToHotel(personalId);

        hotelManager.removeClientFormHotel(personalId);
        assertThrows(ClientException.class, () -> hotelManager.aboutClient(personalId));
    }

    @Test
    public void testRemoveRoom() throws RoomException {
        Room room = randomRoom();
        int roomNumber = room.getRoomNumber();
        hotelManager.addRoom(roomNumber, room.getCapacity(), room.getPrice(),
                room.getEquipmentType());
        assertEquals(room, hotelManager.aboutRoom(roomNumber));

        hotelManager.removeRoom(roomNumber);

        assertThrows(RoomException.class, () -> hotelManager.aboutRoom(roomNumber));
    }

    @Test
    public void testUpdateRoomEquipment() throws RoomException {
        Room room = randomRoom();
        EquipmentType equipmentType = EquipmentType.EXTENDED;

        hotelManager.addRoom(room.getRoomNumber(), room.getCapacity(), room.getPrice(), EquipmentType.BASIC);
        hotelManager.updateRoomEquipment(room.getRoomNumber(), equipmentType);

        assertEquals(equipmentType, hotelManager.aboutRoom(room.getRoomNumber()).getEquipmentType());
    }
}