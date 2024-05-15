package pl.wsb.hotel.exceptions;

public class RoomNotFoundException extends RuntimeException {   // Change the extend class type to RuntimeException from Exception

    public RoomNotFoundException(String message) {
        super(message);
    }
}
