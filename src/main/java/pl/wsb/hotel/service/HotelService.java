package pl.wsb.hotel.service;

import pl.wsb.hotel.models.Hotel;
import pl.wsb.hotel.models.Client;
import pl.wsb.hotel.models.Room;
import pl.wsb.hotel.models.RoomReservation;

import java.util.Map;
import java.util.OptionalInt;


public class HotelService {
    private Hotel hotel;

    public HotelService(Hotel hotel) {
        this.hotel = hotel;
    }


    // Public methods

    // Methods to adding objects to collections
    public void addSpecialService(SpecialService service) {
        this.hotel.getSpecialServices().add(service);
    }

    public void addRoom(Integer roomNumber, Room room) {
        this.hotel.getRooms().put(roomNumber, room);
    }

    public void addRoom(Room room) {
        Integer roomNumber = findFirstAvailableRoomNumber();
        this.hotel.getRooms().put(roomNumber, room);
    }

    public void addClient(Client client) {
        this.hotel.getClients().add(client);
    }

    public void addReservation(String reservationId, RoomReservation reservation) {
        this.hotel.getReservations().put(reservationId, reservation);
    }


    // Methods to returning object from collections
    public SpecialService getSpecialServiceByName(String serviceName) {
        for (SpecialService service : this.hotel.getSpecialServices()) {
            if (service.getName().equals(serviceName)) {
                return service;
            }
        }
        return null; // Zwraca null, jeśli nie znajdzie usługi
    }

    public Room getRoomByNumber(int roomNumber) {
        return this.hotel.getRooms().get(roomNumber);
    }

    public Client getClientById(String clientId) {
        for (Client client : this.hotel.getClients()) {
            if (client.getId().equals(clientId)) {
                return client;
            }
        }
        return null;
    }

    public RoomReservation getReservationById(String reservationId) {
        return this.hotel.getReservations().get(reservationId);
    }


    // Internal methods
    private Integer findFirstAvailableRoomNumber() {
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
