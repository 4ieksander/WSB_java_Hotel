package pl.wsb.hotel.services;

import pl.wsb.hotel.models.*;
import pl.wsb.hotel.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HotelServiceRoomsTest {
    private HotelService service;

    @BeforeEach
    void setUp() {
        service = new HotelService(new Hotel("HotelTest"));
    }

    @Test
    void shouldAddRoomAndReturnValidRoomId() {
        // when
        String roomId = service.addRoom(20.0, 2, true, "ExampleDesc");

        // then
        assertNotNull(roomId, "Room ID should not be null");
        assertTrue(service.hotel.getRooms().containsKey(roomId));
    }

    @Test
    void shouldReturnCorrectRoomArea() throws RoomNotFoundException {
        // given
        String roomId = service.addRoom(30.5, 1, false, "ExampleDesc");

        // when
        double area = service.getRoomArea(roomId);

        // then
        assertEquals(30.5, area, "Room area should match the expected value");
    }

    @Test
    void shouldThrowRoomNotFoundExceptionWhenRoomDoesNotExist() {
        // when & then
        assertThrows(RoomNotFoundException.class, () -> {
            service.getRoomArea("NotExistentId");
        }, "Should throw RoomNotFoundException for non-existent room");
    }

    @Test
    void shouldCountRoomsWithKingSizeBedOnSpecificFloor() {
        // given
        service.addRoom(25.0, 3, true, " ");
        service.addRoom(18.0, 3, true, " ");
        service.addRoom(20.0, 3, false, " ");
        service.addRoom(22.0, 4, true, " ");

        // when
        int count = service.getNumberOfRoomsWithKingSizeBed(3);

        // then
        assertEquals(2, count, "Should count rooms with king-size bed on floor 3 correctly");
    }
    @Test
    void shouldThrowInvalidRoomDataExceptionWhenDescriptionIsEmptyString(){
        // when & then
        assertThrows(InvalidRoomDataException.class, () -> {
            service.addRoom(25.0, 3, true, "");
        }, "Should throw RoomNotFoundException for non-existent room");
    }
}
