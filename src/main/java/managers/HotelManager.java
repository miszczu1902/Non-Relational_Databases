package managers;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import exceptions.ClientException;
import exceptions.LogicException;
import exceptions.ReservationException;
import exceptions.RoomException;
import model.*;
import model.clientType.ClientType;
import org.apache.commons.math3.util.Precision;
import repositories.*;

import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

import static cassandra.CassandraNamespaces.*;
import static cassandra.CassandraNamespaces.RESERVATIONS_BY_CLIENT;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;

public class HotelManager {

    private CqlSession session;
    private ClientRepository clientRepository;
    private RoomRepository roomRepository;
    private ReservationRepository reservationRepository;

    public HotelManager() {
        initSession();
        this.clientRepository = new ClientRepository(this.session);
        this.roomRepository = new RoomRepository(this.session);
        this.reservationRepository = new ReservationRepository(this.session);
    }

    public void initSession() {
        session = CqlSession.builder()
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

        SimpleStatement createClients = SchemaBuilder.createTable(CLIENT_ID)
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

    }

    private boolean checkIfRoomCantBeReserved(int roomNumber, LocalDate beginTime) {
        return !(reservationRepository.getAll().stream()
                .filter(reservation -> reservation.getRoomNumber().equals(roomNumber)
                        && (beginTime.isBefore(reservation.getEndTime())
                        || beginTime.equals(reservation.getEndTime())))
                .toList()).isEmpty();
    }

    public void addClientToHotel(Client client) throws ClientException {
        try {
            clientRepository.get(client.getPersonalID());
            throw new ClientException("A given client exists");

        } catch (NoSuchElementException e) {
            clientRepository.add(client);

        } catch (ClientException clientException) {
            throw new ClientException(clientException.getMessage());
        }
    }

    public Client aboutClient(String personalId) throws ClientException {
        try {
            return clientRepository.get(personalId);
        } catch (NoSuchElementException e) {
            throw new ClientException("Any client didn't find");
        }
    }

    public void removeClientFormHotel(String personalId) {
        try {
            clientRepository.remove(clientRepository.get(personalId));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRoom(Integer roomNumber, Integer capacity, Double price, EquipmentType equipmentType)
            throws RoomException {
        try {
            roomRepository.get(roomNumber);
            throw new RoomException("Room with a given number exist");

        } catch (NoSuchElementException e) {
            roomRepository.add(new Room(roomNumber, capacity, price, equipmentType.getId()));
        } catch (RoomException roomException) {
            throw new RoomException(roomException.getMessage());
        }
    }

    public Room aboutRoom(Integer roomNumber) throws RoomException {
        try {
            return roomRepository.get(roomNumber);
        } catch (NoSuchElementException e) {
            throw new RoomException("Room with a given number doesn't exist");
        }
    }

    public void removeRoom(Integer roomNumber) {
        try {
            if (checkIfRoomCantBeReserved(roomNumber, LocalDate.now())) {
                throw new RoomException("A given room couldn't be removed because it's reserved");
            }
            Room room = roomRepository.get(roomNumber);
            roomRepository.remove(room);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRoomEquipment(int roomNumber, EquipmentType equipment) {
        try {
            Room room = roomRepository.get(roomNumber);
            room.setEquipmentTypeId(equipment.getId());
            roomRepository.update(room);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UUID reserveRoom(Integer roomNumber, LocalDate beginTime, LocalDate endTime,
                            String personalId) throws LogicException {
        try {
            Room room = roomRepository.get(roomNumber);
            Client client = clientRepository.get(personalId);

            if (beginTime.isAfter(endTime) ||
                    endTime.isBefore(beginTime)) {
                throw new ReservationException(
                        "Start time of reservation should be before end time reservation");

            } else if (beginTime.isBefore(LocalDate.now()) ||
                    endTime.isBefore(LocalDate.now())) {
                throw new ReservationException(
                        "Reservation cannot be before current date");

            } else if (checkIfRoomCantBeReserved(roomNumber, beginTime)) {
                throw new RoomException("Room is currently reserved");

            } else {
                Reservation newReservation =
                        new Reservation(UUID.randomUUID(), roomNumber, beginTime, endTime, personalId, 0);
                newReservation.setReservationCost(Precision.round(client.getDiscount()
                        * (newReservation.getRentDays() * room.getPrice()), 2));

                if (newReservation.getReservationCost() >= 1000 && client.getClientType().equals(ClientType.STANDARD.getTypeInfo())) {
                    client.setClientType(ClientType.PREMIUM.getTypeInfo());
                }

                reservationRepository.add(newReservation);
                return newReservation.getId();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ReservationException("Reservation couldn't be added");
    }

    public Reservation aboutReservation(UUID reservationId) throws ReservationException {
        try {
            return reservationRepository.get(reservationId);
        } catch (NoSuchElementException e) {
            throw new ReservationException("Any reservation for a given condition doesn't exist");
        }
    }
}