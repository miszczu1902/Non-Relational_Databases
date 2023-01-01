import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import kafka.producers.HotelProducer;
import model.Client;
import model.Reservation;
import model.Room;
import mongo.UniqueIdMgd;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, JsonMappingException {
        AvroMapper avroMapper = new AvroMapper();
        AvroSchema schema = avroMapper.schemaFor(Reservation.class);
        System.out.println(schema.getAvroSchema());

        HotelProducer hotelProducer = new HotelProducer();
        hotelProducer.send(new Reservation(
                new UniqueIdMgd(UUID.randomUUID()),
                new Room(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().plusDays(1).toString(),
                new Client(),
                0));

    }
}
