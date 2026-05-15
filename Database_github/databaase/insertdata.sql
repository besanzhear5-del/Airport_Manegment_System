USE airport_db_final;

INSERT INTO Airport (code, name, city, country) VALUES
('JFK','John F. Kennedy International Airport','New York','USA'),
('LHR','Heathrow Airport','London','UK'),
('DXB','Dubai International Airport','Dubai','UAE'),
('CDG','Charles de Gaulle Airport','Paris','France'),
('IST','Istanbul Airport','Istanbul','Turkey'),
('AMM','Queen Alia International Airport','Amman','Jordan'),
('FRA','Frankfurt Airport','Frankfurt','Germany'),
('MAD','Adolfo Suárez Madrid-Barajas Airport','Madrid','Spain'),
('CAI','Cairo International Airport','Cairo','Egypt'),
('DOH','Hamad International Airport','Doha','Qatar'),
('FCO','Leonardo da Vinci Airport','Rome','Italy'),
('BCN','Barcelona-El Prat Airport','Barcelona','Spain'),
('RUH','King Khalid International Airport','Riyadh','Saudi Arabia'),
('KWI','Kuwait International Airport','Kuwait City','Kuwait'),
('BEY','Beirut Rafic Hariri International Airport','Beirut','Lebanon'),
('ATH','Athens International Airport','Athens','Greece'),
('MUC','Munich Airport','Munich','Germany'),
('YYZ','Toronto Pearson International Airport','Toronto','Canada'),
('HND','Tokyo Haneda Airport','Tokyo','Japan'),
('SIN','Singapore Changi Airport','Singapore','Singapore');

INSERT INTO Airline (name, country) VALUES
('Emirates','UAE'),
('Qatar Airways','Qatar'),
('Lufthansa','Germany'),
('British Airways','UK'),
('Air France','France'),
('Turkish Airlines','Turkey'),
('Royal Jordanian','Jordan'),
('EgyptAir','Egypt'),
('American Airlines','USA'),
('Delta Airlines','USA'),
('Saudi Airlines','Saudi Arabia'),
('Kuwait Airways','Kuwait'),
('Middle East Airlines','Lebanon'),
('ITA Airways','Italy'),
('Singapore Airlines','Singapore');

INSERT INTO Passenger (first_name,last_name,passport_number,contact_info) VALUES
('Ahmad','Ali','P1001','ahmad@mail.com'),
('Sara','Khaled','P1002','sara@mail.com'),
('John','Smith','P1003','john@mail.com'),
('Lina','Hassan','P1004','lina@mail.com'),
('Omar','Naser','P1005','omar@mail.com'),
('Mona','Salem','P1006','mona@mail.com'),
('Adam','White','P1007','adam@mail.com'),
('Eva','Brown','P1008','eva@mail.com'),
('Yousef','Adel','P1009','yousef@mail.com'),
('Noor','Tariq','P1010','noor@mail.com'),
('Layla','Mansour','P1011','layla@mail.com'),
('Khaled','Saleh','P1012','khaled@mail.com'),
('Dina','Younis','P1013','dina@mail.com'),
('Mark','Johnson','P1014','mark@mail.com'),
('Hana','Othman','P1015','hana@mail.com'),
('Tariq','Hamad','P1016','tariq@mail.com'),
('Rana','Fares','P1017','rana@mail.com'),
('George','Miller','P1018','george@mail.com'),
('Nadine','Karam','P1019','nadine@mail.com'),
('Zaid','Mahmoud','P1020','zaid@mail.com'),
('Mariam','Saleh','P1021','mariam@mail.com'),
('Fadi','Nassar','P1022','fadi@mail.com'),
('Reem','Darwish','P1023','reem@mail.com'),
('Daniel','Wilson','P1024','daniel@mail.com'),
('Nour','Khoury','P1025','nour.k@mail.com'),
('Hussein','Yasin','P1026','hussein@mail.com'),
('Salma','Qasem','P1027','salma@mail.com'),
('Robert','Taylor','P1028','robert@mail.com'),
('Aseel','Hamed','P1029','aseel@mail.com'),
('Majd','Sawalha','P1030','majd@mail.com');

