package kafka;

import exceptions.LogicException;
import kafka.consumers.HotelConsumer;
import model.Reservation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KafkaCreateConsumerTest extends KafkaTests {

    protected static HotelConsumer hotelConsumer;
    protected List<Reservation> reservations = new ArrayList<>();

    @BeforeAll
    public static void prepareConsumerToTest() {
        hotelConsumer = new HotelConsumer();
    }

    @Test
    public void getReservationFromProducer() {
        while (true) {
            reservations.addAll(hotelConsumer.receiveReservations());
        }
    }

    @Test
    public void getReservationFromProducerAndSaveToDataBase() {
        long oldSize = RESERVATION_REPOSITORY.getAll().size();
        Reservation[] reservationList = hotelConsumer.receiveReservations().toArray(new Reservation[0]);
        long listSize = reservationList.length;

        RESERVATION_REPOSITORY.add(reservationList);

        assertEquals(RESERVATION_REPOSITORY.getAll().size(), oldSize + listSize);
    }

    @AfterAll
    public static void closeConsumer() {
        hotelConsumer.close();
    }
}
