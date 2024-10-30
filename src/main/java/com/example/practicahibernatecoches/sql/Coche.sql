DROP DATABASE IF EXISTS Coches;
CREATE DATABASE Coches;
USE Coches;
CREATE TABLE coche (
    id INT AUTO_INCREMENT PRIMARY KEY,
    matricula VARCHAR(20) NOT NULL,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    tipo VARCHAR(50),
    UNIQUE (matricula)
);
