package repositories;

import cassandra.CassandraConnector;
import com.datastax.oss.driver.api.core.CqlSession;
import model.*;
import model.equipmentType.Extended;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RoomRepositoryTest extends BasicModelTest {

    private static CqlSession session = CassandraConnector.initSession();
    private RoomRepository repository = new RoomRepository(session);
    private Room room;
    private EquipmentType equipmentType;

    @Test
    public void testAddAndGet() {
        equipmentType = new Extended();
        equipmentType.setEqId(UUID.randomUUID());
        room = new Room(randomInt(), randomInt(), randomDouble(), equipmentType.getEqId());

        repository.add(equipmentType, room);

        assertEquals(room, repository.get(room.getRoomNumber()));
    }

    @Test
    public void testUpdate() {
        equipmentType = new Extended();
        equipmentType.setEqId(UUID.randomUUID());
        room = new Room(randomInt(), randomInt(), randomDouble(), equipmentType.getEqId());

        repository.add(equipmentType, room);

        Room newRoom = SerializationUtils.clone(room);
        newRoom.setCapacity(randomInt());

        repository.update(newRoom);
        assertEquals(newRoom, repository.get(newRoom.getRoomNumber()));
    }

    @Test
    public void testRemove() {
        equipmentType = new Extended();
        equipmentType.setEqId(UUID.randomUUID());
        room = new Room(randomInt(), randomInt(), randomDouble(), equipmentType.getEqId());

        repository.add(equipmentType, room);
        repository.remove(room);

        assertThrows(NoSuchElementException.class, () -> repository.get(room.getRoomNumber()));
    }

    @AfterAll
    public static void closeSession() {
        session.close();
    }
}
