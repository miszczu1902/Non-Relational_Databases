package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

    private static List<ClientType> getClientTypes() {
        return Arrays.asList(ClientType.values());
    }

    @BeforeAll
    public static void initialize() {
        Address address = new Address(randomString(), randomString(), randomString());
        client = new Client(randomString(), randomString(), randomString(), address);
        room = new Room(randomInt(), randomInt(), randomDouble(), getEquipmentTypes().get(0));

        reservation = new Reservation();
    }

    @ParameterizedTest
    @MethodSource("randomUUIDs")
    void testId(UUID uuid) {
        reservation.setId(uuid);
        assertEquals(uuid, reservation.getId());
    }

    @ParameterizedTest
    @MethodSource("getClientTypes")
    void testCalculateReservationCost(ClientType clientType) {
        client.setClientType(clientType);
        reservation.setClient(client);
        reservation.setRoom(room);

        double expected = client.getClientType().getDiscount() *
                reservation.getRentDays() * reservation.getRoom().getPrice();
        reservation.calculateReservationCost();
        assertEquals(expected, reservation.getReservationCost());
    }

    @Test
    void testEquals() {
        reservation = new Reservation(room, LocalDateTime.now(),
                LocalDateTime.now().plusDays(RandomUtils.nextInt()), client);
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

    @Test
    void testRoom() {
        reservation.setRoom(room);
        assertEquals(room, reservation.getRoom());
    }

    @Test
    void testClient() {
        reservation.setClient(client);
        assertEquals(client, reservation.getClient());
    }
}
