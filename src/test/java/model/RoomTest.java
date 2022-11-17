package model;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class RoomTest extends BasicModelTest {

    private Room room = new Room(
            randomInt(), randomInt(), randomDouble(), getEquipmentTypes().get(0));

    @ParameterizedTest
    @MethodSource("randomInts")
    void testRoomNumber(Integer roomNumber) {
        room.setRoomNumber(roomNumber);
        assertEquals(roomNumber, room.getRoomNumber());
    }

    @ParameterizedTest
    @MethodSource("randomInts")
    void testCapacity(Integer capacity) {
        room.setCapacity(capacity);
        assertEquals(capacity, room.getCapacity());
    }

    @ParameterizedTest
    @MethodSource("randomDoubles")
    void testPrice(Double price) {
        room.setPrice(price);
        assertEquals(price, room.getPrice());
    }

    @ParameterizedTest
    @MethodSource("randomInts")
    void testEquals(Integer roomNumber) {
        room = new Room(roomNumber, randomInt(), randomDouble(), getEquipmentTypes().get(0));
        Room clonedRoom = SerializationUtils.clone(room);
        assertEquals(room, clonedRoom);

        clonedRoom.setRoomNumber(randomInt());
        assertNotEquals(room, clonedRoom);

    }

    @Test
    void testGetEquipmentType() {
        EquipmentType equipmentType = getEquipmentTypes().get(0);
        room.setEquipmentType(equipmentType);

        assertEquals(equipmentType, room.getEquipmentType());
    }
}
