CREATE DATABASE IF NOT EXISTS complaint_db;
USE complaint_db;

CREATE TABLE IF NOT EXISTS complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tracking_id VARCHAR(50) UNIQUE,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending'
);

CREATE TABLE IF NOT EXISTS admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

-- Insert a default admin account
INSERT IGNORE INTO admins (username, password) VALUES ('admin', 'admin123');
