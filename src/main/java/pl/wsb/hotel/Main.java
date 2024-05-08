package pl.wsb.hotel;
import pl.wsb.hotel.models.*;
import pl.wsb.hotel.services.HotelService;
import pl.wsb.hotel.models.PremiumClient;
import pl.wsb.hotel.models.PremiumClient.PremiumAccountType;
import java.time.LocalDate;
import pl.wsb.hotel.models.TimeService;
import pl.wsb.hotel.models.LuggageService;

import static java.lang.System.out;

public class Main {

        public static void main(String[] args) {

            Client client = new Client("1", LocalDate.of(2000, 4, 5), "Jan", "Kowalski", "jan@jan.com", "123456789", "Wrocław");
            Room room = new Room("201", "dirty",20.5, 2, true, 2, true, 95);

            String client_id = client.getId();
            String client_name = client.getFullName();
            int client_age = client.getAge();
            System.out.println("Klient o ID " + client_id + " ma " + client_age + " lat, a nazywa się " + client_name);

            PremiumClient premiumClient = new PremiumClient("2", LocalDate.of(1985, 10, 15), "Anna", "Nowak", "anna@nowak.com", "987654321", "Kraków", PremiumAccountType.PREMIUM);
            PremiumClient premiumPlusClient = new PremiumClient("3", LocalDate.of(1975, 10, 15), "barbara", "testowa", "basia@nowak.com", "333333333", "sieradz", PremiumAccountType.PREMIUM_PLUS);

            String premiumClientFullName = premiumClient.getFullName();
            String premiumPlusClientFullName = premiumPlusClient.getFullName();

            out.println(premiumClientFullName);
            out.println(premiumPlusClientFullName);

            RoomReservation reservation = new RoomReservation(LocalDate.now(), client, room);

            System.out.println("Rezerwacja potwierdzona: " + reservation.isConfirmed());
            reservation.confirmReservation();
            System.out.println("Rezerwacja potwierdzona: " + reservation.isConfirmed());

            System.out.println(reservation.getRoom().getPrice());
            room.setPrice(100);
            System.out.println(reservation.getRoom().getPrice());

            TimeService timeService = new TimeService(client_name);
            LuggageService luggageService = new LuggageService(client_name);

            timeService.orderService();
            luggageService.orderService();


            Hotel hotel = new Hotel("Hotel Testowy");
            HotelService hotelService = new HotelService(hotel);
            hotelService.addClient(client);
            hotelService.addReservation("Rezerwacja1", reservation);
            hotelService.addRoom(room);

            out.println(hotelService);
            out.println(hotel.getRooms());

        }
    }


