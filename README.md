# 🚗 Smart Parking Management System

A Java Swing-based Smart Parking Management System that helps manage parking slots efficiently. The application allows administrators to add parking slots, book slots for vehicles, release occupied slots, and monitor parking availability through a modern dashboard interface.

## 📌 Features

- 🔐 Admin Login Authentication
- ➕ Add New Parking Slots
- 🚗 Book Parking Slots
- 🔄 Release Occupied Slots
- 📊 Real-Time Dashboard Statistics
  - Total Slots
  - Available Slots
  - Occupied Slots
- 🕒 Live Date & Time Display
- 🎨 Modern Dark-Themed User Interface
- 🗄️ MySQL Database Integration
- 📋 Parking Slot Management Table

## 🛠️ Technologies Used

- Java
- Java Swing (GUI)
- MySQL Database
- JDBC (MySQL Connector)
- Object-Oriented Programming (OOP)

## 📂 Project Structure

```
PARKING-SYSTEM
│
├── src/
│   ├── DBConnection.java
│   ├── LoginFrame.java
│   ├── MainFrame.java
│   ├── Slot.java
│   └── SlotDAO.java
│
├── lib/
│   └── mysql-connector-j-9.5.0.jar
│
├── screenshots/
│   ├── login.png
│   ├── dashboard.png
│   └── Book Slot.png
│
└── out/
    └── Compiled Class Files
```

## 🔑 Default Login Credentials

```
Username: admin
Password: admin123
```

## 🗄️ Database Setup

1. Install MySQL Server.
2. Create a database:

```sql
CREATE DATABASE parking_system;
```

3. Create a parking slots table:

```sql
CREATE TABLE slots (
    id INT PRIMARY KEY AUTO_INCREMENT,
    slot_number VARCHAR(20) UNIQUE NOT NULL,
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    vehicle_no VARCHAR(50),
    booked_at VARCHAR(50)
);
```

4. Update database credentials in `DBConnection.java`.

## 🚀 How to Run

1. Clone the repository:

```bash
git clone https://github.com/yourusername/smart-parking-system.git
```

2. Open the project in VS Code or any Java IDE.

3. Add MySQL Connector JAR to the project's build path.

4. Configure MySQL database connection.

5. Compile and run:

```bash
javac *.java
java LoginFrame
```

## 📸 Screenshots

### Login Screen
![Login](screenshots/login.png)

### Dashboard
![Dashboard](screenshots/dashboard.png)


## 🎯 Future Enhancements

- QR Code-Based Vehicle Entry
- RFID Integration
- Online Slot Reservation
- Payment Gateway Integration
- Vehicle Exit Tracking
- Parking Analytics Dashboard

## 👩‍💻 Project Credits

**Project Developed By:** Saraswati

**Project Type:** Academic / Mini Project

This project was developed as a Smart Parking Management System to demonstrate Java Swing GUI development, database connectivity using JDBC, and parking slot management operations.

## 📜 License

This project is created for educational and learning purposes.
