package kafka.consumers;

import com.google.gson.Gson;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import kafka.topics.Topics;
import model.Reservation;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import repositories.ReservationRepository;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HotelConsumer {

    private KafkaConsumer<UUID, String> consumer;
    private Jsonb jsonb = JsonbBuilder.create();
    private ReservationRepository reservationRepository = new ReservationRepository();


    public HotelConsumer() {
        Properties consumerConfig = new Properties();

        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, Topics.CONSUMER_GROUP_NAME);
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");

        this.consumer = new KafkaConsumer<>(consumerConfig);
        this.consumer.subscribe(List.of(Topics.RESERVATION_TOPIC));
    }

    public void receiveReservations() {
        List<Reservation> reservations = new ArrayList<>();
        int noRecordsCount = 0;

        Duration timeout = Duration.of(1000, ChronoUnit.MILLIS);
        MessageFormat formatter = new MessageFormat(
                "Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");

        while (true) {
            ConsumerRecords<UUID, String> consumerRecords = consumer.poll(timeout);
            if (consumerRecords.count() == 0) {
                noRecordsCount++;
                if (noRecordsCount > timeout.getSeconds()) {
                    break;
                } else {
                    continue;
                }
            }

            consumerRecords.forEach(record -> {
                String result = formatter.format(new Object[]{record.topic(), record.partition(),
                        record.offset(), record.key(), record.value()});
                System.out.println(result);
                reservationRepository.add(new Gson().fromJson(record.value(), Reservation.class));
            });
            consumer.commitAsync();
        }
    }

    public void close() {
        this.consumer.close();
    }
}
