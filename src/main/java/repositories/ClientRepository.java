package repositories;

import com.datastax.oss.driver.api.core.CqlSession;
import model.Client;

import java.util.List;

public class ClientRepository extends CassandraRepository implements Repository<Client> {

    public ClientRepository(CqlSession session) {
        super(session);
    }

    @Override
    public Client get(Object element) {
        return null;
    }

    @Override
    public void add(Client... elements) {

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
        return null;
    }
}
