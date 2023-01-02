package kafka.producers;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import model.Reservation;
import org.apache.commons.math3.util.Pair;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.json.JSONObject;


import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class HotelProducer {

    public static final Pair<String, String> CLIENT_TOPIC = new Pair<>("reservations", "ZZpdX2DbSfW5T3HYNSeEdQ");
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
//        producerConfig.put("schema.registry.url", "http://localhost:8081");
//        producerConfig.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        this.producer = new KafkaProducer<>(producerConfig);
    }

    public void send(Reservation reservation) throws ExecutionException, InterruptedException {
        JSONObject jsonObject = new JSONObject(reservation);
        System.out.println(jsonObject);
        ProducerRecord<UUID, String> record = new ProducerRecord<>(CLIENT_TOPIC.getKey(),
                reservation.getId().getUuid(), jsonObject.toString());

        producer.send(record);

        producer.flush();
        producer.close();

    }
}
