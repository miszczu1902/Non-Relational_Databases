package repositories;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class RoomRepositoryTest extends BasicModelTest {

    private RoomRepository roomRepository = new RoomRepository();
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
}
