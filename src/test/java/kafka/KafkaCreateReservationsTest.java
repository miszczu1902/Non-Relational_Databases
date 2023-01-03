package kafka;

import kafka.producers.HotelProducer;
import model.Reservation;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class KafkaCreateReservationsTest extends KafkaTests {

    protected static HotelProducer hotelProducer;

    @BeforeAll
    public static void prepareProducerToTest() {
        try {
            hotelProducer = new HotelProducer();
        } catch (ExecutionException | InterruptedException e) {
            LOGGER.warn(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Test
    public void sendReservationsToTopic() {
        List<Reservation> reservations = RESERVATION_REPOSITORY.getAll();

        reservations.forEach(reservation -> {
            try {
                hotelProducer.send(reservation);
                LOGGER.info("Reservation added: " + new JSONObject(reservation));
                Thread.sleep(5000);
            } catch (ExecutionException | InterruptedException e) {
                LOGGER.warn(e.getMessage());
            }
        });
    }

    @AfterAll
    public static void closeProducer() {
        hotelProducer.close();
    }

    //    @Test
//    public void createReservation() throws LogicException {
//        Client client = randomClient();
//        Room room = randomRoom();
//        LocalDateTime beginTime = LocalDateTime.now();
//        LocalDateTime endTime = LocalDateTime.now().plusDays(2);
//
//        HOTEL_MANAGER.addClientToHotel(client.getPersonalID());
//        HOTEL_MANAGER.addRoom(room.getRoomNumber(), room.getCapacity(), room.getPrice(),
//                room.getEquipmentType());
//
//        UUID reservationId = HOTEL_MANAGER.reserveRoom(room.getRoomNumber(),
//                beginTime, endTime, client.getPersonalID());
//
//        Reservation reservation = HOTEL_MANAGER.aboutReservation(reservationId);
//
//        while (true) {
//            try {
//                hotelProducer.send(reservation);
////                hotelProducer.close();
//                LOGGER.info("Reservation added: " + jsonb.toJson(reservation));
//                Thread.sleep(5000);
//            } catch (ExecutionException | InterruptedException e) {
//                LOGGER.warn(e.getMessage());
//
//            }
//        }
//    }
}
