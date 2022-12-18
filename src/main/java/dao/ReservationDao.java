package dao;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.mapper.annotations.*;
import model.Reservation;

@Dao
public interface ReservationDao {

    @Insert
    void createReservation(Reservation reservation);

    @GetEntity
    Reservation readReservation(ResultSet resultSet);

    @Update
    void updateReservation(Reservation reservation);

    @Delete
    void deleteReservation(Reservation reservation);
}