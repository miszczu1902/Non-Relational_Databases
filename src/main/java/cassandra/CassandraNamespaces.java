package cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class CassandraNamespaces {

    public static final CqlIdentifier HOTEL_NAMESPACE = CqlIdentifier.fromCql("hotel");
    public static final CqlIdentifier RESERVATIONS_BY_CLIENT = CqlIdentifier.fromCql("res_by_client");
    public static final CqlIdentifier CLIENTS_ID = CqlIdentifier.fromCql("clients_id");
    public static final CqlIdentifier ADDRESSES_ID = CqlIdentifier.fromCql("addresses_id");
    public static final CqlIdentifier ROOMS_ID = CqlIdentifier.fromCql("rooms_id");
    public static final CqlIdentifier EQUIPMENT_TYPES = CqlIdentifier.fromCql("equipment_types");
    public static final CqlIdentifier FIRST_NAME = CqlIdentifier.fromCql("firstName");
    public static final CqlIdentifier LAST_NAME = CqlIdentifier.fromCql("lastName");
    public static final CqlIdentifier ADDRESS_ID = CqlIdentifier.fromCql("addressId");
    public static final CqlIdentifier CLIENT_TYPE = CqlIdentifier.fromCql("clientType");
    public static final CqlIdentifier DISCOUNT = CqlIdentifier.fromCql("discount");
    public static final CqlIdentifier ROOM_NUMBER = CqlIdentifier.fromCql("roomNumber");
    public static final CqlIdentifier CAPACITY = CqlIdentifier.fromCql("capacity");
    public static final CqlIdentifier PRICE = CqlIdentifier.fromCql("price");
    public static final CqlIdentifier EQUIPMENT_TYPE_ID = CqlIdentifier.fromCql("equipmentTypeId");
    public static final CqlIdentifier EQ_ID = CqlIdentifier.fromCql("eqId");
    public static final CqlIdentifier EQ_DESCRIPTION = CqlIdentifier.fromCql("eq_description");
    public static final CqlIdentifier KETTLE = CqlIdentifier.fromCql("kettle");
    public static final CqlIdentifier MICROWAVE = CqlIdentifier.fromCql("microwave");
    public static final CqlIdentifier BALCONY = CqlIdentifier.fromCql("balcony");
    public static final CqlIdentifier TV = CqlIdentifier.fromCql("tv");
    public static final CqlIdentifier AIR_CONDITIONING = CqlIdentifier.fromCql("airConditioning");
    public static final CqlIdentifier RESERVATION_ID = CqlIdentifier.fromCql("id");
    public static final CqlIdentifier CITY = CqlIdentifier.fromCql("city");
    public static final CqlIdentifier STREET = CqlIdentifier.fromCql("street");
    public static final CqlIdentifier NUMBER = CqlIdentifier.fromCql("number");
    public static final CqlIdentifier PERSONAL_ID = CqlIdentifier.fromCql("personalID");
    public static final CqlIdentifier BEGIN_TIME = CqlIdentifier.fromCql("beginTime");
    public static final CqlIdentifier END_TIME = CqlIdentifier.fromCql("endTime");
    public static final CqlIdentifier RES_CLIENT_ID = CqlIdentifier.fromCql("clientId");
    public static final CqlIdentifier RESERVATION_COST = CqlIdentifier.fromCql("reservationCost");
}
