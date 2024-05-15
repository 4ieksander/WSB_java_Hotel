package pl.wsb.hotel.services;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wsb.hotel.exceptions.ClientNotFoundException;
import pl.wsb.hotel.exceptions.RoomNotFoundException;
import pl.wsb.hotel.exceptions.RoomReservedException;
import pl.wsb.hotel.models.Hotel;

import java.time.LocalDate;
import java.util.Collection;

class HotelServiceReservationTests {
    private HotelService service;
    private String validClientId;
    private String validRoomId;
    private LocalDate reservationDate;

    @BeforeEach
    void setUp() {
        service = new HotelService(new Hotel("TestHotel"));
        validClientId = service.addClient("Jan", "Kowalski", LocalDate.of(1980, 1, 1));
        validRoomId = service.addRoom(20.0, 1, false, "Standard Room");
        reservationDate = LocalDate.now();
    }

    @Test
    void shouldAddNewReservationSuccessfully() throws Exception {
        // when
        String reservationId = service.addNewReservation(validClientId, validRoomId, reservationDate);

        // then
        assertNotNull(reservationId, "Reservation ID should not be null");
    }

    @Test
    void shouldThrowClientNotFoundExceptionWhenClientDoesNotExist() {
        Exception exception = assertThrows(ClientNotFoundException.class, () ->
                service.addNewReservation("nonexistentClientId", validRoomId, reservationDate)
        );

        assertTrue(exception.getMessage().contains("client id not found"));
    }

    @Test
    void shouldThrowRoomNotFoundExceptionWhenRoomDoesNotExist() {
        Exception exception = assertThrows(RoomNotFoundException.class, () ->
                service.addNewReservation(validClientId, "nonexistentRoomId", reservationDate)
        );

        assertTrue(exception.getMessage().contains("room id not found"));
    }

    @Test
    void shouldThrowRoomReservedExceptionWhenRoomIsAlreadyBooked() {
        service.addNewReservation(validClientId, validRoomId, reservationDate);
        Exception exception = assertThrows(RoomReservedException.class, () ->
                service.addNewReservation(validClientId, validRoomId, reservationDate)
        );

        assertTrue(exception.getMessage().contains("room is already reserved"));
    }

    @Test
    void shouldConfirmReservationSuccessfully() throws Exception {
        String reservationId = service.addNewReservation(validClientId, validRoomId, reservationDate);
        String confirmedId = service.confirmReservation(reservationId);

        assertEquals(reservationId, confirmedId, "Reservation should be confirmed correctly");
    }

    @Test
    void shouldReturnTrueIfRoomIsReserved() throws Exception {
        service.addNewReservation(validClientId, validRoomId, reservationDate);

        assertTrue(service.isRoomReserved(validRoomId, reservationDate), "Room should be marked as reserved");
    }

    @Test
    void shouldCountUnconfirmedReservationsCorrectly() throws Exception {
        service.addNewReservation(validClientId, validRoomId, reservationDate);
        service.addNewReservation(validClientId, validRoomId, reservationDate.plusDays(1)); // Another day

        assertEquals(2, service.getNumberOfUnconfirmedReservation(reservationDate), "Should count unconfirmed reservations correctly");
    }

    @Test
    void shouldReturnListOfRoomIdsReservedByClient() throws Exception {
        service.addNewReservation(validClientId, validRoomId, reservationDate);
        Collection<String> reservedRooms = service.getRoomIdsReservedByClient(validClientId);

        assertTrue(reservedRooms.contains(validRoomId), "Should return list containing room IDs reserved by client");
    }
}
