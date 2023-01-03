package kafka;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.extern.slf4j.Slf4j;
import managers.BasicManagerTest;
import managers.HotelManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repositories.ReservationRepository;

public abstract class KafkaTests extends BasicManagerTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(Slf4j.class);
    protected static final HotelManager HOTEL_MANAGER = new HotelManager();
    protected static final ReservationRepository RESERVATION_REPOSITORY = new ReservationRepository();
    protected static Jsonb jsonb = JsonbBuilder.create();
}
