package pl.wsb.hotel.services;

public class LuggageService extends SpecialService {
    public LuggageService(String name) {
        super(name);
    }

    public void orderService() {
        System.out.println("przechowywany bagaz klienta: " + getName());
    }

    public int calculateCost(int quantity, double unitPrice) {
        return quantity * (int) unitPrice;
    }

    public boolean highDemand() {
        return false;
    }
}