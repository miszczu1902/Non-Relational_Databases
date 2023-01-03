package kafka;

import exceptions.LogicException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.extern.slf4j.Slf4j;
import managers.BasicManagerTest;
import managers.HotelManager;
import model.Client;
import model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repositories.ReservationRepository;

import java.time.LocalDateTime;

public abstract class KafkaTests extends BasicManagerTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(Slf4j.class);
    protected static final HotelManager HOTEL_MANAGER = new HotelManager();
    protected static final ReservationRepository RESERVATION_REPOSITORY = new ReservationRepository();
    protected static Jsonb jsonb = JsonbBuilder.create();

    protected static void addReservationsToHotel(int amountOfReservations) throws LogicException {
        for (int index = 0; index < amountOfReservations; index++) {
            Client client = randomClient();
            Room room = randomRoom();
            LocalDateTime beginTime = LocalDateTime.now();
            LocalDateTime endTime = LocalDateTime.now().plusDays(2);

            HOTEL_MANAGER.addClientToHotel(client.getPersonalID());
            HOTEL_MANAGER.addRoom(room.getRoomNumber(), room.getCapacity(), room.getPrice(),
                    room.getEquipmentType());
            HOTEL_MANAGER.reserveRoom(room.getRoomNumber(),
                    beginTime, endTime, client.getPersonalID());
        }

    }
}
