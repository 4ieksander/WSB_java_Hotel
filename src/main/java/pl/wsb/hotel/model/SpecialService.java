package pl.wsb.hotel.model;

 public abstract class SpecialService {

    private String name;

    public SpecialService(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public abstract void orderService();
    public abstract int calculateCost(int quantity, double unitPrice);
    public abstract boolean highDemand();
}