INSERT INTO Terminal (name, airport_id) VALUES
('Terminal 1',1),
('Terminal 2',2),
('Terminal 3',3),
('Terminal 2E',4),
('International Terminal',5),
('Main Terminal',6),
('Terminal A',7),
('Terminal 4S',8),
('Terminal 3',9),
('Terminal 1',10),
('Terminal 1',11),
('Terminal 2',12),
('Terminal 5',13),
('Terminal 4',14),
('Main Terminal',15),
('Terminal B',16),
('Terminal 2',17),
('Terminal 1',18),
('Terminal 3',19),
('Terminal 4',20);

INSERT INTO Gate (name, terminal_id) VALUES
('A1',1),('A2',1),
('B1',2),('B2',2),
('C1',3),('C2',3),
('D1',4),('D2',4),
('E1',5),('E2',5),
('F1',6),('F2',6),
('G1',7),('G2',7),
('H1',8),('H2',8),
('I1',9),('I2',9),
('J1',10),('J2',10),
('K1',11),('L1',12),
('M1',13),('N1',14),
('O1',15),('P1',16),
('Q1',17),('R1',18),
('S1',19),('T1',20);

INSERT INTO Aircraft (model, capacity, airline_id, current_status) VALUES
('Boeing 737-800',180,7,'Ready'),
('Airbus A320',160,2,'Ready'),
('Boeing 777-300ER',300,1,'Ready'),
('Airbus A380',500,1,'Maintenance'),
('Boeing 787-9',250,5,'Ready'),
('Airbus A321neo',200,6,'Ready'),
('Airbus A330-300',280,7,'Ready'),
('Boeing 747-400',400,8,'Grounded'),
('Airbus A350-900',320,9,'Ready'),
('Boeing 737 MAX 8',190,10,'Ready'),
('Airbus A320neo',165,11,'Ready'),
('Boeing 787-8',240,12,'Maintenance'),
('Airbus A330-200',260,13,'Ready'),
('Airbus A350-1000',350,2,'Ready'),
('Boeing 777-200LR',290,3,'Ready'),
('Airbus A319',140,4,'Ready'),
('Boeing 767-300',220,9,'Grounded'),
('Boeing 787-10',330,15,'Ready'),
('Airbus A321',200,14,'Ready'),
('Boeing 737-900',190,10,'Ready');

INSERT INTO Flight (flight_number, departure_airport_id, arrival_airport_id, aircraft_id, airline_id) VALUES
('RJ101',6,1,1,7),
('QR202',10,2,2,2),
('EK303',3,4,3,1),
('LH404',7,5,15,3),
('BA505',2,6,16,4),
('AF606',4,7,5,5),
('TK707',5,8,6,6),
('MS808',9,3,8,8),
('AA909',1,10,9,9),
('DL010',1,2,10,10),
('RJ222',6,3,7,7),
('QR333',10,1,14,2),
('EK444',3,2,4,1),
('LH555',7,9,15,3),
('BA666',2,4,16,4),
('AF777',4,5,5,5),
('TK888',5,6,6,6),
('MS999',9,7,8,8),
('AA111',1,8,17,9),
('DL222',1,6,20,10),
('SV321',13,6,11,11),
('KU432',14,3,12,12),
('ME543',15,11,13,13),
('AZ654',11,12,19,14),
('SQ765',20,19,18,15);

