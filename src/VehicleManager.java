import java.util.*;
import java.sql.*;
import java.time.LocalDate;
import java.sql.Date;
import java.time.LocalDate;

public class VehicleManager {

    private List<Vehicle> vehicles;
    private List<Rental> rentals;

    public VehicleManager() {
        vehicles = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    // Bubble Sort by Vehicle ID
    private void sortVehicles() {
        int n = vehicles.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (vehicles.get(j).getVehicleId() > vehicles.get(j + 1).getVehicleId()) {
                    Vehicle temp = vehicles.get(j);
                    vehicles.set(j, vehicles.get(j + 1));
                    vehicles.set(j + 1, temp);
                }
            }
        }
    }

    // Binary Search
    public Vehicle binarySearchVehicle(int id) {
        sortVehicles();
        int left = 0;
        int right = vehicles.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int midId = vehicles.get(mid).getVehicleId();
            if (midId == id) return vehicles.get(mid);
            else if (midId < id) left = mid + 1;
            else right = mid - 1;
        }
        return null;
    }

    // Add Vehicle
    public void addVehicle(Vehicle v) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO vehicles(vehicle_id,type,brand,year,rent_per_day,available) VALUES(?,?,?,?,?,?)")) {
            ps.setInt(1, v.getVehicleId());
            ps.setString(2, v.getClass().getSimpleName());
            ps.setString(3, v.getBrand());
            ps.setInt(4, v.getYear());
            ps.setDouble(5, v.getRentPerDay());
            ps.setBoolean(6, v.isAvailable());
            ps.executeUpdate();
            vehicles.add(v);
            System.out.println("Vehicle added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Display All Vehicles
    public void displayAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available");
            return;
        }
        for (Vehicle v : vehicles) {
            v.displayInfo();
            Rental activeRental = getActiveRental(v.getVehicleId());
            if (activeRental != null) {
                System.out.println("Currently rented by: " + activeRental.getCustomerName());
                System.out.println("Rent Date: " + activeRental.getRentDate());
            } else {
                System.out.println("Available for rent");
            }
            System.out.println("---------------------------");
        }
    }

    // Search Vehicle
    public void searchVehicleById(int id) {
        Vehicle v = binarySearchVehicle(id);
        if (v == null) {
            System.out.println("Vehicle not found");
            return;
        }
        v.displayInfo();
        Rental activeRental = getActiveRental(id);
        if (activeRental != null) {
            System.out.println("Currently rented by: " + activeRental.getCustomerName());
            System.out.println("Rent Date: " + activeRental.getRentDate());
        } else {
            System.out.println("Available for rent");
        }
    }

    // Rent Vehicle
    public void rentVehicle(int id, String customerName, int days) {
        Vehicle v = binarySearchVehicle(id);
        if (v == null) {
            System.out.println("Vehicle not found");
            return;
        }
        if (!v.isAvailable()) {
            System.out.println("Vehicle already rented");
            return;
        }
        double total = v.calculateRent(days);
        v.setAvailable(false);
        updateAvailability(id, false);

        Rental rental = new Rental(id, customerName, LocalDate.now());
        rentals.add(rental);

        // Insert rental into DB
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO rentals(vehicle_id, customer_name, rent_date) VALUES(?,?,?)")) {
            ps.setInt(1, id);
            ps.setString(2, customerName);
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Vehicle rented successfully");
        System.out.println("Total rent: $" + total);
    }

    // Return Vehicle
    public void returnVehicle(int id) {
        Vehicle v = binarySearchVehicle(id);
        if (v == null) {
            System.out.println("Vehicle not found");
            return;
        }
        if (v.isAvailable()) {
            System.out.println("Vehicle is not currently rented");
            return;
        }

        v.setAvailable(true);
        updateAvailability(id, true);

        Rental rental = getActiveRental(id);
        if (rental != null) {
            rental.setReturnDate(LocalDate.now());
        }

        // Update rental in DB
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE rentals SET return_date=? WHERE vehicle_id=? AND return_date IS NULL")) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Vehicle returned successfully");
    }

    // Remove Vehicle
    public void removeVehicle(int id) {
        Vehicle v = binarySearchVehicle(id);
        if (v == null) {
            System.out.println("Vehicle not found!");
            return;
        }
        vehicles.remove(v);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM vehicles WHERE vehicle_id=?")) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Vehicle removed successfully");
            else System.out.println("Vehicle not found in database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Vehicle Statistics
    public void vehicleStatistics() {
        int rented = 0;
        int available = 0;
        for (Vehicle v : vehicles) {
            if (v.isAvailable()) available++;
            else rented++;
        }
        System.out.println("Total Vehicles: " + vehicles.size());
        System.out.println("Available Vehicles: " + available);
        System.out.println("Rented Vehicles: " + rented);
    }

    // Update availability in DB
    private void updateAvailability(int id, boolean status) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE vehicles SET available=? WHERE vehicle_id=?")) {
            ps.setBoolean(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load vehicles from database
    public void loadVehicles() {
        vehicles.clear();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM vehicles")) {
            while (rs.next()) {
                int id = rs.getInt("vehicle_id");
                String type = rs.getString("type");
                String brand = rs.getString("brand");
                int year = rs.getInt("year");
                double rent = rs.getDouble("rent_per_day");
                boolean available = rs.getBoolean("available");

                Vehicle v = null;
                switch (type) {
                    case "Car": v = new Car(id, brand, year, rent); break;
                    case "Bike": v = new Bike(id, brand, year, rent); break;
                    case "Truck": v = new Truck(id, brand, year, rent); break;
                }

                if (v != null) {
                    v.setAvailable(available);
                    vehicles.add(v);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadRentals();
    }

    // Load rentals from database
    private void loadRentals() {
        rentals.clear();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rentals")) {
            while (rs.next()) {
                int rentalId = rs.getInt("rental_id");
                int vehicleId = rs.getInt("vehicle_id");
                String customerName = rs.getString("customer_name");
                LocalDate rentDate = rs.getDate("rent_date").toLocalDate();
                Date returnDateRaw = rs.getDate("return_date");
                LocalDate returnDate = returnDateRaw != null ? returnDateRaw.toLocalDate() : null;

                Rental rental = new Rental(rentalId, vehicleId, customerName, rentDate, returnDate);
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get active rental for a vehicle
    private Rental getActiveRental(int vehicleId) {
        for (Rental r : rentals) {
            if (r.getVehicleId() == vehicleId && r.isActive()) return r;
        }
        return null;
    }
}