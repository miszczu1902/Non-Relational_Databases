package dao;

import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import model.Room;

public interface RoomDao {

    @Insert
    void createRoom(Room room);

    @Update
    void updateRoom(Room room);

    @Delete
    void deleteRoom(Room room);
}
