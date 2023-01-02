package kafka.producers;

import model.Reservation;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.json.JSONObject;


import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static kafka.topics.Topics.RESERVATION_TOPIC;

public class HotelProducer {

    private KafkaProducer<UUID, String> producer;

    public HotelProducer() throws ExecutionException, InterruptedException {
        Properties producerConfig = new Properties();

        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                UUIDSerializer.class.getName());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka1:9192,kafka2:9292,kafka3:9392");
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
//        producerConfig.put("schema.registry.url", "http://localhost:1111");
//        producerConfig.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        this.producer = new KafkaProducer<>(producerConfig);
    }

    public void send(Reservation reservation) throws ExecutionException, InterruptedException {
        ProducerRecord<UUID, String> record = new ProducerRecord<>(RESERVATION_TOPIC,
                reservation.getId().getUuid(), new JSONObject(reservation).toString());

        producer.send(record, this::onCompletion);

        producer.flush();
        producer.close();
    }

    private void onCompletion(RecordMetadata data, Exception exception) {
        if (exception == null) {
            System.out.println(data.offset());
        } else {
            System.out.println(exception);
        }
    }
}
