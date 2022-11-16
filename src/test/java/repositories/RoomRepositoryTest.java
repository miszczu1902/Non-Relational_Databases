package repositories;

import model.*;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RoomRepositoryTest extends BasicModelTest {

    private RoomRepository roomRepository = new RoomRepository();
    private Room room;

    @Before
    public void prepareDataToTest() {
        room = new Room(randomInt(), randomInt(), randomDouble(), new Basic(randomString(), true, true));
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
        newRoom.setEquipmentType(new Basic(randomString(), true, true));
        roomRepository.add(newRoom);
        assertEquals(newRoom, roomRepository.get(newRoom));
    }

    @Test
    public void testRemove() {
        roomRepository.add(room);
        roomRepository.remove(room);
        assertNull(roomRepository.get(room));
    }

}
