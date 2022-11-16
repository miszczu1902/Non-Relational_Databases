package repositories;

import model.Address;
import model.BasicModelTest;
import model.Client;
import model.ClientType;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ClientRepositoryTest extends BasicModelTest {

    private ClientRepository clientRepository = new ClientRepository();
    private Client client;

    @Before
    public void prepareDataToTest() {
        client = new Client(randomString(), randomString(), randomString(),
                new Address(RandomUtils.nextLong(), randomString(), randomString(), randomString()),
                ClientType.PREMIUM);
    }

    @Test
    public void testAdd() {
        clientRepository.add(client);
        clientRepository.find(client).forEach(element -> assertEquals(element, client));
    }

    @Test
    public void testGet() {
        clientRepository.add(client);
        assertEquals(client, clientRepository.get(client));
    }

    @Test
    public void testUpdate() {
        clientRepository.add(client);
        Client newClient = client;
        newClient.setClientType(ClientType.STANDARD);
        clientRepository.add(newClient);
        assertEquals(newClient, clientRepository.get(newClient));
    }

    @Test
    public void testRemove() {
        clientRepository.add(client);
        clientRepository.remove(client);
        assertNull(clientRepository.get(client));
    }
}
