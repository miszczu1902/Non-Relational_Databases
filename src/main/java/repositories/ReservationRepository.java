package repositories;

import com.datastax.oss.driver.api.core.CqlSession;
import model.Reservation;

import java.util.List;

public class ReservationRepository extends CassandraRepository
        implements Repository<Reservation> {

    public ReservationRepository(CqlSession session) {
        super(session);
    }

    @Override
    public Reservation get(Object element) {
        return null;
    }

    @Override
    public void add(Reservation... elements) {

    }

    @Override
    public void remove(Reservation... elements) {

    }

    @Override
    public void update(Reservation... elements) {

    }

    @Override
    public List<Reservation> find(Object... elements) {
        return null;
    }

    @Override
    public List<Reservation> getAll() {
        return null;
    }
}
