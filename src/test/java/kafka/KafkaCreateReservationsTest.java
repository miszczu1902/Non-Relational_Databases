package kafka;

import exceptions.LogicException;
import kafka.producers.HotelProducer;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

public class KafkaCreateReservationsTest extends KafkaTests {

    protected static HotelProducer hotelProducer;

    @BeforeAll
    public static void prepareDataAndProducerToTest() {
        try {
            hotelProducer = new HotelProducer();
            addReservationsToHotel(1000);
        } catch (ExecutionException | InterruptedException e) {
            LOGGER.warn(e.getMessage());
            throw new RuntimeException(e);
        } catch (LogicException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    @Test
    public void sendReservationsToTopic() {
        RESERVATIONS.forEach(reservation -> {
            try {
                hotelProducer.send(reservation);
                LOGGER.info("Reservation added: " + new JSONObject(reservation));
                Thread.sleep(2500);
            } catch (ExecutionException | InterruptedException e) {
                LOGGER.warn(e.getMessage());
            }
        });
    }

    @AfterAll
    public static void closeProducer() {
        hotelProducer.close();
    }
}
