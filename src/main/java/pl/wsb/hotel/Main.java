package pl.wsb.hotel;
import pl.wsb.hotel.model.*;
import pl.wsb.hotel.service.HotelService;
import pl.wsb.hotel.service.SpecialService;

import java.time.LocalDate;
import java.util.*;

import static java.lang.System.out;

public class Main {

        public static void main(String[] args) {

            Client client = new Client("1", LocalDate.of(2000, 4, 5), "Jan", "Kowalski", "jan@jan.com", "123456789", "Wrocław");
            Room room = new Room("201", "dirty",20.5, 2, true, 2, true, 95);

            String client_id = client.getId();
            String client_name = client.getFullName();
            int client_age = client.getAge();
            System.out.println("Klient o ID " + client_id + " ma " + client_age + " lat, a nazywa się " + client_name);


            RoomReservation reservation = new RoomReservation(LocalDate.now(), client, room);

            System.out.println("Rezerwacja potwierdzona: " + reservation.isConfirmed());
            reservation.confirmReservation();
            System.out.println("Rezerwacja potwierdzona: " + reservation.isConfirmed());

            System.out.println(reservation.getRoom().getPrice());
            room.setPrice(100);
            System.out.println(reservation.getRoom().getPrice());


            Hotel hotel = new Hotel("Hotel Testowy");
            HotelService hotelService = new HotelService(hotel);
            hotelService.addClient(client);
            hotelService.addReservation("Rezerwacja1", reservation);
            hotelService.addRoom(room);

            out.println(hotelService);
            out.println(hotel.getRooms());

        }
    }


