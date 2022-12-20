package repositories;

import cassandra.CassandraNamespaces;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import model.Address;
import model.Client;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class ClientRepository extends CassandraRepository<Client> implements Repository<Client> {

    public ClientRepository(CqlSession session) {
        super(session);
    }

    @Override
    protected Client rowToEntity(Row row) {
        return new Client(Objects.requireNonNull(row.getString(CassandraNamespaces.PERSONAL_ID)),
                Objects.requireNonNull(row.getString(CassandraNamespaces.FIRST_NAME)),
                Objects.requireNonNull(row.getString(CassandraNamespaces.LAST_NAME)),
                Objects.requireNonNull(row.getUuid(CassandraNamespaces.ADDRESS_ID)),
                row.getString(CassandraNamespaces.CLIENT_TYPE),
                row.getDouble(CassandraNamespaces.DISCOUNT));
    }

    @Override
    public Client get(Object element) {
        Select getClientByPersonalID = QueryBuilder
                .selectFrom(CassandraNamespaces.CLIENT_ID)
                .all()
                .where(Relation.column("personalID").isEqualTo(literal(element.toString())));
        return Optional.ofNullable(readClient(session.execute(getClientByPersonalID.build())))
                .orElseThrow();
    }

    @Override
    public void add(Client... elements) {
        Stream.of(elements).forEach(this::createClient);
    }

    public void add(Address address, Client... elements) {
        addressDao.createAddress(address);
        this.add(elements);
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
        Select getClientsByPersonalID = QueryBuilder
                .selectFrom(CassandraNamespaces.CLIENT_ID)
                .all();
        Stream.of(elements).forEach(element ->
                getClientsByPersonalID.where(Relation.column("personalID")
                        .isEqualTo(literal(element.toString()))));

        return session.execute(getClientsByPersonalID.build()).all()
                .stream()
                .map(this::rowToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Client> getAll() {
        Select getClientsByPersonalID = QueryBuilder
                .selectFrom(CassandraNamespaces.CLIENT_ID)
                .all();

        return session.execute(getClientsByPersonalID.build()).all()
                .stream()
                .map(this::rowToEntity)
                .collect(Collectors.toList());
    }
}
