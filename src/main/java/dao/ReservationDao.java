package dao;

import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import model.Reservation;

public interface ReservationDao {

    @Insert
    void createReservation(Reservation reservation);

    @Update
    void updateReservation(Reservation reservation);

    @Delete
    void deleteReservation(Reservation reservation);
}
