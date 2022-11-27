package repositories;

import model.BasicModelTest;
import model.EquipmentType;
import model.Room;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoomMongoRepositoryTest extends BasicModelTest {

    private RoomMongoRepository roomRepository = new RoomMongoRepository();
    private Room room;

    @Before
    public void prepareDataToTest() {
        room = new Room(randomInt(), randomInt(), randomDouble(), EquipmentType.BASIC);
    }

    @Test
    public void testAdd() {
        roomRepository.add(room);
        assertEquals(room, roomRepository.get(room));
    }

    @Test
    public void testGet() {
        roomRepository.add(room);
        assertEquals(room, roomRepository.get(room));
    }

    @Test
    public void testUpdate() {
        roomRepository.add(room);
        Room newRoom = room;
        newRoom.setEquipmentType(EquipmentType.BASIC);
        roomRepository.update(newRoom);
        assertEquals(newRoom, roomRepository.get(newRoom));
    }

    @Test
    public void testRemove() {
        roomRepository.add(room);
        roomRepository.remove(room);
        assertThrows(NoSuchElementException.class, () -> roomRepository.get(room));
    }

    @Test
    public void testGetAll() {
        int oldSize = roomRepository.getAll().size();
        roomRepository.add(room);
        assertEquals(oldSize + 1, roomRepository.getAll().size());
    }
}
