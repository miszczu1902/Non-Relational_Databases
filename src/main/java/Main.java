import kafka.consumers.HotelConsumer;
import kafka.producers.HotelProducer;
import kafka.topics.Topics;
import model.Client;
import model.Reservation;
import model.Room;
import mongo.UniqueIdMgd;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
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
        hotelConsumer.receiveReservation();
    }
}