INSERT INTO Employee (name, role, contact_info, airport_id, employee_password) VALUES
('Ali', 'Pilot', 'ali@airport.com', 1, 'Ali@123'),
('Sara', 'Crew', 'sara@airport.com', 2, 'Sara@123'),
('John', 'Crew', 'john@airport.com', 3, 'John@123'),
('Lina', 'Pilot', 'lina@airport.com', 4, 'Lina@123'),
('Omar', 'Crew', 'omar@airport.com', 5, 'Omar@123'),
('Mona', 'Pilot', 'mona@airport.com', 6, 'Mona@123'),
('Adam', 'Crew', 'adam@airport.com', 7, 'Adam@123'),
('Eva', 'Pilot', 'eva@airport.com', 8, 'Eva@123'),
('Yousef', 'Crew', 'yousef@airport.com', 9, 'Yousef@123'),
('Noor', 'Pilot', 'noor@airport.com', 10, 'Noor@123'),
('Kareem', 'Pilot', 'kareem@airport.com', 11, 'Kareem@123'),
('Hala', 'Crew', 'hala@airport.com', 12, 'Hala@123'),
('Rami', 'Crew', 'rami@airport.com', 13, 'Rami@123'),
('Tala', 'Pilot', 'tala@airport.com', 14, 'Tala@123'),
('Samer', 'Crew', 'samer@airport.com', 15, 'Samer@123');

INSERT INTO Pilot (employee_id, license_number, experience_years) VALUES
(1, 'LIC-P001', 5),
(4, 'LIC-P004', 6),
(6, 'LIC-P006', 4),
(8, 'LIC-P008', 3),
(10, 'LIC-P010', 11),
(11, 'LIC-P011', 9),
(14, 'LIC-P014', 7);

INSERT INTO Crew (employee_id, position, shift, experience_years) VALUES
(2, 'Cabin', 'Morning', 4),
(3, 'Ground', 'Morning', 5),
(5, 'Ground', 'Night', 2),
(7, 'Cabin', 'Morning', 4),
(9, 'Ground', 'Night', 6),
(12, 'Cabin', 'Evening', 3),
(13, 'Ground', 'Evening', 5),
(15, 'Cabin', 'Night', 4);

INSERT INTO Maintenance (aircraft_id, description, date, status) VALUES
(1,'Routine landing gear inspection','2026-02-01','Done'),
(2,'Cabin pressure sensor check','2026-02-03','Done'),
(3,'Navigation system calibration','2026-02-05','Pending'),
(4,'Hydraulic system inspection','2026-02-07','Pending'),
(5,'Brake system inspection','2026-02-09','Done'),
(6,'Engine vibration check','2026-02-11','Done'),
(7,'Fuel system inspection','2026-02-13','Done'),
(8,'Tire replacement scheduled','2026-02-15','Pending'),
(9,'Avionics software update','2026-02-17','Done'),
(10,'Emergency equipment inspection','2026-02-19','Done'),
(11,'Oxygen system check','2026-02-21','Done'),
(12,'Wing flap inspection','2026-02-23','Pending'),
(13,'Cabin lighting repair','2026-02-25','Done'),
(14,'Radar system calibration','2026-02-27','Done'),
(15,'APU service check','2026-03-01','Done'),
(16,'Door seal inspection','2026-03-03','Done'),
(17,'Engine oil leak investigation','2026-03-05','Pending'),
(18,'Landing gear lubrication','2026-03-07','Done'),
(19,'Fuel pump check','2026-03-09','Done'),
(20,'Routine aircraft cleaning and inspection','2026-03-11','Done');

