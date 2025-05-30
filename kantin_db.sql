
-- Membuat database kantin_db
CREATE DATABASE IF NOT EXISTS kantin_db;
USE kantin_db;

-- Tabel user untuk login
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Contoh user
INSERT INTO user (username, password) VALUES ('admin', 'admin123');

-- Tabel menu untuk daftar makanan dan minuman
CREATE TABLE IF NOT EXISTS menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    harga DOUBLE NOT NULL
);

-- Contoh data menu
INSERT INTO menu (nama, harga) VALUES 
('Nasi Goreng', 15000),
('Mie Ayam', 12000),
('Es Teh', 5000),
('Ayam Bakar', 20000),
('Soto Ayam', 14000);

-- Tabel pesanan
CREATE TABLE IF NOT EXISTS pesanan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_menu INT NOT NULL,
    jumlah INT NOT NULL,
    total_harga DOUBLE NOT NULL,
    FOREIGN KEY (id_menu) REFERENCES menu(id)
);
