package pl.wsb.hotel.model;

import pl.wsb.hotel.service.SpecialService;
import java.util.*;

public class Hotel {
    private String name;
    private Set<SpecialService> specialServices;
    private Map<Integer, Room> rooms;
    private List<Client> clients;
    private List<RoomReservation> reservations;


    public Hotel(String name, Set<SpecialService> specialServices, Map<Integer, Room> rooms, List<Client> clients) {
        this.name = name;
        this.clients = clients;
        this.specialServices = specialServices;
        this.rooms = rooms;
        reservations = new ArrayList<>();

    }

    public Hotel(String name, Set<SpecialService> specialServices, Map<Integer, Room> rooms) {
        this.name = name;
        this.specialServices = specialServices;
        this.rooms = rooms;
        clients = new ArrayList<>();
        reservations = new ArrayList<>();
    }


    public Hotel(String name) {
        this.name = name;
        specialServices = new HashSet<>();
        clients = new ArrayList<>();
        rooms = new HashMap<>();
        reservations = new ArrayList<>();
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

    public List<RoomReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<RoomReservation> reservations) {
        this.reservations = reservations;
    }
}
