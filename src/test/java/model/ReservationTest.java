package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ReservationTest extends BasicModelTest {

    private static Reservation reservation;
    private static Room room;
    private static Client client;

    @BeforeAll
    public static void initialize() {
        Address address = new Address(UUID.randomUUID(), randomString(), randomString(), randomString());
        client = new Client(randomString(), randomString(), randomString(), address.getId());
        room = new Room(randomInt(), randomInt(), randomDouble(), getEquipmentTypes().get(0));

        reservation = new Reservation();
    }

    @ParameterizedTest
    @MethodSource("randomUUIDs")
    void testId(UUID uuid) {
        reservation.setId(uuid);
        assertEquals(uuid, reservation.getId());
    }

    @Test
    void testEquals() {
        reservation = new Reservation(room.getRoomNumber(), LocalDateTime.now(),
                LocalDateTime.now().plusDays(RandomUtils.nextInt()), client.getPersonalID());
        Reservation clonedReservation = SerializationUtils.clone(reservation);
        assertEquals(reservation, clonedReservation);


        clonedReservation.setEndTime(LocalDateTime.now());
        assertEquals(reservation, clonedReservation);

        clonedReservation.setId(UUID.randomUUID());
        assertNotEquals(reservation, clonedReservation);
    }

    @Test
    void testRentDays() {
        LocalDateTime beginTime = LocalDateTime.now();
        LocalDateTime endTime = beginTime.plusDays(randomInt());
        reservation.setBeginTime(beginTime);
        reservation.setEndTime(endTime);

        assertEquals(endTime.getDayOfYear() - beginTime.getDayOfYear(),
                reservation.getRentDays());
    }
}
