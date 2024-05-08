package pl.wsb.hotel.services;

import pl.wsb.hotel.models.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


class HotelServiceTest {
    private HotelService service;
    private String validClientId;
    private String validClientFirstName;
    private String validClientLastName;
    private LocalDate validClientDateOfBirth;


    @BeforeEach     // Przed każdym testem tworzy nową instancję
    void setUp() {
        service = new HotelService(new Hotel("HotelTest"));
        validClientFirstName = "TestFirstName";
        validClientLastName = "TestLastName";
        validClientDateOfBirth = LocalDate.of(1990, 1, 1);
        validClientId = service.addClient(validClientFirstName,validClientLastName,validClientDateOfBirth);
    }

    @Test
    void shouldAddClientSuccessfully() {
        // given in @BeforeEach

        // when
        String clientId = validClientId;

        // then
        Client addedClient = service.hotel.getClients().stream()
                .filter(c -> c.getId().equals(clientId))
                .findFirst()
                .orElse(null);

        assertNotNull(addedClient, "Client should be added");
        assertEquals(validClientFirstName, addedClient.getFirstName(), "First name should match");
        assertEquals(validClientLastName, addedClient.getLastName(), "Last name should match");
        assertEquals(validClientDateOfBirth, addedClient.getBirthDate(), "Birth date should match");
    }

    @Test
    void shouldHandleMultipleClients() {
        // given
        service.addClient("Anna", "Kowalska", LocalDate.of(1992, 2, 2));

        // when
        int numberOfClients = service.hotel.getClients().size();

        // then
        assertEquals(2, numberOfClients, "There should be two clients in the hotel");
    }




}
