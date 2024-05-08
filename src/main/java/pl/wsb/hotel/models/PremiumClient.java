package pl.wsb.hotel.models;
import java.time.LocalDate;


public class PremiumClient extends Client {
    public enum PremiumAccountType {
        PREMIUM, PREMIUM_PLUS
    }
    private PremiumAccountType premiumAccountType;
    public PremiumClient(String id, LocalDate birthDate, String firstName, String lastName, String email, String phoneNumber, String address, PremiumAccountType premiumAccountType) {
        super(id, birthDate, firstName, lastName, email, phoneNumber, address);
        this.premiumAccountType = premiumAccountType;
    }
    public String getFullName(){
        if (this.premiumAccountType == PremiumAccountType.PREMIUM) {
            return "[premium] " + super.getFullName();
    }   else if (this.premiumAccountType == PremiumAccountType.PREMIUM_PLUS) {
        return "[premium_plus] " + super.getFullName();
    }
        return super.getFullName();
    }

    public void setPremiumAccountType(PremiumAccountType premiumAccountType) {
        this.premiumAccountType = premiumAccountType;
    }

    public PremiumAccountType getPremiumAccountType() {
        return premiumAccountType;
    }

}
