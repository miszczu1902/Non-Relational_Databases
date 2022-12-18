package cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class CassandraNamespaces {

    public static final CqlIdentifier HOTEL_NAMESPACE = CqlIdentifier.fromCql("hotel");
    public static final CqlIdentifier RESERVATIONS_BY_CLIENT = CqlIdentifier.fromCql("res_by_client");
    public static final CqlIdentifier CLIENT_ID = CqlIdentifier.fromCql("clients_id");
    public static final CqlIdentifier ADDRESS_ID = CqlIdentifier.fromCql("addresses_id");
    public static final CqlIdentifier ROOM_ID = CqlIdentifier.fromCql("rooms_id");
    public static final CqlIdentifier EQUIPMENTS_ID = CqlIdentifier.fromCql("equipment_types");
}
