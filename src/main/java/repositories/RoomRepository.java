package repositories;

import java.util.List;

import lombok.AllArgsConstructor;
import model.Room;

@AllArgsConstructor
public class RoomRepository extends AbstractMongoRepository implements Repository<Room> {
    @Override
    public Room get(Object element) {
        return null;
    }

    @Override
    public void add(Room... elements) {

    }

    @Override
    public void remove(Room... elements) {

    }

    @Override
    public void update(Room... elements) {

    }

    @Override
    public List<Room> find(Object... elements) {
        return null;
    }

    @Override
    public List<Room> getAll() {
        return null;
    }
}
