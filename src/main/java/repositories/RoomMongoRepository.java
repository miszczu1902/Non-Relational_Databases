package repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoomMongoRepository extends AbstractMongoRepository implements Repository<Room> {

    private MongoCollection<Room> collection;

    public RoomMongoRepository() {
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
        Stream.of(elements).forEach(element -> {
            collection.replaceOne(Filters.eq("roomNumber", element.getRoomNumber()), element);
        });
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

    @Override
    public Long size() {
        return collection.countDocuments();
    }
}