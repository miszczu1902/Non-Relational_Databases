package cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;

import java.net.InetSocketAddress;

import static cassandra.CassandraNamespaces.*;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;

public class CassandraConnector {

    public static CqlSession initSession() {
        CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .addContactPoint(new InetSocketAddress("localhost", 9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("cassandra", "cassandra")
                .withKeyspace(CqlIdentifier.fromCql("hotel"))
                .build();

        CreateKeyspace keyspace = createKeyspace(HOTEL_NAMESPACE)
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true);
        SimpleStatement createKeyspace = keyspace.build();
        session.execute(createKeyspace);

        SimpleStatement createAddresses = SchemaBuilder.createTable(ADDRESSES_ID)
                .ifNotExists()
                .withPartitionKey(ADDRESS_ID, DataTypes.UUID)
                .withColumn(CITY, DataTypes.TEXT)
                .withColumn(STREET, DataTypes.TEXT)
                .withColumn(NUMBER, DataTypes.TEXT)
                .build();
        session.execute(createAddresses);

        SimpleStatement createClients = SchemaBuilder.createTable(CLIENTS_ID)
                .ifNotExists()
                .withPartitionKey(PERSONAL_ID, DataTypes.TEXT)
                .withColumn(FIRST_NAME, DataTypes.TEXT)
                .withColumn(LAST_NAME, DataTypes.TEXT)
                .withColumn(ADDRESS_ID, DataTypes.UUID)
                .withColumn(CLIENT_TYPE, DataTypes.TEXT)
                .withColumn(DISCOUNT, DataTypes.DOUBLE)
                .build();
        session.execute(createClients);

        SimpleStatement createRooms = SchemaBuilder.createTable(ROOMS_ID)
                .ifNotExists()
                .withPartitionKey(ROOM_NUMBER, DataTypes.INT)
                .withColumn(CAPACITY, DataTypes.INT)
                .withColumn(PRICE, DataTypes.DOUBLE)
                .withClusteringColumn(EQUIPMENT_TYPE_ID, DataTypes.UUID)
                .withClusteringOrder(EQUIPMENT_TYPE_ID, ClusteringOrder.ASC)
                .build();
        session.execute(createRooms);

        SimpleStatement createEquipmentTypes = SchemaBuilder.createTable(EQUIPMENT_TYPES)
                .ifNotExists()
                .withPartitionKey(EQ_ID, DataTypes.UUID)
                .withColumn(EQ_DESCRIPTION, DataTypes.TEXT)
                .withColumn(KETTLE, DataTypes.BOOLEAN)
                .withColumn(MICROWAVE, DataTypes.BOOLEAN)
                .withColumn(BALCONY, DataTypes.BOOLEAN)
                .withColumn(TV, DataTypes.BOOLEAN)
                .withColumn(AIR_CONDITIONING, DataTypes.BOOLEAN)
                .build();
        session.execute(createEquipmentTypes);

        SimpleStatement createReservations = SchemaBuilder.createTable(RESERVATIONS_BY_CLIENT)
                .ifNotExists()
                .withPartitionKey(RESERVATION_ID, DataTypes.UUID)
                .withColumn(ROOM_NUMBER, DataTypes.INT)
                .withColumn(BEGIN_TIME, DataTypes.DATE)
                .withColumn(END_TIME, DataTypes.DATE)
                .withColumn(RES_CLIENT_ID, DataTypes.TEXT)
                .withColumn(RESERVATION_COST, DataTypes.DOUBLE)
                .build();
        session.execute(createReservations);

        return session;
    }
}
