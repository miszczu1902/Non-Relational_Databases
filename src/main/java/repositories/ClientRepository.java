package repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import model.Client;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.BsonNull;
import org.bson.codecs.Codec;
import org.bson.codecs.EncoderContext;
import org.bson.conversions.Bson;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientRepository extends AbstractMongoRepository implements Repository<Client> {

    private MongoCollection<Client> collection;

    public ClientRepository() {
        super();
        this.collection = hotelDB.getCollection("clients", Client.class);
    }

    @Override
    public Client get(Object element) {
        return collection.find(Filters.eq("personalID", (
                (Client) element).getPersonalID())).first();
    }

    @Override
    public void add(Client... elements) {
        Stream.of(elements).forEach(collection::insertOne);
    }

    @Override
    public void remove(Client... elements) {
        Stream.of(elements).forEach(element -> collection.deleteOne(
                Filters.eq("personalID", element.getPersonalID())
        ));
    }

    @Override
    public void update(Client... elements) {
        Map<Bson, Bson> objectsToUpdate = new HashMap<>();
        Codec<Client> clientCodec = collection.getCodecRegistry().get(Client.class);
        Stream.of(elements).forEach(element -> {
            BsonDocument bsonDocument = new BsonDocument();
            clientCodec.encode(new BsonDocumentWriter(bsonDocument), element, EncoderContext.builder().build());
            objectsToUpdate.put(Filters.eq("personalID", element.getPersonalID()),
                    Updates.set("client", bsonDocument));
        });
        objectsToUpdate.forEach((key, value) -> collection.updateOne(key, value));
    }

    @Override
    public List<Client> find(Object... elements) {
        return Arrays.stream(elements)
                .map(this::get)
                .collect(Collectors.toList());
    }
}
