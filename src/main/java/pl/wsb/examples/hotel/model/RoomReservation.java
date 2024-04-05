package pl.wsb.examples.hotel.model;
import java.time.LocalDate;

public class RoomReservation {
    private LocalDate date;
    private boolean isConfirmed;
    private Client client;
    private Room room;


    public RoomReservation(LocalDate date, Client client, Room room) {
        this.date = date;
        this.client = client;
        this.room = room;
        this.isConfirmed = false;
        System.out.println(client.getFullName() + " zarezerwował pokój nr " + room.getId() + ".");
    }

    public void confirmReservation() {
        this.isConfirmed = true;
        System.out.println("Klient " + client.getFullName() + " właśnie potwierdził rezerwację na dzień " + this.date.toString());
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
