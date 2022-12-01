package failure;

import model.BasicModelTest;
import model.Client;
import model.Room;
import org.junit.jupiter.api.Test;
import repositories.ClientRepository;
import repositories.RoomRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RedisFailureTest extends BasicModelTest {

    @Test
    public void testGetRoom() throws Exception {
        RoomRepository roomRepository = new RoomRepository();
        Room room = randomRoom();
        roomRepository.add(room);
        roomRepository.close();

        assertEquals(room, roomRepository.get(room));
    }

    @Test
    public void testGetClient() throws Exception {
        ClientRepository clientRepository = new ClientRepository();
        Client client = randomClient();
        clientRepository.add(client);
        clientRepository.close();

        assertEquals(client, clientRepository.get(client));
    }
}
