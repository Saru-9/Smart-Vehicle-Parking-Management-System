CREATE DATABASE IF NOT EXISTS parkingdb;
USE parkingdb;

CREATE TABLE IF NOT EXISTS parking_slots (
    id INT AUTO_INCREMENT PRIMARY KEY,
    slot_number VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    vehicle_no VARCHAR(20),
    booked_at TIMESTAMP NULL
);

INSERT INTO parking_slots (slot_number, status) VALUES
('S1', 'AVAILABLE'),
('S2', 'AVAILABLE'),
('S3', 'AVAILABLE');
