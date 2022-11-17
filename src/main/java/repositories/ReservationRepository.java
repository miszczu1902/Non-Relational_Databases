package repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lombok.AllArgsConstructor;
import model.Reservation;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.EncoderContext;
import org.bson.conversions.Bson;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class ReservationRepository extends AbstractMongoRepository implements Repository<Reservation> {

    private MongoCollection<Reservation> collection;

    public ReservationRepository() {
        super();
        this.collection = hotelDB.getCollection("reservations", Reservation.class);
    }

    @Override
    public Reservation get(Object element) {
        return Optional.ofNullable(collection.find(
                        Filters.eq("_id", ((Reservation) element).getId())).first())
                .orElseThrow();
    }

    @Override
    public void add(Reservation... elements) {
        Stream.of(elements).forEach(collection::insertOne);
    }

    @Override
    public void remove(Reservation... elements) {
        Stream.of(elements).forEach(element -> collection.deleteOne(
                Filters.eq("_id", element.getId())
        ));
    }

    @Override
    public void update(Reservation... elements) {
        Map<Bson, Bson> objectsToUpdate = new HashMap<>();
        Codec<Reservation> clientCodec = collection.getCodecRegistry().get(Reservation.class);
        Stream.of(elements).forEach(element -> {
            BsonDocument bsonDocument = new BsonDocument();
            clientCodec.encode(new BsonDocumentWriter(bsonDocument), element, EncoderContext.builder().build());
            objectsToUpdate.put(Filters.eq("_id", element.getId()),
                    Updates.set("reservation", bsonDocument));
        });
        objectsToUpdate.forEach((key, value) -> collection.updateOne(key, value));
    }

    @Override
    public List<Reservation> find(Object... elements) {
        return Optional.of(Arrays.stream(elements)
                .map(this::get)
                .collect(Collectors.toList())).orElseThrow();
    }

    @Override
    public List<Reservation> getAll() {
        return collection.find().into(new ArrayList<>());
    }
}
