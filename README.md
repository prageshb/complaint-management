# Complaint Management System

A simple desktop-based Complaint Management System built using Java Swing and JDBC for a college project. This application allows users to submit grievances and provides an authenticated dashboard for administrators to view, manage, and export resolved complaints.

## 🛠 Technologies Used
- **Language**: Java 17
- **UI Framework**: Java Swing
- **Database**: MySQL
- **Database Connectivity**: JDBC (MySQL Connector/J)
- **Build Tool**: Maven

## ✨ Features
### User Portal
- **Submit Complaints**: Users can easily submit a grievance by providing a title and description.
- **Complaint Tracking**: Upon submission, an 8-character unique alphanumeric Tracking ID is generated. Users can easily copy it to their clipboard with a single click.
- **Track Progress**: A dedicated button on the main page allows users to enter their Tracking ID and instantly see the real-time status (Pending/Resolved) of their grievance.

### Admin Dashboard
- **Authentication Layer**: Secure login for administrators.
- **View Complaints**: A tabular view (`JTable`) displaying all submitted grievances, including their unique Tracking IDs and current status.
- **Mark as Resolved**: Admins can change the status of pending complaints to 'Resolved'.
- **Delete Records**: Admins can permanently delete a complaint from the system.
- **Export Log**: Admins can export a daily summary log of resolved complaints to a local text file (`.txt`).

## 📂 Project Structure
```text
complaint-management/
│
├── pom.xml                   # Maven build configuration and dependencies
├── setup.sql                 # SQL script for initializing the database
├── src/main/java/com/complaint/
│   ├── Main.java             # Entry point of the application
│   ├── Complaint.java        # Model class representing a grievance
│   ├── DatabaseConnection.java # Handles JDBC MySQL connection
│   ├── ComplaintDAO.java     # Database logic (Insert, Update, Delete, Select)
│   ├── UserForm.java         # Swing UI for user submission
│   ├── AdminLoginForm.java   # Swing UI for admin authentication
│   └── AdminDashboard.java   # Swing UI for admin operations
└── README.md
```

## 💾 Database Schema
The database uses two tables: `complaints` to store user grievances, and `admins` to handle dashboard authentication.

```sql
CREATE DATABASE IF NOT EXISTS complaint_db;
USE complaint_db;

-- Table to store user submitted grievances
CREATE TABLE IF NOT EXISTS complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tracking_id VARCHAR(50) UNIQUE,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending'
);

-- Table to store administrator credentials
CREATE TABLE IF NOT EXISTS admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

-- Default admin credentials
INSERT IGNORE INTO admins (username, password) VALUES ('admin', 'admin123');
```

## 🚀 Setup & Installation

### 1. Prerequisites
- **Java Development Kit (JDK)** (Version 8+)
- **MySQL Server** installed and running on `localhost:3306`.
- **Maven** installed (for dependency management).

### 2. Database Initialization
1. Open your MySQL client (e.g., MySQL Workbench or Command Line).
2. Execute the queries provided in the `setup.sql` file (or from the schema section above) to create the `complaint_db` database and its tables.

### 3. Configure Database Credentials
Open `src/main/java/com/complaint/DatabaseConnection.java` and update the database credentials to match your local MySQL configuration:
```java
private static final String URL = "jdbc:mysql://localhost:3306/complaint_db";
private static final String USER = "root";         // Replace with your MySQL username
private static final String PASSWORD = "password"; // Replace with your MySQL password
```

### 4. Build and Run
If using an IDE like VS Code, Eclipse, or IntelliJ, simply open the project, ensure dependencies sync, and run the `Main.java` file. 

If running from the terminal using Maven:
```bash
# Compile the project
mvn clean compile

# Run the application
mvn exec:java -Dexec.mainClass="com.complaint.Main"
```

## 🔒 Default Login
- **Username**: `admin`
- **Password**: `admin123`
