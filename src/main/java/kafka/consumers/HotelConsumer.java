package kafka.consumers;

import com.google.gson.Gson;
import kafka.topics.Topics;
import model.Reservation;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HotelConsumer {

    private KafkaConsumer<UUID, String> consumer;

    public HotelConsumer() {
        Properties consumerConfig = new Properties();

        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, Topics.CONSUMER_GROUP_NAME);
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");

        this.consumer = new KafkaConsumer<>(consumerConfig);
        this.consumer.subscribe(List.of(Topics.RESERVATION_TOPIC));
    }

    public List<Reservation> receiveReservation() {
        Map<Integer, Long> offsets = new HashMap<>();
        List<Reservation> reservations = new ArrayList<>();

        Duration timeout = Duration.of(100, ChronoUnit.MILLIS);
        MessageFormat formatter = new MessageFormat("Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");

        ConsumerRecords<UUID, String> records = consumer.poll(timeout);
        records.forEach(record -> {

            reservations.add(new Gson().fromJson(record.value(), Reservation.class));
//            offsets.put(record.partition(), record.offset());
        });

//        for (ConsumerRecord<UUID, String> record : records) {
//            String result = formatter.format(new Object[]{record.topic(), record.partition(), record.offset(), record.key(), record.value()});
//            System.out.println(result);
//            offsets.put(record.partition(), record.offset());
//        }
//
//        System.out.println(offsets);
        this.consumer.commitAsync();
        this.consumer.close();

        return reservations;
    }
}
