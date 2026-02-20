public abstract class Vehicle {
    private int vehicleId;
    private String brand;
    private double rentPerDay;
    private boolean available;
    private int year;

    public Vehicle(int vehicleId,String brand, double rentPerDay){
        this.vehicleId=vehicleId;
        this.brand=brand;
        this.year=year;
        this.rentPerDay=rentPerDay;
        this.available=true;

    }
    public int getVehicleId(){
        return vehicleId;
    }
    public String getBrand(){
        return brand;

    }
    public double getRentPerDay(){
        return rentPerDay;
    }
    public boolean isAvaialable(){
        return available;
    }
    public void setVehicleId(int vehicleId){
        this.vehicleId=vehicleId;
    }
    public void setBrand(String brand){
        this.brand=brand;
    }
    public void setRentPerDay(double rentPerDay){
        this.rentPerDay=rentPerDay;
    }
    public void setYear(int year){
        this.year=year;
    }
    public void setAvailable(boolean available){
        this.available=available;
    }
    public abstract double calculateRent(int days);


    public void displayInfo(){
        System.out.println("ID: " + vehicleId +
                ", Brand: " + brand +
                ", Rent per Day: $" + rentPerDay +
                ", Available: " + available);





    }



}
