package cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import lombok.Getter;

import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;

public class CassandraClient {

    @Getter
    private CqlSession session;

    public static final CqlIdentifier HOTEL_NAMESPACE = CqlIdentifier.fromCql("hotel");
    public static final CqlIdentifier RESERVATIONS_BY_CLIENT = CqlIdentifier.fromCql("res_by_client");
    public static final CqlIdentifier CLIENT_ID = CqlIdentifier.fromCql("client_id");
    public static final CqlIdentifier ROOM_ID = CqlIdentifier.fromCql("room_id");
//    public static final CqlIdentifier RESERVATION_ID = CqlIdentifier.fromCql("res_id");

    public CassandraClient() {
        this.initSession();
    }

    public void initSession() {
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("172.24.0.2", 9042))
//                .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("cassandra", "cassandra")
//                .withLocalDatacenter("cassandra-cluster")
                .withKeyspace(CqlIdentifier.fromCql("hotel"))
                .build();

        CreateKeyspace keyspace = createKeyspace(HOTEL_NAMESPACE)
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true);
        SimpleStatement createKeyspace = keyspace.build();
        session.execute(createKeyspace);

        SimpleStatement createClients = SchemaBuilder.createTable(CLIENT_ID)
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("personalID"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("firstName"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("lastName"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("address"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("clientType"), DataTypes.TEXT)
                .build();
        session.execute(createClients);

        SimpleStatement createRooms = SchemaBuilder.createTable(ROOM_ID)
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("roomNumber"), DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql("capacity"), DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql("price"), DataTypes.DOUBLE)
                .withClusteringColumn(CqlIdentifier.fromCql("equipmentType"), DataTypes.TEXT)
                .withClusteringOrder(CqlIdentifier.fromCql("equipmentType"), ClusteringOrder.ASC)
                .build();
        session.execute(createRooms);

        SimpleStatement createReservations = SchemaBuilder.createTable(RESERVATIONS_BY_CLIENT)
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("room"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("beginTime"), DataTypes.TIMESTAMP)
                .withColumn(CqlIdentifier.fromCql("endTime"), DataTypes.TIMESTAMP)
                .withColumn(CqlIdentifier.fromCql("client"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("reservationCost"), DataTypes.DOUBLE)
                .build();
        session.execute(createReservations);
    }
}
