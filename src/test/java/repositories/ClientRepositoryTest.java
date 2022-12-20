package repositories;

import cassandra.CassandraConnector;
import com.datastax.oss.driver.api.core.CqlSession;
import model.Address;
import model.BasicModelTest;
import model.Client;
import model.clientType.ClientType;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryTest extends BasicModelTest {

    private static CqlSession session = CassandraConnector.initSession();
    private ClientRepository repository = new ClientRepository(session);
    private Address address;
    private Client client;

    @Before
    public void initToTest() {
        address = new Address(UUID.randomUUID(), randomString(), randomString(), randomString());
        client = new Client(randomString(), randomString(), randomString(), address.getAddressId());
    }

    @Test
    public void testAddAndGet() {
        repository.add(address, client);

        assertEquals(client, repository.get(client.getPersonalID()));
    }

    @Test
    public void testUpdate() {
        repository.add(address, client);

        Client newClient = SerializationUtils.clone(client);
        newClient.setClientType(ClientType.STANDARD.toString());

        repository.update(newClient);
        assertEquals(newClient, repository.get(newClient.getPersonalID()));
    }

    @Test
    public void testRemove() {
        repository.add(address, client);
        repository.remove(client);

        assertThrows(NoSuchElementException.class, () -> repository.get(client.getPersonalID()));
    }

    @AfterAll
    public static void closeSession() {
        session.close();
    }
}
