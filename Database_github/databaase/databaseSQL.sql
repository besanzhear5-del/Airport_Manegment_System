drop database airport_db_final;
CREATE DATABASE airport_db_final;
USE airport_db_final;

CREATE TABLE Airport (
    airport_id INT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(50),
    country VARCHAR(50)
);


CREATE TABLE Airline (
    airline_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    country VARCHAR(50)
);


CREATE TABLE Passenger (
    passenger_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    passport_number VARCHAR(50) UNIQUE,
    contact_info VARCHAR(100)
);

CREATE TABLE Terminal (
    terminal_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    airport_id INT,
    FOREIGN KEY (airport_id) REFERENCES Airport(airport_id)
    ON DELETE CASCADE
);

CREATE TABLE Gate (
    gate_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    terminal_id INT,
    FOREIGN KEY (terminal_id) REFERENCES Terminal(terminal_id)
    ON DELETE CASCADE
);

CREATE TABLE Aircraft (
    aircraft_id INT PRIMARY KEY AUTO_INCREMENT,
    model VARCHAR(50),
    capacity INT,
    current_status VARCHAR(30),
    airline_id INT,

    FOREIGN KEY (airline_id)
    REFERENCES Airline(airline_id)
    ON DELETE CASCADE
);

CREATE TABLE Flight (
    flight_id INT PRIMARY KEY AUTO_INCREMENT,
    flight_number VARCHAR(20) UNIQUE,
    departure_airport_id INT,
    arrival_airport_id INT,
    aircraft_id INT,
    airline_id INT,
  
    FOREIGN KEY (departure_airport_id) REFERENCES Airport(airport_id)
    ON DELETE CASCADE,

    FOREIGN KEY (arrival_airport_id) REFERENCES Airport(airport_id)
    ON DELETE CASCADE,

    FOREIGN KEY (aircraft_id) REFERENCES Aircraft(aircraft_id)
    ON DELETE CASCADE,

    FOREIGN KEY (airline_id) REFERENCES Airline(airline_id)
    ON DELETE CASCADE
);

CREATE TABLE Employee (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    role VARCHAR(50),
    contact_info VARCHAR(100),
    airport_id INT,
    FOREIGN KEY (airport_id) REFERENCES Airport(airport_id)
    ON DELETE CASCADE
);


CREATE TABLE Pilot (
    employee_id INT PRIMARY KEY,
    license_number VARCHAR(50),
    experience_years INT,
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
    ON DELETE CASCADE
);

CREATE TABLE Crew (
    employee_id INT PRIMARY KEY,
    position VARCHAR(50),
    shift VARCHAR(50),
    experience_years INT,
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
    ON DELETE CASCADE
);

CREATE TABLE Maintenance (
    maintenance_id INT PRIMARY KEY AUTO_INCREMENT,
    aircraft_id INT,
    description VARCHAR(200),
    date DATE,
    status VARCHAR(20),

    FOREIGN KEY (aircraft_id) REFERENCES Aircraft(aircraft_id)
    ON DELETE CASCADE

);

CREATE TABLE Flight_Schedule (
    schedule_id INT PRIMARY KEY AUTO_INCREMENT,
    flight_id INT,
    departure_time DATETIME,
    arrival_time DATETIME,
    status VARCHAR(20),

    FOREIGN KEY (flight_id) REFERENCES Flight(flight_id)
    ON DELETE CASCADE
);

CREATE TABLE Ticket (
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    passenger_id INT,
    flight_id INT,
    seat_number VARCHAR(10),
    booking_date DATE,
    class VARCHAR(20),
    price DECIMAL(10,2),
    status VARCHAR(20),

    FOREIGN KEY (passenger_id) REFERENCES Passenger(passenger_id)
    ON DELETE CASCADE,

    FOREIGN KEY (flight_id) REFERENCES Flight(flight_id)
    ON DELETE CASCADE
);


CREATE TABLE BoardingPass (
    boarding_pass_id INT PRIMARY KEY AUTO_INCREMENT,
    ticket_id INT,
    gate_id INT,

    FOREIGN KEY (ticket_id) REFERENCES Ticket(ticket_id)
    ON DELETE CASCADE,

    FOREIGN KEY (gate_id) REFERENCES Gate(gate_id)
    ON DELETE CASCADE

);

CREATE TABLE Baggage (
    baggage_id INT PRIMARY KEY AUTO_INCREMENT,
    passenger_id INT,
    flight_id INT,
    weight FLOAT,
    status VARCHAR(20),
    current_location VARCHAR(100),

    FOREIGN KEY (passenger_id) REFERENCES Passenger(passenger_id)
    ON DELETE CASCADE,

    FOREIGN KEY (flight_id) REFERENCES Flight(flight_id)
    ON DELETE CASCADE
);


CREATE TABLE Flight_Pilot (
    flight_id INT,
    pilot_id INT,
    role VARCHAR(50),
    PRIMARY KEY (flight_id, pilot_id),

    FOREIGN KEY (flight_id) REFERENCES Flight(flight_id)
    ON DELETE CASCADE,

    FOREIGN KEY (pilot_id) REFERENCES Pilot(employee_id)
    ON DELETE CASCADE
);

CREATE TABLE Flight_Crew (
    flight_id INT,
    crew_id INT,
    shift_role VARCHAR(50),
    PRIMARY KEY (flight_id, crew_id),

    FOREIGN KEY (flight_id) REFERENCES Flight(flight_id)
    ON DELETE CASCADE,

    FOREIGN KEY (crew_id) REFERENCES Crew(employee_id)
    ON DELETE CASCADE
);
ALTER TABLE Employee
ADD employee_password VARCHAR(50);