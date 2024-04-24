package pl.wsb.hotel.model;

import pl.wsb.hotel.service.SpecialService;
import java.util.*;

public class Hotel {
    private String name;
    private Set<SpecialService> specialServices = new HashSet<>();
    private Map<Integer, Room> rooms = new HashMap<>();
    private List<Client> clients = new ArrayList<>();
    private Map<String, RoomReservation> reservations = new HashMap<>();


    // Constructors
    public Hotel(String name, Set<SpecialService> specialServices, Map<Integer, Room> rooms, List<Client> clients) {
        this.name = name;
        this.clients = clients;
        this.specialServices = specialServices;
        this.rooms = rooms;
    }

    public Hotel(String name, Set<SpecialService> specialServices, Map<Integer, Room> rooms) {
        this.name = name;
        this.specialServices = specialServices;
        this.rooms = rooms;
    }

    public Hotel(String name, Map<Integer, Room> rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    public Hotel(String name) {
        this.name = name;
    }


    // Getters and Setters
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

    public Map<Integer, Room> getRooms() {
        return rooms;
    }

    public void setRooms(Map<Integer, Room> rooms) {
        this.rooms = rooms;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Map<String, RoomReservation> getReservations() {
        return reservations;
    }

    public void setReservations(Map<String, RoomReservation> reservations) {
        this.reservations = reservations;
    }
}
