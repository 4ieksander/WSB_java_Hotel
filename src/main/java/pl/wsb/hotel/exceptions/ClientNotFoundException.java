package pl.wsb.hotel.exceptions;

public class ClientNotFoundException extends RuntimeException { // Change the extend class type to RuntimeException from Exception
    public ClientNotFoundException(String message) {
        super(message);
    }
}
