//package model;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.apache.commons.lang3.SerializationUtils;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//
//class ClientTest extends BasicModelTest {
//    private final Client client = new Client();
//
//    @ParameterizedTest
//    @MethodSource("randomStrings")
//    void testFirstName(String firstName) {
//        client.setFirstName(firstName);
//        assertEquals(firstName, client.getFirstName());
//    }
//
//    @ParameterizedTest
//    @MethodSource("randomStrings")
//    void testLastName(String lastName) {
//        client.setLastName(lastName);
//        assertEquals(lastName, client.getLastName());
//    }
//
//    @ParameterizedTest
//    @MethodSource("randomStrings")
//    void testPersonalID(String personalID) {
//        client.setPersonalID(personalID);
//        assertEquals(personalID, client.getPersonalID());
//    }
//
//    @ParameterizedTest
//    @MethodSource("randomStrings")
//    void testEquals(String personalID) {
//        client.setPersonalID(personalID);
//        Client clonedClient = SerializationUtils.clone(client);
//        assertEquals(client, clonedClient);
//
//        client.setFirstName(randomString());
//        assertEquals(client, clonedClient);
//
//        client.setPersonalID(randomString());
//        assertNotEquals(client, clonedClient);
//    }
//
//    @Test
//    void testAddress() {
//        Address address = new Address(randomString(), randomString(), randomString());
//        client.setAddress(address);
//        assertEquals(address, client.getAddress());
//    }
//
//    @Test
//    void testClientType() {
//        assertEquals(ClientType.STANDARD, client.getClientType());
//
//        client.setClientType(ClientType.PREMIUM);
//        assertEquals(ClientType.PREMIUM, client.getClientType());
//
//    }
//
////    @Test
////    void testArchive() {
////        assertFalse(client.isArchive());
////
////        client.setArchive(true);
////        assertTrue(client.isArchive());
////    }
//}