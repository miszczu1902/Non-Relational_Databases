package repositories;

import lombok.AllArgsConstructor;
import model.Reservation;

import java.util.List;

@AllArgsConstructor
public class ReservationRepository implements Repository<Reservation> {
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
