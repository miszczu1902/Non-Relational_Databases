package repositories;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import dao.ClientDao;
import dao.ReservationDao;
import dao.RoomDao;
import model.Client;
import model.Reservation;
import model.Room;

public class CassandraRepository implements ClientDao, RoomDao, ReservationDao {

    private CqlSession session;

    public CassandraRepository(CqlSession session) {
        this.session = session;
    }

    @Override
    public void createClient(Client client) {

    }

    @Override
    public Client readClient(ResultSet resultSet) {
        return null;
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
}
