package repositories;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import dao.*;
import mappers.*;
import model.*;

public abstract class CassandraRepository implements ClientDao, RoomDao, ReservationDao, AddressDao, EquipmentTypeDao {

    protected CqlSession SESSION;
    private ClientMapper clientMapper;
    private ClientDao clientDao;
    private RoomMapper roomMapper;
    private RoomDao roomDao;
    private ReservationMapper reservationMapper;
    private ReservationDao reservationDao;
    private AddressMapper addressMapper;
    private AddressDao addressDao;
    private EquipmentTypeMapper equipmentTypeMapper;
    private EquipmentTypeDao equipmentTypeDao;

    public CassandraRepository(CqlSession SESSION) {
        this.SESSION = SESSION;
        this.clientMapper = new ClientMapperBuilder(SESSION).build();
        this.clientDao = clientMapper.clientDao();
        this.roomMapper = new RoomMapperBuilder(SESSION).build();
        this.roomDao = roomMapper.roomDao();
        this.reservationMapper = new ReservationMapperBuilder(SESSION).build();
        this.reservationDao = reservationMapper.reservationDao();
        this.addressMapper = new AddressMapperBuilder(SESSION).build();
        this.addressDao = addressMapper.addressDao();
        this.equipmentTypeMapper = new EquipmentTypeMapperBuilder(SESSION).build();
        this.equipmentTypeDao = equipmentTypeMapper.equipmentTypeDao();
    }

    @Override
    public void createClient(Client client) {
        clientDao.createClient(client);
    }

    @Override
    public Client readClient(ResultSet resultSet) {
        return clientDao.readClient(resultSet);
    }

    @Override
    public void updateClient(Client client) {

    }

    @Override
    public void deleteClient(Client client) {

    }

    @Override
    public void createReservation(Reservation reservation) {

    }

    @Override
    public Reservation readReservation(ResultSet resultSet) {
        return null;
    }

    @Override
    public void updateReservation(Reservation reservation) {

    }

    @Override
    public void deleteReservation(Reservation reservation) {

    }

    @Override
    public void createRoom(Room room) {

    }

    @Override
    public Room readRoom(ResultSet resultSet) {
        return null;
    }

    @Override
    public void updateRoom(Room room) {

    }

    @Override
    public void deleteRoom(Room room) {

    }

    @Override
    public void createAddress(Address address) {

    }

    @Override
    public Address readAddress(ResultSet resultSet) {
        return null;
    }

    @Override
    public void updateAddress(Address address) {

    }

    @Override
    public void deleteClient(Address address) {

    }

    @Override
    public void createEquipmentType(EquipmentType equipmentType) {

    }

    @Override
    public EquipmentType readEquipmentType(ResultSet resultSet) {
        return null;
    }

    @Override
    public void updateEquipmentType(EquipmentType equipmentType) {

    }

    @Override
    public void deleteEquipmentType(EquipmentType equipmentType) {

    }
}
