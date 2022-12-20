package managers;

import model.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class BasicManagerTest extends BasicModelTest {

    protected static Client createClient(String firstName, String lastName, String personalID) {
        return new Client(personalID, firstName, lastName, UUID.randomUUID());
    }

    protected static List<Client> getRandomClient() {
        return Collections.singletonList(
                createClient(randomString(), randomString(), randomString()));
    }

    protected static Client randomClient() {
        return getRandomClient().get(0);
    }
}