package model;

import mongo.UniqueIdMgd;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.math3.util.Precision;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationTest extends BasicModelTest {

    private static Reservation reservation;
    private static Room room;
    private static Client client;

    private static List<ClientType> getClientTypes() {
        return Arrays.asList(ClientType.values());
    }

    @BeforeAll
    public static void initialize() {
        Address address = new Address(randomInt() ,randomString(), randomString(), randomString());
        client = new Client(randomString(), randomString(), randomString(), address, ClientType.STANDARD);
        room = new Room(randomInt(), randomInt(), randomDouble(), getEquipmentTypes().get(0));

        reservation = new Reservation();
    }

    @ParameterizedTest
    @MethodSource("getClientTypes")
    void testCalculateReservationCost(ClientType clientType) {
        client.setClientType(clientType);
        reservation.setClient(client);
        reservation.setRoom(room);

        double expected = Precision.round(client.getClientType().getDiscount() *
                reservation.getRentDays() * reservation.getRoom().getPrice(), 2);
        reservation.calculateReservationCost();
        assertEquals(expected, reservation.getReservationCost());
    }

    @Test
    void testEquals() {
        reservation = new Reservation(new UniqueIdMgd(), room, LocalDateTime.now(),
                LocalDateTime.now().plusDays(RandomUtils.nextInt()), client,randomInt());
        Reservation clonedReservation = SerializationUtils.clone(reservation);
        assertEquals(reservation, clonedReservation);


        clonedReservation.setEndTime(LocalDateTime.now());
        assertEquals(reservation, clonedReservation);
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
