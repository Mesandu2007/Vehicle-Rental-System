import java.time.LocalDate;

public class Rental {
    private int rentalId;
    private int vehicleId;
    private String customerName;
    private LocalDate rentDate;
    private LocalDate returnDate; // null if not returned

    // Constructor for new rental
    public Rental(int vehicleId, String customerName, LocalDate rentDate) {
        this.vehicleId = vehicleId;
        this.customerName = customerName;
        this.rentDate = rentDate;
        this.returnDate = null;
    }

    // Constructor for loading from DB
    public Rental(int rentalId, int vehicleId, String customerName, LocalDate rentDate, LocalDate returnDate) {
        this.rentalId = rentalId;
        this.vehicleId = vehicleId;
        this.customerName = customerName;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }

    // Getters and Setters
    public int getRentalId() { return rentalId; }
    public int getVehicleId() { return vehicleId; }
    public String getCustomerName() { return customerName; }
    public LocalDate getRentDate() { return rentDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    // Check if rental is active
    public boolean isActive() {
        return returnDate == null;
    }

    // Display rental info
    public void displayInfo() {
        System.out.println("Customer: " + customerName);
        System.out.println("Rent Date: " + rentDate);
        System.out.println("Return Date: " + (returnDate != null ? returnDate : "Not returned yet"));
    }
}