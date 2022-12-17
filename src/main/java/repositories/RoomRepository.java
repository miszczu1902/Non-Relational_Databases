package repositories;

import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;
import model.Room;

public class RoomRepository extends CassandraRepository implements Repository<Room> {

    public RoomRepository(CqlSession session) {
        super(session);
    }

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
