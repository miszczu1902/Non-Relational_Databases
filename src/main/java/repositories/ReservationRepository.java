package repositories;

import jakarta.persistence.EntityManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import model.Reservation;

@AllArgsConstructor
public class ReservationRepository implements Repository<Reservation> {

    private EntityManager entityManager;

    @Override
    public Reservation get(Object element) {
        return Optional.ofNullable(find(element).get(0)).orElseThrow();
    }

    @Override
    public List<Reservation> find(Object... elements) {
        return Optional.of(Arrays.stream(elements)
                .map(element -> entityManager.find(Reservation.class, element))
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    @Override
    public List<Reservation> getAll() {
        return entityManager.createQuery("SELECT reservation FROM Reservation reservation", Reservation.class)
                .getResultList();
    }

    @Override
    public void add(Reservation... elements) {
        Arrays.asList(elements).forEach(element -> entityManager.persist(element));
    }

    @Override
    public void remove(Reservation... elements) {
        Arrays.asList(elements).forEach(element -> entityManager.remove(element));
    }

    @Override
    public void update(Reservation... elements) {
        Arrays.asList(elements).forEach(element -> entityManager.merge(element));
    }
}
