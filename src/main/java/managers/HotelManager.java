package managers;

import exceptions.ClientException;
import exceptions.LogicException;
import exceptions.ReservationException;
import exceptions.RoomException;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

import lombok.Getter;
import model.Address;
import model.Client;
import model.ClientType;
import model.EquipmentType;
import model.Reservation;
import model.Room;
import repositories.ClientRepository;
import repositories.ReservationRepository;
import repositories.RoomRepository;

public class HotelManager {

    @Getter
    @PersistenceContext
    private static final EntityManager entityManager =
            Persistence.createEntityManagerFactory("TEST_HOTEL").createEntityManager();
    private final EntityTransaction entityTransaction = entityManager.getTransaction();
    private final ClientRepository clientRepository = new ClientRepository(entityManager);
    private final RoomRepository roomRepository = new RoomRepository(entityManager);
    private final ReservationRepository reservationRepository = new ReservationRepository(entityManager);

    private boolean checkIfRoomCantBeReserved(int roomNumber, LocalDateTime beginTime) {
        return !(reservationRepository.getAll().stream()
                .filter(reservation -> reservation.getRoom().getRoomNumber().equals(roomNumber) &&
                        (beginTime.isBefore(reservation.getEndTime()) ||
                                beginTime.equals(reservation.getEndTime())))
                .toList()).isEmpty();
    }

    public void addClientToHotel(String personalId, String firstName, String lastName,
                                 Address address) throws ClientException {
        try {
            entityTransaction.begin();
            entityManager.lock(clientRepository.get(personalId), LockModeType.PESSIMISTIC_WRITE);
            entityTransaction.rollback();

            throw new ClientException("A given client exists");

        } catch (NoSuchElementException e) {
            clientRepository.add(new Client(personalId, firstName, lastName, address));
            entityTransaction.commit();

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
            entityTransaction.begin();
            clientRepository.remove(clientRepository.get(personalId));
            entityTransaction.commit();

        } catch (Exception e) {
            entityTransaction.rollback();

            e.printStackTrace();
        }
    }

    public void addRoom(Integer roomNumber, Integer capacity, Double price,
                        EquipmentType equipmentType) throws RoomException {
        try {
            entityTransaction.begin();
            roomRepository.get(roomNumber);
            entityTransaction.rollback();

            throw new RoomException("Room with a given number exist");

        } catch (NoSuchElementException e) {
            roomRepository.add(new Room(roomNumber, capacity, price, equipmentType));

            entityTransaction.commit();

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
            entityTransaction.begin();
            if (checkIfRoomCantBeReserved(roomNumber, LocalDateTime.now())) {
                throw new RoomException("A given room couldn't be removed because it's reserved");
            }

            Room room = roomRepository.get(roomNumber);
            roomRepository.remove(room);

            entityTransaction.commit();

        } catch (Exception e) {
            entityTransaction.rollback();

            e.printStackTrace();
        }
    }

    public void updateRoomEquipment(int roomNumber, EquipmentType equipment) {
        try {
            entityTransaction.begin();

            Room room = roomRepository.get(roomNumber);
            room.setEquipmentType(equipment);
            roomRepository.update(room);

            entityTransaction.commit();

        } catch (Exception e) {
            entityTransaction.rollback();

            e.printStackTrace();
        }
    }

    public UUID reserveRoom(Integer roomNumber, LocalDateTime beginTime, LocalDateTime endTime,
                            String personalId) throws LogicException {
        try {
            entityTransaction.begin();
            entityManager.lock(roomRepository.get(roomNumber), LockModeType.OPTIMISTIC_FORCE_INCREMENT);

            if (beginTime.isAfter(endTime) ||
                    endTime.isBefore(beginTime)) {
                throw new ReservationException(
                        "Start time of reservation should be before end time reservation");

            } else if (beginTime.isBefore(LocalDateTime.now()) ||
                    endTime.isBefore(LocalDateTime.now())) {
                throw new ReservationException(
                        "Reservation cannot be before current date");

            } else if (checkIfRoomCantBeReserved(roomNumber, beginTime)) {
                throw new RoomException("Room is currently reserved");

            } else {
                Reservation newReservation =
                        new Reservation(roomRepository.get(roomNumber), beginTime, endTime,
                                clientRepository.get(personalId));

                newReservation.calculateReservationCost();

                if (newReservation.getReservationCost() >= 1000 &&
                        newReservation.getClient().getClientType().equals(ClientType.STANDARD)) {
                    newReservation.getClient().setClientType(ClientType.PREMIUM);
                }

                reservationRepository.add(newReservation);
                entityTransaction.commit();

                return newReservation.getId();
            }

        } catch (Exception e) {
            entityTransaction.rollback();

            e.printStackTrace();
        }

        entityTransaction.rollback();

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
