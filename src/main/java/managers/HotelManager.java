package managers;

import cassandra.CassandraConnector;
import com.datastax.oss.driver.api.core.CqlSession;
import exceptions.ClientException;
import exceptions.LogicException;
import exceptions.ReservationException;
import exceptions.RoomException;
import model.*;
import model.clientType.ClientType;
import org.apache.commons.math3.util.Precision;
import repositories.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

public class HotelManager {

    private static CqlSession SESSION;
    private ClientRepository clientRepository;
    private RoomRepository roomRepository;
    private ReservationRepository reservationRepository;

    public HotelManager() {
        SESSION = CassandraConnector.initSession();
        this.clientRepository = new ClientRepository(SESSION);
        this.roomRepository = new RoomRepository(SESSION);
        this.reservationRepository = new ReservationRepository(SESSION);
    }

    public static void closeSession() {
        SESSION.close();
    }

    private boolean checkIfRoomCantBeReserved(int roomNumber, LocalDate beginTime) {
        return !(reservationRepository.getAll().stream()
                .filter(reservation -> reservation.getRoomNumber().equals(roomNumber)
                        && (beginTime.isBefore(reservation.getEndTime())
                        || beginTime.equals(reservation.getEndTime())))
                .toList()).isEmpty();
    }

    private int getRentDays(Reservation reservation) {
        return reservation.getEndTime().getDayOfYear() - reservation.getBeginTime().getDayOfYear();
    }

    public void addClientToHotel(Client client) throws ClientException {
        try {
            clientRepository.get(client.getPersonalID());
            throw new ClientException("A given client exists");

        } catch (NoSuchElementException e) {
            clientRepository.add(client);

        } catch (ClientException clientException) {
            throw new ClientException(clientException.getMessage());
        }
    }

    public Client aboutClient(String personalId) throws ClientException {
        try {
            return clientRepository.get(personalId);
        } catch (NoSuchElementException e) {
            throw new ClientException("Any client didn't find");
        }
    }

    public void removeClientFormHotel(String personalId) {
        try {
            clientRepository.remove(clientRepository.get(personalId));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRoom(Integer roomNumber, Integer capacity, Double price, EquipmentType equipmentType)
            throws RoomException {
        try {
            roomRepository.get(roomNumber);
            throw new RoomException("Room with a given number exist");

        } catch (NoSuchElementException e) {
            roomRepository.add(new Room(roomNumber, capacity, price, equipmentType.getEqId()));
        } catch (RoomException roomException) {
            throw new RoomException(roomException.getMessage());
        }
    }

    public Room aboutRoom(Integer roomNumber) throws RoomException {
        try {
            return roomRepository.get(roomNumber);
        } catch (NoSuchElementException e) {
            throw new RoomException("Room with a given number doesn't exist");
        }
    }

    public void removeRoom(Integer roomNumber) {
        try {
            if (checkIfRoomCantBeReserved(roomNumber, LocalDate.now())) {
                throw new RoomException("A given room couldn't be removed because it's reserved");
            }
            Room room = roomRepository.get(roomNumber);
            roomRepository.remove(room);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRoomEquipment(int roomNumber, EquipmentType equipment) {
        try {
            Room room = roomRepository.get(roomNumber);
            room.setEquipmentTypeId(equipment.getEqId());
            roomRepository.update(room);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UUID reserveRoom(Integer roomNumber, LocalDate beginTime, LocalDate endTime,
                            String personalId) throws LogicException {
        try {
            Room room = roomRepository.get(roomNumber);
            Client client = clientRepository.get(personalId);

            if (beginTime.isAfter(endTime) ||
                    endTime.isBefore(beginTime)) {
                throw new ReservationException(
                        "Start time of reservation should be before end time reservation");

            } else if (beginTime.isBefore(LocalDate.now()) ||
                    endTime.isBefore(LocalDate.now())) {
                throw new ReservationException(
                        "Reservation cannot be before current date");

            } else if (checkIfRoomCantBeReserved(roomNumber, beginTime)) {
                throw new RoomException("Room is currently reserved");

            } else {
                Reservation newReservation =
                        new Reservation(UUID.randomUUID(), roomNumber, beginTime, endTime, personalId, 0);
                newReservation.setReservationCost(Precision.round(client.getDiscount()
                        * (getRentDays(newReservation) * room.getPrice()), 2));

                if (newReservation.getReservationCost() >= 1000 && client.getClientType().equals(ClientType.STANDARD.getTypeInfo())) {
                    client.setClientType(ClientType.PREMIUM.getTypeInfo());
                }

                reservationRepository.add(newReservation);
                return newReservation.getId();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ReservationException("Reservation couldn't be added");
    }

    public Reservation aboutReservation(UUID reservationId) throws ReservationException {
        try {
            return reservationRepository.get(reservationId);
        } catch (NoSuchElementException e) {
            throw new ReservationException("Any reservation for a given condition doesn't exist");
        }
    }
}