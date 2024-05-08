package pl.wsb.hotel.services;

import pl.wsb.hotel.models.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


class HotelServiceTest {
    private final HotelService service = new HotelService(new Hotel("HotelTest"));

    @Test
    void shouldAddClientSuccessfully() {
        // given
        String firstName = "Jan";
        String lastName = "Nowak";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);

        // when
        String clientId = service.addClient(firstName, lastName, birthDate);

        // then
        Client addedClient = service.hotel.getClients().stream()
                .filter(c -> c.getId().equals(clientId))
                .findFirst()
                .orElse(null);

        assertNotNull(addedClient, "Client should be added");
        assertEquals(firstName, addedClient.getFirstName(), "First name should match");
        assertEquals(lastName, addedClient.getLastName(), "Last name should match");
        assertEquals(birthDate, addedClient.getBirthDate(), "Birth date should match");
    }

    @Test
    void shouldHandleMultipleClients() {
        // given
        service.addClient("Jan", "Nowak", LocalDate.of(1990, 1, 1));
        service.addClient("Anna", "Kowalska", LocalDate.of(1992, 2, 2));

        // when
        int numberOfClients = service.hotel.getClients().size();

        // then
        assertEquals(2, numberOfClients, "There should be two clients in the hotel");
    }
}
