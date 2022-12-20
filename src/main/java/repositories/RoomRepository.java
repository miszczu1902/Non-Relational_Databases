package repositories;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cassandra.CassandraNamespaces;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import model.EquipmentType;
import model.Room;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.bindMarker;

public class RoomRepository extends CassandraRepository<Room> implements Repository<Room> {

    public RoomRepository(CqlSession session) {
        super(session);
    }

    @Override
    protected Room rowToEntity(Row row) {
        return new Room(row.getInt(CassandraNamespaces.ROOM_NUMBER),
                row.getInt(CassandraNamespaces.CAPACITY),
                row.getDouble(CassandraNamespaces.PRICE),
                Objects.requireNonNull(row.getUuid(CassandraNamespaces.EQUIPMENT_TYPE_ID)));
    }

    @Override
    public Room get(Object element) {
        Select getRoomByRoomNumber = QueryBuilder
                .selectFrom(CassandraNamespaces.ROOMS_ID)
                .all()
                .where(Relation.column("roomNumber").isEqualTo(bindMarker()));

        PreparedStatement preparedStatement = session.prepare(getRoomByRoomNumber.build());

        return Optional.ofNullable(readRoom(session.execute(preparedStatement.bind(element))))
                .orElseThrow();
    }

    @Override
    public void add(Room... elements) {
        Stream.of(elements).forEach(this::createRoom);
    }

    public void add(EquipmentType equipmentType, Room... elements) {
        equipmentTypeDao.createEquipmentType(equipmentType);
        this.add(elements);
    }

    @Override
    public void remove(Room... elements) {
        Stream.of(elements).forEach(this::deleteRoom);
    }

    @Override
    public void update(Room... elements) {
        Stream.of(elements).forEach(this::updateRoom);
    }

    @Override
    public List<Room> find(Object... elements) {
        Select getRoomsByRoomNumber = QueryBuilder
                .selectFrom(CassandraNamespaces.ROOMS_ID)
                .all();
        Stream.of(elements).forEach(element ->
                getRoomsByRoomNumber.where(Relation.column("roomNumber")
                        .isEqualTo(bindMarker())));

        PreparedStatement preparedStatement = session.prepare(getRoomsByRoomNumber.build());

        return session.execute(preparedStatement.bind(elements)).all()
                .stream()
                .map(this::rowToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Room> getAll() {
        Select getRooms = QueryBuilder
                .selectFrom(CassandraNamespaces.ROOMS_ID)
                .all();

        return session.execute(getRooms.build()).all()
                .stream()
                .map(this::rowToEntity)
                .collect(Collectors.toList());
    }
}
