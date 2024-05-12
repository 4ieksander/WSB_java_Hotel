package pl.wsb.hotel.interfaces;

import pl.wsb.hotel.exceptions.ClientNotFoundException;
import pl.wsb.hotel.exceptions.ReservationNotFoundException;
import pl.wsb.hotel.exceptions.RoomNotFoundException;
import pl.wsb.hotel.exceptions.RoomReservedException;

import java.time.LocalDate;
import java.util.Collection;

public interface HotelCapability {

//    // clients //
        String addClient(String firstName, String lastName, LocalDate birthDate);
        String getClientFullName(String clientId) throws ClientNotFoundException; //dodane throws KONSULTACJE
        int getNumberOfUnderageClients();
//
//    // rooms //
        String addRoom(double area, int floor, boolean hasKingSizeBed, String description);
        double getRoomArea(String roomId) throws RoomNotFoundException; //dodane throws
        int getNumberOfRoomsWithKingSizeBed(int floor);
//
//    // reservations //
    String addNewReservation(String clientId, String roomId, LocalDate date) throws ClientNotFoundException, RoomNotFoundException, RoomReservedException; //dodane throws
//
    String confirmReservation(String reservationId) throws ReservationNotFoundException;
//
    boolean isRoomReserved(String roomId, LocalDate date) throws RoomNotFoundException;
//
    int getNumberOfUnconfirmedReservation(LocalDate date);
//
   Collection<String> getRoomIdsReservedByClient(String clientId) throws ClientNotFoundException; //dodane throws
}

