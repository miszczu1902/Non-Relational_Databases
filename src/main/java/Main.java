import kafka.consumers.HotelConsumer;
import kafka.producers.HotelProducer;
import kafka.topics.Topics;
import lombok.extern.slf4j.Slf4j;
import model.Client;
import model.Reservation;
import model.Room;
import mongo.UniqueIdMgd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Logger LOGGER = LoggerFactory.getLogger(Slf4j.class);
        Topics.createTopic();

        HotelProducer hotelProducer = new HotelProducer();
        HotelConsumer hotelConsumer = new HotelConsumer();

        hotelProducer.send(new Reservation(
                new UniqueIdMgd(UUID.randomUUID()),
                new Room(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().plusDays(1).toString(),
                new Client(),
                0));
        hotelConsumer.receiveReservation().forEach(reservation -> {
            System.out.println(reservation.toString());
                    LOGGER.info(reservation.toString());
                }
        );
    }
}
