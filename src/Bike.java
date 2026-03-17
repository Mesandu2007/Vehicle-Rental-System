public class Bike  extends Vehicle {
    public Bike(int vehicleId, String brand, int year, double rentPerDay){
        super(vehicleId,brand,year,rentPerDay);

    }
    @Override
    public double calculateRent(int days){
        return getRentPerDay() * days;
    }

}
