package pl.wsb.hotel;
import pl.wsb.hotel.exceptions.ClientNotFoundException;
import pl.wsb.hotel.exceptions.RoomNotFoundException;
import pl.wsb.hotel.exceptions.RoomReservedException;
import pl.wsb.hotel.exceptions.ReservationNotFoundException;
import pl.wsb.hotel.models.*;
import pl.wsb.hotel.services.HotelService;
import pl.wsb.hotel.models.PremiumClient;
import pl.wsb.hotel.models.PremiumClient.PremiumAccountType;
import java.time.LocalDate;
import java.util.Collection;

import pl.wsb.hotel.services.TimeService;
import pl.wsb.hotel.services.LuggageService;

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
//            public String addClient(String firstName, String lastName, LocalDate.of(2000, 4, 5){
            String id_klienta = hotelService.addClient("losoweimie_task3", "losowenazwisko_task3", LocalDate.of(2010, 4, 5));
            out.println("id klienta: " + id_klienta);
            try {
                out.println(hotelService.getClientFullName(id_klienta));
            } catch (ClientNotFoundException e) {
                throw new RuntimeException(e);
            }
            out.println(hotelService.getNumberOfUnderageClients());

            String id_pokoju = hotelService.addRoom(300,2,true,"pokój z balkonem :)");

            out.println("id pokoju: " + id_pokoju);


            try {
                out.println(hotelService.getRoomArea(id_pokoju));
            } catch (RoomNotFoundException e) {
                throw new RuntimeException(e);
            }
            out.println(hotelService.getNumberOfRoomsWithKingSizeBed(1));

            //hotelService.addReservation("Rezerwacja1", reservation);
            //hotelService.addRoom(room);

            out.println(hotelService);
            out.println(hotel.getRooms());


            String reservationId = null;
            try {
                reservationId = hotelService.addNewReservation(id_klienta, id_pokoju, LocalDate.now());
                System.out.println( "rezerwacja o id: " + reservationId + " dodana");
            } catch (ClientNotFoundException | RoomNotFoundException | RoomReservedException e) {
                System.err.println("rezerwcaja nie dodana " + e.getMessage());
            }


            try {
                String confirmedReservationId = hotelService.confirmReservation(reservationId);
                System.out.println("rezerwacja potwierdzona " + confirmedReservationId);
            } catch (ReservationNotFoundException e) {
                System.err.println("rezerwacja nieudana " + e.getMessage());
            }

            LocalDate reservationDate = LocalDate.now();
            try {
                boolean isReserved = hotelService.isRoomReserved(id_pokoju, reservationDate);
                System.out.println("pokoj " + id_pokoju + " zarezerwowany " + reservationDate + ": " + isReserved);
            } catch (RoomNotFoundException | RoomReservedException e) {
                System.err.println("blad " + e.getMessage());
            }

            int unconfirmedReservations = hotelService.getNumberOfUnconfirmedReservation(LocalDate.now());
            System.out.println("niepotwierdzone rezerwacje: " + unconfirmedReservations);

            try {
                Collection<String> reservedRooms = hotelService.getRoomIdsReservedByClient(id_klienta);
                System.out.println("klient: " + id_klienta + " zarezerował takie pokoje:  " + reservedRooms);
            } catch (ClientNotFoundException e) {
                System.err.println("blad: " + e.getMessage());
            }
        }

    }


