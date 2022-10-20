package repositories;

import jakarta.persistence.EntityManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import model.Room;

@AllArgsConstructor
public class RoomRepository implements Repository<Room> {

    private EntityManager entityManager;

    @Override
    public Room get(Object element) {
        return Optional.ofNullable(find(element).get(0)).orElseThrow();
    }

    @Override
    public List<Room> find(Object... elements) {
        return Optional.of(Arrays.stream(elements)
                .map(element -> entityManager.find(Room.class, element))
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    @Override
    public List<Room> getAll() {
        return entityManager.createQuery("SELECT room FROM Room room", Room.class)
                .getResultList();
    }

    @Override
    public void add(Room... elements) {
        Arrays.asList(elements).forEach(element -> entityManager.persist(element));
    }

    @Override
    public void remove(Room... elements) {
        Arrays.asList(elements).forEach(element -> entityManager.remove(element));
    }

    @Override
    public void update(Room... elements) {
        Arrays.asList(elements).forEach(element -> entityManager.merge(element));
    }
}
