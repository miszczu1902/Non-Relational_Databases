package repositories;

import model.*;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class RoomRepositoryTest extends BasicModelTest {

    private RoomRepository repository = new RoomRepository();
    private Room room;

    @Test
    public void testAdd() {
        room = randomRoom();

        repository.add(room);
        repository.find(room).forEach(element -> assertEquals(element, room));
    }

    @Test
    public void testGet() {
        room = randomRoom();

        repository.add(room);
        assertEquals(room, repository.get(room));
    }

    @Test
    public void testUpdate() {
        room = randomRoom();

        repository.add(room);

        Room newRoom = room;
        newRoom.setCapacity(randomInt());

        repository.update(newRoom);
        assertEquals(newRoom, repository.get(newRoom));
    }

    @Test
    public void testRemove() {
        room = randomRoom();

        repository.add(room);
        repository.remove(room);
        assertThrows(NoSuchElementException.class, () -> repository.get(room));
    }

    @Test
    public void testGetAll() {
        int oldSize = repository.getAll().size();
        room = randomRoom();

        repository.add(room);
        assertEquals(oldSize + 1, repository.getAll().size());
    }

    @Test
    public void testClear() {
        room = randomRoom();
        repository.clear();

        assertTrue(repository.getAll().isEmpty());
    }
}
