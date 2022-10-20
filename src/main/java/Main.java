import exceptions.LogicException;

import java.time.LocalDateTime;

import managers.HotelManager;
import model.Address;
import model.Deluxe;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class Main {
    private static HotelManager hotelManager = new HotelManager();

    public static void main(String[] args) throws LogicException, InterruptedException {

        String clientID = RandomStringUtils.randomNumeric(11);
        int roomNumber = RandomUtils.nextInt();

        hotelManager.addClientToHotel(clientID, RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                new Address(RandomStringUtils.randomAlphanumeric(10),
                        RandomStringUtils.randomAlphanumeric(10),
                        RandomStringUtils.randomAlphanumeric(10)));

        hotelManager.addRoom(roomNumber, RandomUtils.nextInt(1, 10),
                RandomUtils.nextDouble(1, 100), new Deluxe(RandomStringUtils.randomAlphanumeric(25)));

        LocalDateTime beginTime = LocalDateTime.now().plusDays(5);
        LocalDateTime endTime = LocalDateTime.now().plusDays(14);

        reserveRoom(clientID, roomNumber, beginTime, endTime);
        reserveRoom(clientID, roomNumber, beginTime, endTime);

        HotelManager.getEntityManager().clear();
        HotelManager.getEntityManager().close();
    }

    private static void reserveRoom(String personalID, int roomNumber, LocalDateTime beginTime, LocalDateTime endTime)
            throws LogicException, InterruptedException {
        hotelManager.reserveRoom(roomNumber, beginTime, endTime, personalID);
        Thread.sleep(1000);
    }
}
