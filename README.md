# 🚗 Vehicle Rental System (Java + MySQL)

A **console-based Vehicle Rental System** developed using **Java, Object-Oriented Programming (OOP), and MySQL**.
This system allows staff to efficiently **manage vehicles, handle rentals, and perform fast searches using algorithms**.

---

# 📌 Features

### 🚘 Vehicle Management

* Add new vehicles (Car, Bike, Truck)
* Remove vehicles
* Display all vehicles

### 🔍 Searching & Sorting

* Search vehicle by ID using **Binary Search**
* Sort vehicles using **Bubble Sort**

### 🔄 Rental Operations

* Rent vehicles
* Return vehicles
* Prevent renting already rented vehicles

### 📊 Monitoring & Insights

* View rented vehicles
* View available (non-rented) vehicles
* Count number of rented vehicles

### 🛡️ Validation & Safety

* Input validation (IDs, rent, type, etc.)
* Prevent duplicate vehicle entries

---

# 🧠 OOP Concepts Used

| Concept           | Implementation                                      |
| ----------------- | --------------------------------------------------- |
| Encapsulation     | Private variables with getters/setters in `Vehicle` |
| Inheritance       | `Car`, `Bike`, `Truck` extend `Vehicle`             |
| Polymorphism      | `calculateRent()` behaves differently per vehicle   |
| Abstraction       | `Vehicle` defined as an abstract class              |
| Method Overriding | Each subclass overrides `calculateRent()`           |

---

# ⚙️ Algorithms Used

### 🔹 Bubble Sort

Used to sort vehicles by ID before searching.

Time Complexity:

```
O(n²)
```

---

### 🔹 Binary Search

Used to efficiently search vehicles by ID.

Time Complexity:

```
O(log n)
```

---

# 🏗️ Project Architecture

This project follows a **Layered Architecture**:

```
Presentation Layer   → Main.java (User Interaction)
Business Logic      → VehicleManager.java
Data Access Layer   → DBConnection + SQL
Database           → MySQL (vehicles table)
```

---

# 🗂️ Project Structure

```
VehicleRentalSystem
│
├── Main.java
├── Vehicle.java
├── Car.java
├── Bike.java
├── Truck.java
├── VehicleManager.java
├── DBConnection.java
└── README.md
```

---

# 💾 Database Setup

### 🔹 Create Database

```sql
CREATE DATABASE vehicle_rental;
```

### 🔹 Create Table

```sql
CREATE TABLE vehicles (
    vehicle_id INT PRIMARY KEY,
    type VARCHAR(20),
    brand VARCHAR(50),
    year INT,
    rent_per_day DOUBLE,
    available BOOLEAN
);
```

---

# 🔗 SQL Queries Used

### Insert Vehicle

```sql
INSERT INTO vehicles(vehicle_id,type,brand,year,rent_per_day,available)
VALUES(?,?,?,?,?,?)
```

### Retrieve Vehicles

```sql
SELECT * FROM vehicles;
```

### Update Availability

```sql
UPDATE vehicles SET available=? WHERE vehicle_id=?;
```

### Delete Vehicle

```sql
DELETE FROM vehicles WHERE vehicle_id=?;
```

---

# ▶️ How to Run the Project

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/yourusername/vehicle-rental-system.git
```

### 2️⃣ Open in IDE

Use:

* IntelliJ IDEA
* Eclipse
* VS Code

---

### 3️⃣ Configure Database

Update in `DBConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/vehicle_rental";
private static final String USER = "root";
private static final String PASS = "your_password";
```

---

### 4️⃣ Run the Application

Run:

```
Main.java
```

---

# 🖥️ Sample Menu

```
===== Vehicle Rental System =====

1. Add Vehicle
2. Display All Vehicles
3. Search Vehicle
4. Rent Vehicle
5. Return Vehicle
6. Remove Vehicle
7. View Rented Vehicles
8. View Available Vehicles
9. Count Rented Vehicles
10. Exit
```

---

# 🚀 Future Enhancements

* Add GUI using JavaFX or Swing
* Implement user authentication (login system)
* Add rental history tracking
* Introduce multithreading (concurrent users)
* Convert to REST API using Spring Boot

---

# 🧑‍💻 Author

Developed as a **Java OOP + Database Systems project**.

---

# ⭐ Support

If you found this project useful, consider giving it a **star ⭐ on GitHub**!
