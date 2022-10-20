package repositories;

import jakarta.persistence.EntityManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import model.Client;

@AllArgsConstructor
public class ClientRepository implements Repository<Client> {

    private EntityManager entityManager;

    @Override
    public Client get(Object element) {
        return Optional.ofNullable(find(element).get(0)).orElseThrow();
    }

    @Override
    public List<Client> find(Object... elements) {
        return Optional.of(Arrays.stream(elements)
                .map(element -> entityManager.find(Client.class, element))
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    @Override
    public List<Client> getAll() {
        return entityManager.createQuery("SELECt client FROM Client client", Client.class)
                .getResultList();
    }

    @Override
    public void add(Client... elements) {
        Arrays.asList(elements).forEach(element -> entityManager.persist(element));
    }

    @Override
    public void remove(Client... elements) {
        Arrays.asList(elements).forEach(element -> entityManager.remove(element));
    }

    @Override
    public void update(Client... elements) {
        Arrays.asList(elements).forEach(element -> entityManager.merge(element));
    }
}
