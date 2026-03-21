import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        VehicleManager manager = new VehicleManager();

        manager.loadVehicles();

        int choice;

        do {

            System.out.println("\n===== Vehicle Rental System =====");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Display All Vehicles");
            System.out.println("3. Search Vehicle by ID");
            System.out.println("4. Rent Vehicle");
            System.out.println("5. Return Vehicle");
            System.out.println("6. Remove a Vehicle");
            System.out.println("7. View Vehicle Statistics");
            System.out.println("0. Exit");
            System.out.println("===================================");

            System.out.print("Enter choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                sc.next();
                continue;
            }

            choice = sc.nextInt();

            switch (choice) {

                case 1: // Add Vehicle
                    sc.nextLine(); // consume leftover newline
                    String type;
                    while (true) {
                        System.out.println("Enter Vehicle Type (Car/Bike/Truck): ");
                        type = sc.nextLine();
                        if (type.equalsIgnoreCase("Car") || type.equalsIgnoreCase("Bike") || type.equalsIgnoreCase("Truck")) {
                            break;
                        }
                        System.out.println("Invalid vehicle type. Try again.");
                    }

                    int id;
                    while (true) {
                        System.out.println("Enter Vehicle ID (positive number):");
                        if (sc.hasNextInt()) {
                            id = sc.nextInt();
                            if (id > 0) break;
                        } else sc.next();
                        System.out.println("Invalid ID. Please enter a positive number.");
                    }

                    sc.nextLine(); // consume newline
                    System.out.println("Enter Brand:");
                    String brand = sc.nextLine();

                    int year;
                    while (true) {
                        System.out.println("Enter Year:");
                        if (sc.hasNextInt()) {
                            year = sc.nextInt();
                            if (year >= 1990 && year <= 2100) break;
                        } else sc.next();
                        System.out.println("Invalid year. Enter between 1990 and 2100.");
                    }

                    double rent;
                    while (true) {
                        System.out.println("Enter Rent per Day:");
                        if (sc.hasNextDouble()) {
                            rent = sc.nextDouble();
                            if (rent > 0) break;
                        } else sc.next();
                        System.out.println("Invalid rent amount. Must be greater than 0.");
                    }

                    Vehicle v = null;
                    if (type.equalsIgnoreCase("Car")) v = new Car(id, brand, year, rent);
                    else if (type.equalsIgnoreCase("Bike")) v = new Bike(id, brand, year, rent);
                    else if (type.equalsIgnoreCase("Truck")) v = new Truck(id, brand, year, rent);

                    manager.addVehicle(v);
                    break;

                case 2: // Display All Vehicles
                    manager.displayAllVehicles();
                    break;

                case 3: // Search Vehicle
                    System.out.println("Enter Vehicle ID to search:");
                    if (sc.hasNextInt()) {
                        int searchId = sc.nextInt();
                        manager.searchVehicleById(searchId);
                    } else {
                        System.out.println("Invalid ID.");
                        sc.next();
                    }
                    break;

                case 4: // Rent Vehicle
                    sc.nextLine(); // consume leftover newline
                    System.out.println("Enter Vehicle ID to rent:");
                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid ID.");
                        sc.next();
                        break;
                    }
                    int rentId = sc.nextInt();
                    sc.nextLine(); // consume newline

                    System.out.println("Enter Customer Name:");
                    String customerName = sc.nextLine();

                    int days;
                    while (true) {
                        System.out.println("Enter number of days:");
                        if (sc.hasNextInt()) {
                            days = sc.nextInt();
                            if (days > 0) break;
                        } else sc.next();
                        System.out.println("Invalid number of days.");
                    }

                    manager.rentVehicle(rentId, customerName, days);
                    break;

                case 5: // Return Vehicle
                    System.out.println("Enter Vehicle ID to return:");
                    if (sc.hasNextInt()) {
                        int returnId = sc.nextInt();
                        manager.returnVehicle(returnId);
                    } else {
                        System.out.println("Invalid ID.");
                        sc.next();
                    }
                    break;

                case 6: // Remove Vehicle
                    System.out.println("Enter Vehicle ID to remove: ");
                    if(sc.hasNextInt()){
                        int removeId = sc.nextInt();
                        manager.removeVehicle(removeId);
                    } else {
                        System.out.println("Invalid Id");
                        sc.next();
                    }
                    break;

                case 7: // View Vehicle Statistics
                    manager.vehicleStatistics();
                    break;

                case 0: // Exit
                    System.out.println("Exiting system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);

        sc.close();
    }
}