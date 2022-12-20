package repositories;

import cassandra.CassandraNamespaces;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import model.Reservation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.bindMarker;

public class ReservationRepository extends CassandraRepository<Reservation>
        implements Repository<Reservation> {

    public ReservationRepository(CqlSession session) {
        super(session);
    }

    @Override
    protected Reservation rowToEntity(Row row) {
        return new Reservation(
                row.getUuid(CassandraNamespaces.RESERVATION_ID),
                row.getInt(CassandraNamespaces.ROOM_NUMBER),
                Objects.requireNonNull(row.getLocalDate(CassandraNamespaces.BEGIN_TIME)),
                Objects.requireNonNull(row.getLocalDate(CassandraNamespaces.END_TIME)),
                Objects.requireNonNull(row.getString(CassandraNamespaces.RES_CLIENT_ID)),
                row.getDouble(CassandraNamespaces.RESERVATION_COST));
    }

    @Override
    public Reservation get(Object element) {
        Select getReservationById = QueryBuilder
                .selectFrom(CassandraNamespaces.RESERVATIONS_BY_CLIENT)
                .all()
                .where(Relation.column("id").isEqualTo(bindMarker()));

        PreparedStatement preparedStatement = session.prepare(getReservationById.build());

        return Optional.ofNullable(readReservation(session.execute(preparedStatement.bind(element))))
                .orElseThrow();
    }

    @Override
    public void add(Reservation... elements) {
        Stream.of(elements).forEach(this::createReservation);
    }

    @Override
    public void remove(Reservation... elements) {
        Stream.of(elements).forEach(this::deleteReservation);
    }

    @Override
    public void update(Reservation... elements) {
        Stream.of(elements).forEach(this::updateReservation);
    }

    @Override
    public List<Reservation> find(Object... elements) {
        Select getReservationsById = QueryBuilder
                .selectFrom(CassandraNamespaces.RESERVATIONS_BY_CLIENT)
                .all();
        Stream.of(elements).forEach(element ->
                getReservationsById.where(Relation.column("id")
                        .isEqualTo(bindMarker())));

        PreparedStatement preparedStatement = session.prepare(getReservationsById.build());

        return session.execute(preparedStatement.bind(elements)).all()
                .stream()
                .map(this::rowToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> getAll() {
        Select getReservations = QueryBuilder
                .selectFrom(CassandraNamespaces.RESERVATIONS_BY_CLIENT)
                .all();
        return session.execute(getReservations.build()).all()
                .stream()
                .map(this::rowToEntity)
                .collect(Collectors.toList());
    }
}
