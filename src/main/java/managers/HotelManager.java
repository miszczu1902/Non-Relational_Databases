package managers;

import exceptions.ClientException;
import exceptions.LogicException;
import exceptions.ReservationException;
import exceptions.RoomException;
import model.*;
import mongo.UniqueIdMgd;
import repositories.ClientRepository;
import repositories.ReservationRepository;
import repositories.RoomRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

public class HotelManager {

    private ClientRepository clientRepository = new ClientRepository();
    private RoomRepository roomRepository = new RoomRepository();
    private ReservationRepository reservationRepository = new ReservationRepository();

    private boolean checkIfRoomCantBeReserved(int roomNumber, LocalDateTime beginTime) {
        return !(reservationRepository.getAll().stream()
                .filter(reservation -> reservation.getRoom().getRoomNumber().equals(roomNumber) &&
                        (beginTime.isBefore(reservation.getEndTime()) ||
                                beginTime.equals(reservation.getEndTime())))
                .toList()).isEmpty();
    }

    public void addClientToHotel(String personalId) throws ClientException {
        try {
            clientRepository.get(new Client(personalId));
            throw new ClientException("A given client exists");

        } catch (NoSuchElementException e) {
            clientRepository.add(new Client(personalId));

        } catch (ClientException clientException) {
            throw new ClientException(clientException.getMessage());
        }
    }

    public Client aboutClient(String personalId) throws ClientException {
        try {
            return clientRepository.get(new Client(personalId));
        } catch (NoSuchElementException e) {
            throw new ClientException("Any client didn't find");
        }
    }

    public void removeClientFormHotel(String personalId) {
        try {
            clientRepository.remove(clientRepository.get(new Client(personalId)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRoom(Integer roomNumber, Integer capacity, Double price,
                        EquipmentType equipmentType) throws RoomException {
        try {
            roomRepository.get(new Room(roomNumber));
            throw new RoomException("Room with a given number exist");

        } catch (NoSuchElementException e) {
            roomRepository.add(new Room(roomNumber, capacity, price, equipmentType));
        } catch (RoomException roomException) {
            throw new RoomException(roomException.getMessage());
        }
    }

    public Room aboutRoom(Integer roomNumber) throws RoomException {
        try {
            return roomRepository.get(new Room(roomNumber));
        } catch (NoSuchElementException e) {
            throw new RoomException("Room with a given number doesn't exist");
        }
    }

    public void removeRoom(Integer roomNumber) {
        try {
            if (checkIfRoomCantBeReserved(roomNumber, LocalDateTime.now())) {
                throw new RoomException("A given room couldn't be removed because it's reserved");
            }
            Room room = roomRepository.get(new Room(roomNumber));
            roomRepository.remove(room);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRoomEquipment(int roomNumber, EquipmentType equipment) {
        try {
            Room room = roomRepository.get(new Room(roomNumber));
            room.setEquipmentType(equipment);
            roomRepository.update(room);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UUID reserveRoom(Integer roomNumber, LocalDateTime beginTime, LocalDateTime endTime,
                            String personalId) throws LogicException {
        try {
            roomRepository.get(new Room(roomNumber));

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
                        new Reservation(new UniqueIdMgd(), roomRepository.get(new Room(roomNumber)), beginTime, endTime,
                                clientRepository.get(new Client(personalId)), 0);
                newReservation.calculateReservationCost();

                if (newReservation.getReservationCost() >= 1000 &&
                        newReservation.getClient().getClientType().equals(ClientType.STANDARD)) {
                    newReservation.getClient().setClientType(ClientType.PREMIUM);
                }

                reservationRepository.add(newReservation);
                return newReservation.getId().getUuid();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ReservationException("Reservation couldn't be added");
    }

    public Reservation aboutReservation(UUID reservationId) throws ReservationException {
        try {
            return reservationRepository.get(new Reservation(new UniqueIdMgd(reservationId)));
        } catch (NoSuchElementException e) {
            throw new ReservationException("Any reservation for a given condition doesn't exist");
        }
    }
}
