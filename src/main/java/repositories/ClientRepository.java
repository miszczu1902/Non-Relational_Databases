package repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor
public class ClientRepository extends AbstractMongoRepository implements Repository<Client> {

    private final MongoCollection<Client> collection = hotelDB.getCollection("clients", Client.class);

    @Override
    public Client get(Object element) {
        return collection.find(Filters.eq("personalID", element.toString())).first();
    }

    @Override
    public void add(Client... elements) {
        Stream.of(elements).peek(collection::insertOne);
    }

    @Override
    public void remove(Client... elements) {

    }

    @Override
    public void update(Client... elements) {

    }

    @Override
    public List<Client> find(Object... elements) {
        return null;
    }

    @Override
    public List<Client> getAll() {
        return collection.aggregate(
                        List.of(Aggregates.replaceRoot("$client")), Client.class)
                .into(new ArrayList<>());
    }
}
