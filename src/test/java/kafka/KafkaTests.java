package kafka;

import exceptions.LogicException;
import lombok.extern.slf4j.Slf4j;
import managers.BasicManagerTest;
import managers.HotelManager;
import model.*;
import mongo.UniqueIdMgd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class KafkaTests extends BasicManagerTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(Slf4j.class);
    protected static final HotelManager HOTEL_MANAGER = new HotelManager();
    protected static List<Reservation> RESERVATIONS = new ArrayList<>();

    protected static void addReservationsToHotel(int amountOfReservations) throws LogicException {
        for (int index = 0; index < amountOfReservations; index++) {
            Client client = randomClient();
            Room room = randomRoom();

            HOTEL_MANAGER.addClientToHotel(client.getPersonalID());
            HOTEL_MANAGER.addRoom(room.getRoomNumber(), room.getCapacity(), room.getPrice(),
                    room.getEquipmentType());
            RESERVATIONS.add(new Reservation(new UniqueIdMgd(),
                    new Room(randomInt(), randomInt(), randomDouble(), EquipmentType.BASIC),
                    LocalDateTime.now().plusDays(1).toString(), LocalDateTime.now().plusDays(2).toString(),
                    new Client(randomString(), randomString(), randomString(),
                            new Address(randomInt(), randomString(), randomString(), randomString()),
                            ClientType.PREMIUM), randomDouble()));
        }
    }
}
