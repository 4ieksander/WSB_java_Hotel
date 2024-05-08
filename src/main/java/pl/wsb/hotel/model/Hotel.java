package pl.wsb.hotel.model;

import pl.wsb.hotel.exceptions.*;

import pl.wsb.hotel.service.SpecialService;
import java.time.Period;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import pl.wsb.hotel.exceptions.ClientNotFoundException;
import pl.wsb.hotel.exceptions.RoomNotFoundException;
import pl.wsb.hotel.exceptions.RoomReservedException;
import pl.wsb.hotel.exceptions.ReservationNotFoundException;



public class Hotel implements HotelCapability {
    private String name;
    private Set<SpecialService> specialServices = new HashSet<>();
    private Map<String, Room> rooms = new HashMap<>();
    private Map<String, Client> clients = new HashMap<>();
    private Map<String, RoomReservation> reservations = new HashMap<>();

    public Hotel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SpecialService> getSpecialServices() {
        return specialServices;
    }

    public void setSpecialServices(Set<SpecialService> specialServices) {
        this.specialServices = specialServices;
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, Room> rooms) {
        this.rooms = rooms;
    }

    public Map<String, Client> getClients() {
        return clients;
    }

    public void setClients(Map<String, Client> clients) {
        this.clients = clients;
    }

    public Map<String, RoomReservation> getReservations() {
        return reservations;
    }

    public void setReservations(Map<String, RoomReservation> reservations) {
        this.reservations = reservations;
    }

    public String addClient(String firstName, String lastName, LocalDate birthDate) {
        String clientId = UUID.randomUUID().toString();
        clients.put(clientId, new Client(clientId, birthDate, firstName, lastName, null, null, null));
        return clientId;
    }

    public String getClientFullName(String clientId) throws ClientNotFoundException {
        if (!clients.containsKey(clientId)) {
            throw new ClientNotFoundException("Client not found: " + clientId);
        }
        return clients.get(clientId).getFullName();
    }

    public int getNumberOfUnderageClients() {
        return (int) clients.values().stream()
                .filter(client -> Period.between(client.getBirthDate(), LocalDate.now()).getYears() < 18)
                .count();
    }

    public String addRoom(double area, int floor, boolean hasKingSizeBed, String description) {
        String roomId = UUID.randomUUID().toString();
        rooms.put(roomId, new Room(roomId, description, area, floor, hasKingSizeBed, 0, false, 0.0));
        return roomId;
    }

    public double getRoomArea(String roomId) throws RoomNotFoundException {
        if (!rooms.containsKey(roomId)) {
            throw new RoomNotFoundException("Room not found: " + roomId);
        }
        return rooms.get(roomId).getArea();
    }

    public int getNumberOfRoomsWithKingSizeBed(int floor) {
        return (int) rooms.values().stream()
                .filter(room -> room.getFloor() == floor && room.isHasKingSizeBed())
                .count();
    }

    public String addNewReservation(String clientId, String roomId, LocalDate date) throws ClientNotFoundException, RoomNotFoundException, RoomReservedException {
        if (!clients.containsKey(clientId)) {
            throw new ClientNotFoundException("Client not found: " + clientId);
        }
        if (!rooms.containsKey(roomId)) {
            throw new RoomNotFoundException("Room not found: " + roomId);
        }
        if (reservations.values().stream().anyMatch(reservation -> reservation.getRoom().getId().equals(roomId) && reservation.getDate().equals(date))) {
            throw new RoomReservedException(roomId, date);
        }
        String reservationId = UUID.randomUUID().toString();
        reservations.put(reservationId, new RoomReservation(date, clients.get(clientId), rooms.get(roomId)));
        return reservationId;
    }

    public String confirmReservation(String reservationId) throws ReservationNotFoundException {
        if (!reservations.containsKey(reservationId)) {
            throw new ReservationNotFoundException("Reservation not found: " + reservationId);
        }
        reservations.get(reservationId).confirmReservation();
        return reservationId;
    }

    public boolean isRoomReserved(String roomId, LocalDate date) throws RoomNotFoundException {
        if (!rooms.containsKey(roomId)) {
            throw new RoomNotFoundException("Room not found: " + roomId);
        }
        return reservations.values().stream()
                .anyMatch(reservation -> reservation.getRoom().getId().equals(roomId) && reservation.getDate().equals(date));
    }

    public int getNumberOfUnconfirmedReservation(LocalDate date) {
        return (int) reservations.values().stream()
                .filter(reservation -> reservation.getDate().equals(date) && !reservation.isConfirmed())
                .count();
    }

    public Collection<String> getRoomIdsReservedByClient(String clientId) throws ClientNotFoundException {
        if (!clients.containsKey(clientId)) {
            throw new ClientNotFoundException("Client not found: " + clientId);
        }
        return reservations.values().stream()
                .filter(reservation -> reservation.getClient().getId().equals(clientId))
                .map(reservation -> reservation.getRoom().getId())
                .collect(Collectors.toList());
    }
}
