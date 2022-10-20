package model;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class AddressTest extends BasicModelTest {

    private Address address = new Address();

    @ParameterizedTest
    @MethodSource("randomStrings")
    void testCity(String city) {
        address.setCity(city);
        assertEquals(city, address.getCity());
    }

    @ParameterizedTest
    @MethodSource("randomStrings")
    void testStreet(String street) {
        address.setStreet(street);
        assertEquals(street, address.getStreet());
    }

    @ParameterizedTest
    @MethodSource("randomStrings")
    void getNumber(String number) {
        address.setNumber(number);
        assertEquals(number, address.getNumber());
    }

    @Test
    void testEquals() {
        address = new Address(randomString(), randomString(), randomString());
        Address clonedAddress = SerializationUtils.clone(address);
        assertEquals(address, clonedAddress);

        clonedAddress.setNumber(randomString());
        assertNotEquals(address, clonedAddress);
    }
}