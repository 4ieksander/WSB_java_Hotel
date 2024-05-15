package pl.wsb.hotel.services;

import pl.wsb.hotel.exceptions.ClientNotFoundException;
import pl.wsb.hotel.exceptions.ReservationNotFoundException;
import pl.wsb.hotel.exceptions.RoomNotFoundException;
import pl.wsb.hotel.exceptions.RoomReservedException;
import pl.wsb.hotel.exceptions.InvalidRoomDataException;
import pl.wsb.hotel.interfaces.HotelCapability;
import pl.wsb.hotel.models.Hotel;
import pl.wsb.hotel.models.Client;
import pl.wsb.hotel.models.Room;
import pl.wsb.hotel.models.RoomReservation;

import java.time.LocalDate;
import java.util.UUID;
import java.util.*;
import java.util.stream.Collectors;

public class HotelService implements HotelCapability{
    final Hotel hotel;

    public HotelService(Hotel hotel) {
        this.hotel = hotel;
    }

    // overriden methods from interface
    // clients //
    // 1
    @Override
    public String addClient(String firstName, String lastName, LocalDate birthDate){
        String clientId = UUID.randomUUID().toString();
        Client newClient = new Client(clientId, birthDate, firstName, lastName,null, null, null);
        return  addClient(newClient);
    }

    // 2
    @Override
    public String getClientFullName(String clientId) {
        for (Client client : this.hotel.getClients()) {
            if (client.getId().equals(clientId)) {
                return client.getFullName();
            }
        }
        throw  new ClientNotFoundException(clientId);
    }

    // 3
    @Override
    public int getNumberOfUnderageClients() {
        int count = 0;
        for (Client client : this.hotel.getClients()) {
            if (client.getAge() < 18) {
                count++;
            }
        }
        return count;
    }

    // rooms //
    // 4
    @Override
    public String addRoom(double area, int floor, boolean hasKingSizeBed, String description) {
        if (area <= 0 || floor < 0 || description == null || description.isEmpty()) {
            throw new InvalidRoomDataException("Invalid room data provided.");
        }
        String roomId = UUID.randomUUID().toString();
        Room newRoom = new Room(roomId, null, area, floor, hasKingSizeBed, 0, false, 0);
        this.hotel.getRooms().put(roomId, newRoom);
        return roomId;
    }

    // 5
    @Override
    public double getRoomArea(String roomId) {
        for (Room room : this.hotel.getRooms().values()) {
            if (room.getId().equals(roomId)) {
                return room.getArea();
            }
        }
        throw new RoomNotFoundException("room not found");
    }

    // 6
    @Override
    public int getNumberOfRoomsWithKingSizeBed(int floor) {
        int counter = 0;
        for (Room room : this.hotel.getRooms().values()) {
            if (room.getFloor() == floor && room.isHasKingSizeBed()) {
                counter++;
            }
        }
        return counter;
    }

    // reservations //
    // 7
    @Override
    public String addNewReservation(String clientId, String roomId, LocalDate date) {
        Client client = this.hotel.getClients().stream()
                .filter(c -> c.getId().equals(clientId))
                .findFirst()
                .orElse(null);
        if (client == null) {
            throw new ClientNotFoundException("client id not found: " + clientId);
        }

        Room room = this.hotel.getRooms().get(roomId); //tutaj jest error
        if (room == null) {
            throw new RoomNotFoundException("room id not found: " + roomId);
        }
        for (RoomReservation reservation : this.hotel.getReservations().values()) {
            if (reservation.getRoom().getId().equals(roomId) && reservation.getDate().equals(date)) {
                throw new RoomReservedException(roomId, date);
            }
        }
        String reservationId = UUID.randomUUID().toString();
        RoomReservation newReservation = new RoomReservation(date, client, room);
        this.hotel.getReservations().put(reservationId, newReservation);
        return reservationId;
    }

    // 8
    @Override
    public String confirmReservation(String reservationId) {
        RoomReservation reservation = this.hotel.getReservations().get(reservationId);
        if (reservation == null) {
            throw new ReservationNotFoundException("reservation not found " + reservationId);
        }
        reservation.confirmReservation();
        return reservationId;
    }

    // 9
    @Override
    public boolean isRoomReserved(String roomId, LocalDate date) {
        Room room = this.hotel.getRooms().get(roomId);
        if (room == null) {
            throw new RoomNotFoundException("room not found with id: " + roomId);
        }
        return this.hotel.getReservations().values().stream()
                .anyMatch(reservation -> reservation.getRoom().getId().equals(roomId) && reservation.getDate().equals(date));
    }

    // 10
    @Override
    public int getNumberOfUnconfirmedReservation(LocalDate date) {
        return (int) this.hotel.getReservations().values().stream()
                .filter(reservation -> !reservation.isConfirmed() && reservation.getDate().equals(date))
                .count();
    }

    // 11
    @Override
    public Collection<String> getRoomIdsReservedByClient(String clientId) {
        if (this.hotel.getClients().stream().noneMatch(client -> client.getId().equals(clientId))) {
            throw new ClientNotFoundException("client not found " + clientId);
        }
        return this.hotel.getReservations().values().stream()
                .filter(reservation -> reservation.getClient().getId().equals(clientId))
                .map(reservation -> reservation.getRoom().getId())
                .collect(Collectors.toSet());
    }

    //////////////////////////////
    // Additional methods
    // clients //
    public String addClient(Client client) {
        this.hotel.getClients().add(client);
        return client.getId();
    }

    public Client getClientById(String clientId) {
        for (Client client : this.hotel.getClients()) {
            if (client.getId().equals(clientId)) {
                return client;
            }
        }
        return null;
    }

    // Reservations
    public void addReservation(String reservationId, RoomReservation reservation) {
        this.hotel.getReservations().put(reservationId, reservation);
    }

    public String addReservation(RoomReservation reservation) {
        String reservationId = reservation.getDate().toString() + "-" + reservation.getClient().getFullName();
        this.hotel.getReservations().put(reservationId, reservation);
        return reservationId;
    }

    public RoomReservation getReservationById(String reservationId) {
        return this.hotel.getReservations().get(reservationId);
    }

    public SpecialService getSpecialServiceByName(String serviceName) {
        for (SpecialService service : this.hotel.getSpecialServices()) {
            if (service.getName().equals(serviceName)) {
                return service;
            }
        }
        return null;
    }

    public void addSpecialService(SpecialService service) {
        this.hotel.getSpecialServices().add(service);
    }
}

