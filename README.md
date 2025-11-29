# Smart-Vehicle-Parking-Management-System

# 🚗 Smart Vehicle Parking & Slot Management System

A Java Swing + MySQL based desktop application designed to efficiently manage vehicle parking slots with real-time booking and release functionality.

---

## ⭐ Features
- 🔐 Admin Login (admin / admin123)
- ➕ Add Parking Slots
- 🚘 Book Parking Slot with Vehicle Number
- ♻ Release Slot after vehicle exit
- 📊 Live status: AVAILABLE / BOOKED
- 🗄 Database connected using JDBC

---

## 🛠️ Technologies Used
| Technology | Purpose |
|-----------|---------|
| Java Swing | GUI |
| JDBC | DB Connection |
| MySQL | Database |
| VS Code / NetBeans | IDE |

---

## 📂 Project Structure
parking-system/
├── src/
│ ├── DBConnection.java
│ ├── LoginFrame.java
│ ├── MainFrame.java
│ ├── Slot.java
│ └── SlotDAO.java
├── lib/
│ └── mysql-connector-j.jar
├── database/
│ └── parkingdb.sql
├── screenshots/ (Add images here)
└── README.md


---

## ⚙️ How to Run

### 1️⃣ Setup Database
Run this script in MySQL Workbench:



### 2️⃣ Update DB Credentials  
Inside `DBConnection.java`:
```java
private static final String USER = "root";
private static final String PASSWORD = "";

3️⃣ Add MySQL Connector

Ensure mysql-connector-j.jar is inside lib and added to classpath.

4️⃣ Run the Project

Execute → LoginFrame.java
Login with:
Username: admin
Password: admin123

🚀 Future Enhancements

QR Code Parking Ticket

Bill Generation

Multi-level Parking Support

Vehicle History Tracking

Number Plate Detection (OCR)

👨‍💻 Developer

Saraswati Zerkunte
Java Developer (Aspiring)

📜 License

This project is for educational purposes only.

💬 Feedback

Suggestions and improvements are welcome 😊