INSERT INTO Flight_Schedule (flight_id, departure_time, arrival_time, status) VALUES
(1,'2026-07-01 08:00:00','2026-07-01 12:00:00','On Time'),
(2,'2026-07-01 09:30:00','2026-07-01 14:00:00','Delayed'),
(3,'2026-07-01 11:00:00','2026-07-01 15:30:00','Boarding'),
(4,'2026-07-02 06:45:00','2026-07-02 10:50:00','Cancelled'),
(5,'2026-07-02 12:20:00','2026-07-02 16:00:00','On Time'),
(6,'2026-07-02 15:10:00','2026-07-02 19:15:00','On Time'),
(7,'2026-07-03 07:40:00','2026-07-03 11:45:00','Delayed'),
(8,'2026-07-03 14:25:00','2026-07-03 18:20:00','On Time'),
(9,'2026-07-03 18:30:00','2026-07-03 23:10:00','Boarding'),
(10,'2026-07-04 05:55:00','2026-07-04 10:30:00','On Time'),
(11,'2026-07-04 08:15:00','2026-07-04 12:00:00','On Time'),
(12,'2026-07-04 10:45:00','2026-07-04 16:30:00','Boarding'),
(13,'2026-07-05 06:30:00','2026-07-05 10:10:00','Delayed'),
(14,'2026-07-05 13:00:00','2026-07-05 17:40:00','On Time'),
(15,'2026-07-05 19:15:00','2026-07-05 22:50:00','Cancelled'),
(16,'2026-07-06 07:20:00','2026-07-06 11:10:00','On Time'),
(17,'2026-07-06 09:10:00','2026-07-06 13:30:00','Boarding'),
(18,'2026-07-06 18:00:00','2026-07-06 22:20:00','Delayed'),
(19,'2026-07-07 11:45:00','2026-07-07 16:25:00','On Time'),
(20,'2026-07-07 22:00:00','2026-07-08 02:30:00','On Time'),
(21,'2026-07-08 08:00:00','2026-07-08 12:10:00','On Time'),
(22,'2026-07-08 13:35:00','2026-07-08 17:45:00','Delayed'),
(23,'2026-07-09 09:40:00','2026-07-09 13:15:00','Boarding'),
(24,'2026-07-09 16:10:00','2026-07-09 20:05:00','On Time'),
(25,'2026-07-10 06:25:00','2026-07-10 12:20:00','On Time');

INSERT INTO Ticket (passenger_id, flight_id, seat_number, booking_date, class, price, status) VALUES
(1,1,'1A','2026-06-01','Economy',200,'Booked'),
(2,2,'2B','2026-06-01','Business',400,'Booked'),
(3,3,'3C','2026-06-02','Economy',150,'Cancelled'),
(4,4,'4D','2026-06-02','First',800,'Booked'),
(5,5,'5E','2026-06-03','Economy',180,'Boarded'),
(6,6,'6F','2026-06-03','Business',350,'Booked'),
(7,7,'7A','2026-06-04','Economy',170,'Booked'),
(8,8,'8B','2026-06-04','First',900,'Booked'),
(9,9,'9C','2026-06-05','Economy',160,'Cancelled'),
(10,10,'10D','2026-06-05','Business',300,'Booked'),
(11,11,'11A','2026-06-06','Economy',210,'Booked'),
(12,12,'12B','2026-06-06','Business',460,'Booked'),
(13,13,'13C','2026-06-07','Economy',190,'Booked'),
(14,14,'14D','2026-06-07','First',850,'Booked'),
(15,15,'15E','2026-06-08','Economy',175,'Cancelled'),
(16,16,'16F','2026-06-08','Business',390,'Booked'),
(17,17,'17A','2026-06-09','Economy',200,'Boarded'),
(18,18,'18B','2026-06-09','First',920,'Booked'),
(19,19,'19C','2026-06-10','Economy',185,'Booked'),
(20,20,'20D','2026-06-10','Business',420,'Booked'),
(21,21,'21A','2026-06-11','Economy',220,'Booked'),
(22,22,'22B','2026-06-11','Economy',230,'Booked'),
(23,23,'23C','2026-06-12','Business',480,'Booked'),
(24,24,'24D','2026-06-12','Economy',195,'Booked'),
(25,25,'25E','2026-06-13','First',880,'Booked'),
(26,1,'26F','2026-06-13','Economy',205,'Booked'),
(27,2,'27A','2026-06-14','Business',410,'Booked'),
(28,3,'28B','2026-06-14','Economy',155,'Cancelled'),
(29,6,'29C','2026-06-15','Economy',210,'Booked'),
(30,9,'30D','2026-06-15','Business',430,'Booked');

INSERT INTO BoardingPass (ticket_id, gate_id) VALUES
(1,1),
(2,3),
(6,11),
(7,13),
(8,17),
(10,19),
(11,1),
(12,20),
(13,5),
(14,15),
(16,12),
(18,8),
(19,16),
(20,2),
(21,23),
(22,24),
(23,25),
(24,22),
(25,30),
(26,1),
(27,3),
(29,11),
(30,17);

