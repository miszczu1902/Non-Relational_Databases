package managers;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.ClientException;
import exceptions.LogicException;
import exceptions.ReservationException;
import exceptions.RoomException;

import java.time.LocalDateTime;
import java.util.UUID;

import model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;

public class HotelManagerTest extends BasicManagerTest {

    private final HotelManager hotelManager = new HotelManager();

    @Test
    public void addClientTest() throws ClientException {
        Client client = randomClient();
        hotelManager.addClientToHotel(client.getPersonalID(), client.getFirstName(),
                client.getLastName(), randomClient().getAddress());

        assertEquals(client, hotelManager.aboutClient(client.getPersonalID()));
        assertThrows(ClientException.class, () -> hotelManager.aboutClient(randomString()));
        assertThrows(ClientException.class,
                () -> hotelManager.addClientToHotel(client.getPersonalID(), randomString(),
                        randomString(), client.getAddress()));

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
        LocalDateTime beginTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = LocalDateTime.now().plusDays(2);

        hotelManager.addClientToHotel(client.getPersonalID(), client.getFirstName(),
                client.getLastName(), client.getAddress());
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
        hotelManager.addClientToHotel(personalId, randomString(), randomString(),
                randomClient().getAddress());

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
        EquipmentType equipmentType = getEquipmentTypes().get(0);

        hotelManager.addRoom(room.getRoomNumber(), room.getCapacity(), room.getPrice(),
                room.getEquipmentType());
        hotelManager.updateRoomEquipment(room.getRoomNumber(), equipmentType);

        assertEquals(equipmentType, hotelManager.aboutRoom(room.getRoomNumber()).getEquipmentType());
    }

    @Test
    public void lockTest() throws LogicException, InterruptedException {
        String clientID = RandomStringUtils.randomNumeric(11);
        String clientID2 = RandomStringUtils.randomNumeric(11);
        int roomNumber = RandomUtils.nextInt();

        hotelManager.addClientToHotel(clientID, RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                new Address(RandomStringUtils.randomAlphanumeric(10),
                        RandomStringUtils.randomAlphanumeric(10),
                        RandomStringUtils.randomAlphanumeric(10)));
        hotelManager.addClientToHotel(clientID2, RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                new Address(RandomStringUtils.randomAlphanumeric(10),
                        RandomStringUtils.randomAlphanumeric(10),
                        RandomStringUtils.randomAlphanumeric(10)));

        hotelManager.addRoom(roomNumber, RandomUtils.nextInt(1, 10),
                RandomUtils.nextDouble(1, 100), new Deluxe(RandomStringUtils.randomAlphanumeric(25)));

        LocalDateTime beginTime = LocalDateTime.now().plusDays(5);
        LocalDateTime endTime = LocalDateTime.now().plusDays(14);

        reserveRoom(clientID, roomNumber, beginTime, endTime);
        assertThrows(ReservationException.class,
                () -> hotelManager.reserveRoom(roomNumber, beginTime, endTime, clientID2));
        HotelManager.getEntityManager().clear();
        HotelManager.getEntityManager().close();


    }

    private void reserveRoom(String personalID, int roomNumber, LocalDateTime beginTime, LocalDateTime endTime)
            throws LogicException, InterruptedException {
        hotelManager.reserveRoom(roomNumber, beginTime, endTime, personalID);
        Thread.sleep(1000);
    }

    @AfterClass
    public static void afterTests() {
        HotelManager.getEntityManager().clear();
        HotelManager.getEntityManager().close();
    }
}
