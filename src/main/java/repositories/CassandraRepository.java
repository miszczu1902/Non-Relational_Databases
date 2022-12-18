package repositories;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import dao.*;
import mappers.*;
import model.*;

public abstract class CassandraRepository implements ClientDao, RoomDao, ReservationDao, AddressDao, EquipmentTypeDao {

    protected CqlSession session;
    protected ClientMapper clientMapper;
    protected ClientDao clientDao;
    protected RoomMapper roomMapper;
    protected RoomDao roomDao;
    protected ReservationMapper reservationMapper;
    protected ReservationDao reservationDao;
    protected AddressMapper addressMapper;
    protected AddressDao addressDao;
    protected EquipmentTypeMapper equipmentTypeMapper;
    protected EquipmentTypeDao equipmentTypeDao;

    public CassandraRepository(CqlSession session) {
        this.session = session;
        this.clientMapper = new ClientMapperBuilder(session).build();
        this.clientDao = clientMapper.clientDao();
        this.roomMapper = new RoomMapperBuilder(session).build();
        this.roomDao = roomMapper.roomDao();
        this.reservationMapper = new ReservationMapperBuilder(session).build();
        this.reservationDao = reservationMapper.reservationDao();
        this.addressMapper = new AddressMapperBuilder(session).build();
        this.addressDao = addressMapper.addressDao();
        this.equipmentTypeMapper = new EquipmentTypeMapperBuilder(session).build();
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
        clientDao.updateClient(client);
    }

    @Override
    public void deleteClient(Client client) {
        clientDao.deleteClient(client);
    }

    @Override
    public void createReservation(Reservation reservation) {
        reservationDao.createReservation(reservation);
    }

    @Override
    public Reservation readReservation(ResultSet resultSet) {
        return reservationDao.readReservation(resultSet);
    }

    @Override
    public void updateReservation(Reservation reservation) {
        reservationDao.updateReservation(reservation);
    }

    @Override
    public void deleteReservation(Reservation reservation) {
        reservationDao.deleteReservation(reservation);
    }

    @Override
    public void createRoom(Room room) {
        roomDao.createRoom(room);
    }

    @Override
    public Room readRoom(ResultSet resultSet) {
        return roomDao.readRoom(resultSet);
    }

    @Override
    public void updateRoom(Room room) {
        roomDao.updateRoom(room);
    }

    @Override
    public void deleteRoom(Room room) {
        roomDao.deleteRoom(room);
    }

    @Override
    public void createAddress(Address address) {
        addressDao.createAddress(address);
    }

    @Override
    public Address readAddress(ResultSet resultSet) {
        return addressDao.readAddress(resultSet);
    }

    @Override
    public void updateAddress(Address address) {
        addressDao.updateAddress(address);
    }

    @Override
    public void deleteAddress(Address address) {
        addressDao.deleteAddress(address);
    }

    @Override
    public void createEquipmentType(EquipmentType equipmentType) {
        equipmentTypeDao.createEquipmentType(equipmentType);
    }

    @Override
    public EquipmentType readEquipmentType(ResultSet resultSet) {
        return equipmentTypeDao.readEquipmentType(resultSet);
    }

    @Override
    public void updateEquipmentType(EquipmentType equipmentType) {
        equipmentTypeDao.updateEquipmentType(equipmentType);
    }

    @Override
    public void deleteEquipmentType(EquipmentType equipmentType) {
        equipmentTypeDao.deleteEquipmentType(equipmentType);
    }
}
