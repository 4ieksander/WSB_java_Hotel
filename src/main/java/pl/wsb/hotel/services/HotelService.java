package pl.wsb.hotel.services;

import pl.wsb.hotel.exceptions.ClientNotFoundException;
import pl.wsb.hotel.exceptions.ReservationNotFoundException;
import pl.wsb.hotel.exceptions.RoomNotFoundException;
import pl.wsb.hotel.exceptions.RoomReservedException;
import pl.wsb.hotel.interfaces.HotelCapability;
import pl.wsb.hotel.models.Hotel;
import pl.wsb.hotel.models.Client;
import pl.wsb.hotel.models.Room;
import pl.wsb.hotel.models.RoomReservation;

import java.time.LocalDate;
import java.util.Map;
import java.util.OptionalInt;
import java.util.UUID;
import java.time.Period;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class HotelService implements HotelCapability{
    final Hotel hotel;

    public HotelService(Hotel hotel) {
        this.hotel = hotel;
    }


    // Public methods
    // Client
    public String addClient(Client client) {
        this.hotel.getClients().add(client);
        return client.getId();
    }
    //////////////////////////////
    //CLIENTS/////////////////////



    @Override
    public String addClient(String firstName, String lastName, LocalDate birthDate){
        String clientId = UUID.randomUUID().toString();
        Client newClient = new Client(clientId, birthDate, firstName, lastName,null, null, null);
        this.hotel.getClients().add(newClient);
        return clientId;
    }
    @Override
    public String getClientFullName(String clientId) throws ClientNotFoundException {
        for (Client client : this.hotel.getClients()) {
            if (client.getId().equals(clientId)) {
                return client.getFullName();
            }
        }
        throw  new ClientNotFoundException(clientId);
    }
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


    ///////////////////////////////////////////
    //rooms///
    @Override
    public String addRoom(double area, int floor, boolean hasKingSizeBed, String description){
        String roomId = UUID.randomUUID().toString();
        Room newRoom = new Room(roomId, null, area, floor,hasKingSizeBed,0,false,0);
        this.hotel.getRooms().put(this.findFirstAvailableRoomNumber(), newRoom);
        return roomId;
    }
    @Override
    public double getRoomArea(String roomId) throws RoomNotFoundException {
        for (Room room : this.hotel.getRooms().values()) {
            if (room.getId().equals(roomId)) {
                return room.getArea();
            }
        }
        throw new RoomNotFoundException("room not found");
    }

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



    // reservations ///
    //////////////////

    public Client getClientById(String clientId) {
        for (Client client : this.hotel.getClients()) {
            if (client.getId().equals(clientId)) {
                return client;
            }
        }
        return null;
    }



    // Room
    public void addRoom(Integer roomNumber, Room room) {
        this.hotel.getRooms().put(roomNumber, room);
    }

    public void addRoom(Room room) {
        Integer roomNumber = findFirstAvailableRoomNumber();
        this.hotel.getRooms().put(roomNumber, room);
    }

    public Room getRoomByNumber(int roomNumber) {
        return this.hotel.getRooms().get(roomNumber);
    }



    // Reservations
    public void addReservation(String reservationId, RoomReservation reservation) {
        this.hotel.getReservations().put(reservationId, reservation);
    }

    public RoomReservation getReservationById(String reservationId) {
        return this.hotel.getReservations().get(reservationId);
    }



    // Unnecessary
    // Methods to returning object from collections
    public SpecialService getSpecialServiceByName(String serviceName) {
        for (SpecialService service : this.hotel.getSpecialServices()) {
            if (service.getName().equals(serviceName)) {
                return service;
            }
        }
        return null; // Zwraca null, jeśli nie znajdzie usługi
    }

    // Methods to adding objects to collections
    public void addSpecialService(SpecialService service) {
        this.hotel.getSpecialServices().add(service);
    }



    // Internal methods
    protected Integer findFirstAvailableRoomNumber() {
        Map<Integer, Room> rooms = this.hotel.getRooms();
        if (rooms.isEmpty()) {
            return 1;
        }

        OptionalInt highestRoomNumber = rooms.keySet().stream().mapToInt(Integer::intValue).max();
        for (int i = 1; i <= highestRoomNumber.getAsInt() + 1; i++) {
            if (!rooms.containsKey(i)) {
                return i;
            }
        }
        return highestRoomNumber.getAsInt() + 1;
    }
}
