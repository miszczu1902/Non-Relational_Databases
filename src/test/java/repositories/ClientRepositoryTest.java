//package repositories;
//
//import model.BasicModelTest;
//import model.Client;
//import model.ClientType;
//import org.junit.jupiter.api.Test;
//
//import java.util.NoSuchElementException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ClientRepositoryTest extends BasicModelTest {
//    private static ClientRepository repository = new ClientRepository();
//    private Client client;
//
//    @Test
//    public void testAdd() {
//        client = randomClient();
//
//        repository.add(client);
//        repository.find(client).forEach(element -> assertEquals(element, client));
//    }
//
//    @Test
//    public void testGet() {
//        client = randomClient();
//
//        repository.add(client);
//        assertEquals(client, repository.get(client));
//    }
//
//    @Test
//    public void testUpdate() {
//        client = randomClient();
//
//        repository.add(client);
//
//        Client newClient = client;
//        newClient.setClientType(ClientType.STANDARD);
//
//        repository.update(newClient);
//        assertEquals(newClient, repository.get(newClient));
//    }
//
//    @Test
//    public void testRemove() {
//        client = randomClient();
//
//        repository.add(client);
//        repository.remove(client);
//        assertThrows(NoSuchElementException.class, () -> repository.get(client));
//    }
//
//    @Test
//    public void testClear() {
//        repository.clear();
//
//        assertEquals(0, repository.size());
//    }
//}
