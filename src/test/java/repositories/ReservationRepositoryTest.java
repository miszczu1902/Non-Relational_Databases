package repositories;

import cassandra.CassandraConnector;
import com.datastax.oss.driver.api.core.CqlSession;
import model.*;
import model.equipmentType.Basic;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationRepositoryTest extends BasicModelTest {

    private static CqlSession session = CassandraConnector.initSession();
    private ReservationRepository reservationRepository = new ReservationRepository(session);
    private ClientRepository clientRepository = new ClientRepository(session);
    private RoomRepository roomRepository = new RoomRepository(session);
    private Client client;
    private Address address;
    private EquipmentType equipmentType;
    private Room room;
    private Reservation reservation;

    @Before
    public void initToTest() {
        address = new Address(UUID.randomUUID(), randomString(), randomString(), randomString());
        client = new Client(randomString(), randomString(), randomString(), address.getAddressId());
        equipmentType = new Basic();
        equipmentType.setEqId(UUID.randomUUID());
        room = new Room(randomInt(), randomInt(), randomDouble(), equipmentType.getEqId());
        reservation = new Reservation(UUID.randomUUID(), room.getRoomNumber(), LocalDate.now(), LocalDate.now().plusDays(7),
                client.getPersonalID(), 0);
        clientRepository.add(address, client);
        roomRepository.add(equipmentType, room);
    }

    @Test
    public void testAddAndGet() {
        reservationRepository.add(reservation);

        assertEquals(reservation, reservationRepository.get(reservation.getId()));
    }

    @Test
    public void testUpdate() {
        reservationRepository.add(reservation);

        Reservation newReservation = SerializationUtils.clone(reservation);
        newReservation.setEndTime(LocalDate.now().plusDays(2));

        reservationRepository.update(newReservation);
        assertEquals(newReservation, reservationRepository.get(newReservation.getId()));
    }

    @Test
    public void testRemove() {
        reservationRepository.add(reservation);
        reservationRepository.remove(reservation);

        assertThrows(NoSuchElementException.class, () -> reservationRepository.get(reservation.getId()));
    }

    @Test
    public void testGetAll() {
        int oldSize = reservationRepository.getAll().size();
        reservationRepository.add(reservation);

        assertEquals(oldSize + 1, reservationRepository.getAll().size());
    }

    @AfterAll
    public static void closeSession() {
        session.close();
    }
}
