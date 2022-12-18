package repositories;

import cassandra.CassandraNamespaces;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import model.Client;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class ClientRepository extends CassandraRepository implements Repository<Client> {

    public ClientRepository(CqlSession session) {
        super(session);
    }

    @Override
    public Client get(Object element) {
        Select getClientByPersonalID = QueryBuilder
                .selectFrom(CassandraNamespaces.CLIENT_ID)
                .all()
                .where(Relation.column("personalID").isEqualTo(literal(element.toString())));
        return Optional.ofNullable(readClient(SESSION.execute(getClientByPersonalID.build())))
                .orElseThrow();
    }

    @Override
    public void add(Client... elements) {
        Stream.of(elements).forEach(this::createClient);
    }

    @Override
    public void remove(Client... elements) {
        Stream.of(elements).forEach(this::deleteClient);
    }

    @Override
    public void update(Client... elements) {
        Stream.of(elements).forEach(this::updateClient);
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
