package managers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import model.Address;
import model.BasicModelTest;
import model.Client;
import model.Room;

public abstract class BasicManagerTest extends BasicModelTest {

    protected static Client createClient(String firstName, String lastName, String personalID,
                                         Address address) {
        return new Client(personalID, firstName, lastName,
                Optional.ofNullable(address)
                        .orElse(new Address(randomString(), randomString(), randomString())));
    }

    protected static Address createAddress(String city, String street, String number) {
        return new Address(city, street, number);
    }

    protected static Room createRoom(int roomNumber, int capacity, double price) {
        return new Room(roomNumber, capacity, price,
                getEquipmentTypes().get(0));
    }

    protected static List<Client> getRandomClient() {
        return Collections.singletonList(
                createClient(randomString(), randomString(), randomString(), null));
    }

    protected static List<Room> getRandomRoom() {
        return Collections.singletonList(
                createRoom(randomInt(), randomInt(), randomDouble()));
    }

    protected static Client randomClient() {
        return getRandomClient().get(0);
    }

    protected static Room randomRoom() {
        return getRandomRoom().get(0);
    }
}
