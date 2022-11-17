package repositories;

import model.*;
import mongo.UniqueIdMgd;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationRepositoryTest extends BasicModelTest {

    private ReservationRepository reservationRepository = new ReservationRepository();
    private Reservation reservation;

    @Before
    public void prepareDataToTest() {
        reservation = new Reservation(new UniqueIdMgd(),
                new Room(randomInt(), randomInt(), randomDouble(), EquipmentType.BASIC),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2),
                new Client(randomString(), randomString(), randomString(),
                        new Address(randomInt(), randomString(), randomString(), randomString()),
                        ClientType.PREMIUM), randomDouble());

    }

    @Test
    public void testAdd() {
        reservationRepository.add(reservation);
        assertEquals(reservation, reservationRepository.get(reservation));
    }

    @Test
    public void testGet() {
        reservationRepository.add(reservation);
        assertEquals(reservation, reservationRepository.get(reservation));
    }

    @Test
    public void testUpdate() {
        reservationRepository.add(reservation);
        Reservation newReservation = reservation;
        newReservation.setEndTime(LocalDateTime.now().plusDays(2));
        reservationRepository.update(newReservation);
        assertEquals(newReservation, reservationRepository.get(newReservation));
    }

    @Test
    public void testRemove() {
        reservationRepository.add(reservation);
        reservationRepository.remove(reservation);
        assertThrows(NoSuchElementException.class, () -> reservationRepository.get(reservation));
    }

    @Test
    public void testGetAll() {
        int oldSize = reservationRepository.getAll().size();
        reservationRepository.add(reservation);
        assertEquals(oldSize + 1, reservationRepository.getAll().size());
    }
}
