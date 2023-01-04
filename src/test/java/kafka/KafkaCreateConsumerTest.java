package kafka;

import kafka.consumers.HotelConsumer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class KafkaCreateConsumerTest extends KafkaTests {

    protected static HotelConsumer hotelConsumer;

    @BeforeAll
    public static void prepareConsumerToTest() throws InterruptedException {
        hotelConsumer = new HotelConsumer();
        Thread.sleep(5000);
    }

    @Test
    public void getReservationFromProducer() throws InterruptedException {
        while (true) {
            hotelConsumer.receiveReservations();
        }
    }

    @AfterAll
    public static void closeConsumer() {
        hotelConsumer.close();
    }
}
