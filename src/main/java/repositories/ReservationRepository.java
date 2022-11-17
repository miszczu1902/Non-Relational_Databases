package repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.AllArgsConstructor;
import model.Reservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        Stream.of(elements).forEach(element -> {
            collection.replaceOne(Filters.eq("_id", element.getId()), element);
        });
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
