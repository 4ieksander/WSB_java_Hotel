package pl.wsb.hotel.services;

import pl.wsb.hotel.models.*;
import pl.wsb.hotel.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class HotelServiceOptionalMethodsTests {
    private HotelService service;

    private SpecialService specialService;
    private Client client;
    private String clientId;

    private Room room;
    private String roomId;

    private RoomReservation reservation;


    @BeforeEach
    void setUp() {
        service = new HotelService(new Hotel("TestHotel"));
        client = new Client("1", LocalDate.of(1990, 1, 1), "Test", "Client", "test@example.com", "123456789", "Test Address");
        room = new Room("123", "test",321, 1, true, 1, true, 111);
        reservation = new RoomReservation(LocalDate.now(), client, room);
        specialService = new LuggageService("testService");

        clientId= service.addClient(client.getFirstName(), client.getLastName(), client.getBirthDate());
        roomId = service.addRoom(room.getArea(), room.getFloor(), room.isHasKingSizeBed(), room.getDescription());
    }

    @Test
    void shouldAddSpecialServiceSuccessfully() {
        // when
        service.addSpecialService(specialService);

        // then
        assertEquals(specialService, service.getSpecialServiceByName("testService"), "Special service should be added and retrievable by name");
    }

    @Test
    void shouldReturnNullWhenSpecialServiceNotFound() {
        // when
        SpecialService foundService = service.getSpecialServiceByName("NonExistingService");

        // then
        assertNull(foundService, "Should return null for non-existing service");
    }

    @Test
    void shouldAddClientSuccessfully() {
        // given in @BeforeEach

        // when
        Client addedClient = service.getClientById(clientId);

        // then
        assertNotNull(addedClient, "Client should be added");
        assertEquals(client.getFirstName(), addedClient.getFirstName(), "First name should match");
        assertEquals(client.getLastName(), addedClient.getLastName(), "Last name should match");
        assertEquals(client.getBirthDate(), addedClient.getBirthDate(), "Birth date should match");
    }

    @Test
    void shouldAddRoomSuccessfully() {
        // given in @BeforeEach

        // when
        Room addedRoom = service.hotel.getRooms().get(roomId);

        // then
        assertNotNull(addedRoom, "Room should be added");
        assertEquals(room.getArea(), addedRoom.getArea(), "Room area should match");
        assertEquals(room.getFloor(), addedRoom.getFloor(), "Room floor should match");
        assertEquals(room.isHasKingSizeBed(), addedRoom.isHasKingSizeBed(), "Room bed type should match");
    }


    @Test
    void shouldAddReservationSuccessfully() {
        // given in @BeforeEach

        // when
        String reservationId = service.addReservation(reservation);

        // then
        RoomReservation addedReservation = service.getReservationById(reservationId);
        assertNotNull(addedReservation, "Reservation should be added");
        assertEquals(room, addedReservation.getRoom(), "Room should match");
        assertEquals(client, addedReservation.getClient(), "Client should match");
    }

    @Test
    void shouldGetReservationById() {
        // given
        String reservationId = "test_id";
        service.addReservation(reservationId, reservation);

        // when
        RoomReservation foundReservation = service.getReservationById(reservationId);

        // then
        assertNotNull(foundReservation, "Reservation should be found");
        assertEquals(reservation, foundReservation, "Reservation should match");
    }

    @Test
    void shouldNotFindNonExistingReservation() {
        // when
        RoomReservation foundReservation = service.getReservationById("nonExistingId");

        // then
        assertNull(foundReservation, "Reservation should not be found");
    }

    @Test
    void shouldNotAddRoomWithInvalidData() {
        // given invalid data
        double invalidRoomArea = -1.0;
        int invalidRoomFloor = -2;
        boolean invalidRoomBedType = false;
        String invalidRoomDescription = "";

        // when/then
        assertThrows(InvalidRoomDataException.class, () -> {
            service.addRoom(invalidRoomArea, invalidRoomFloor, invalidRoomBedType, invalidRoomDescription);
        }, "Should throw InvalidRoomDataException for invalid room data");
    }
}
