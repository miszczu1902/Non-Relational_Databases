package repositories;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import model.Room;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.EncoderContext;
import org.bson.conversions.Bson;

public class RoomRepository extends AbstractMongoRepository implements Repository<Room> {

    private MongoCollection<Room> collection;

    public RoomRepository() {
        super();
        this.collection = hotelDB.getCollection("rooms", Room.class);
    }

    @Override
    public Room get(Object element) {
        return Optional.ofNullable(collection.find(
                        Filters.eq("roomNumber", ((Room) element).getRoomNumber())).first())
                .orElseThrow();
    }

    @Override
    public void add(Room... elements) {
        Stream.of(elements).forEach(collection::insertOne);
    }

    @Override
    public void remove(Room... elements) {
        Stream.of(elements).forEach(element -> collection.deleteOne(
                Filters.eq("roomNumber", element.getRoomNumber())
        ));
    }

    @Override
    public void update(Room... elements) {
        Map<Bson, Bson> objectsToUpdate = new HashMap<>();
        Codec<Room> clientCodec = collection.getCodecRegistry().get(Room.class);
        Stream.of(elements).forEach(element -> {
            BsonDocument bsonDocument = new BsonDocument();
            clientCodec.encode(new BsonDocumentWriter(bsonDocument), element, EncoderContext.builder().build());
            objectsToUpdate.put(Filters.eq("roomNumber", element.getRoomNumber()),
                    Updates.set("room", bsonDocument));
        });
        objectsToUpdate.forEach((key, value) -> collection.updateOne(key, value));
    }

    @Override
    public List<Room> find(Object... elements) {
        return Optional.of(Arrays.stream(elements)
                .map(this::get)
                .collect(Collectors.toList())).orElseThrow();
    }

    @Override
    public List<Room> getAll() {
        return collection.find().into(new ArrayList<>());
    }
}