INSERT INTO Baggage (passenger_id, flight_id, weight, status, current_location) VALUES
(1,1,20,'Loaded','Gate A1'),
(2,2,18,'Pending','Check-in Counter 2'),
(3,3,25,'Loaded','Aircraft Hold'),
(4,4,22,'Lost','Baggage Office'),
(5,5,19,'Loaded','Gate C1'),
(6,6,21,'Pending','Check-in Counter 4'),
(7,7,23,'Loaded','Aircraft Hold'),
(8,8,24,'Loaded','Gate I1'),
(9,9,20,'Lost','Unknown'),
(10,10,18,'Loaded','Gate J1'),
(11,11,19,'Checked In','Check-in Counter 2'),
(12,12,26,'Loaded','Gate J2'),
(13,13,17,'In Transit','Sorting Area'),
(14,14,24,'Loaded','Aircraft Hold'),
(15,15,20,'Delayed','Baggage Office'),
(16,16,22,'Checked In','Check-in Counter 4'),
(17,17,18,'Loaded','Gate G1'),
(18,18,27,'In Transit','Belt 3'),
(19,19,21,'Arrived','Arrival Belt 1'),
(20,20,23,'Checked In','Check-in Counter 5'),
(21,21,19,'Loaded','Gate M1'),
(22,22,21,'Checked In','Check-in Counter 6'),
(23,23,24,'In Transit','Sorting Area'),
(24,24,20,'Loaded','Gate L1'),
(25,25,26,'Checked In','Check-in Counter 7'),
(26,1,17,'Loaded','Gate A1'),
(27,2,28,'Checked In','Check-in Counter 2'),
(28,3,19,'Delayed','Baggage Office'),
(29,6,22,'Loaded','Gate F1'),
(30,9,23,'In Transit','Sorting Area');

INSERT INTO Flight_Pilot (flight_id, pilot_id, role) VALUES
(1,1,'Captain'),
(2,4,'Captain'),
(3,6,'Captain'),
(4,8,'Captain'),
(5,10,'Captain'),
(6,1,'Co-Pilot'),
(7,4,'Co-Pilot'),
(8,6,'Co-Pilot'),
(9,8,'Co-Pilot'),
(10,10,'Co-Pilot'),
(11,11,'Captain'),
(12,14,'Captain'),
(13,1,'Captain'),
(14,4,'Captain'),
(15,6,'Captain'),
(16,8,'Captain'),
(17,10,'Captain'),
(18,11,'Co-Pilot'),
(19,14,'Co-Pilot'),
(20,1,'Co-Pilot'),
(21,4,'Captain'),
(22,6,'Captain'),
(23,8,'Captain'),
(24,10,'Captain'),
(25,11,'Captain');

INSERT INTO Flight_Crew (flight_id, crew_id, shift_role) VALUES
(1,2,'Cabin'),
(2,3,'Ground'),
(3,5,'Ground'),
(4,7,'Cabin'),
(5,9,'Ground'),
(6,2,'Cabin'),
(7,3,'Ground'),
(8,5,'Ground'),
(9,7,'Cabin'),
(10,9,'Ground'),
(11,12,'Cabin'),
(12,13,'Ground'),
(13,15,'Cabin'),
(14,2,'Cabin'),
(15,3,'Ground'),
(16,5,'Ground'),
(17,7,'Cabin'),
(18,9,'Ground'),
(19,12,'Cabin'),
(20,13,'Ground'),
(21,15,'Cabin'),
(22,2,'Cabin'),
(23,3,'Ground'),
(24,5,'Ground'),
(25,7,'Cabin');

USE airport_db_final;

SELECT * FROM Airport;

SELECT * FROM Airline;

SELECT * FROM Passenger;

SELECT * FROM Terminal;

SELECT * FROM Gate;

SELECT * FROM Aircraft;

SELECT * FROM Flight;

SELECT * FROM Employee;

SELECT * FROM Pilot;

SELECT * FROM Crew;

SELECT * FROM Maintenance;

SELECT * FROM Flight_Schedule;

SELECT * FROM Ticket;


SELECT * FROM BoardingPass;

SELECT * FROM Baggage;

SELECT * FROM Flight_Pilot;

SELECT * FROM Flight_Crew;