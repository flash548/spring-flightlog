

-- Plane Models
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS models;
DROP TABLE IF EXISTS users;

CREATE TABLE models (
    tail_number INTEGER PRIMARY KEY,
    model text NOT NULL,
    maint_good BOOLEAN
);

-- Users table
CREATE TABLE users (
    id INTEGER PRIMARY KEY auto_increment,
    name text NOT NULL,
    is_admin BOOLEAN
);

-- Flights Table
CREATE TABLE flights (
    id INTEGER PRIMARY KEY auto_increment,
    depart_time text NOT NULL,
    arrive_time text NOT NULL,
    depart_from text NOT NULL,
    arrive_to text NOT NULL,
    tail_number INTEGER,
    pilot INTEGER,
    FOREIGN KEY (pilot) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (tail_number) REFERENCES models(tail_number) ON DELETE CASCADE
);

INSERT INTO users (name, is_admin) VALUES ('Bill', false);
INSERT INTO users (name, is_admin) VALUES ('Frank', true);
INSERT INTO users (name, is_admin) VALUES ('Rick', false);

INSERT INTO models (tail_number, model, maint_good) VALUES (12345, 'MD-80', true);
INSERT INTO models (tail_number, model, maint_good) VALUES (55555, '747', true);
INSERT INTO models (tail_number, model, maint_good) VALUES (99999, '737', true);
INSERT INTO models (tail_number, model, maint_good) VALUES (44444, 'B-52', false);

INSERT INTO flights (depart_time, arrive_time, depart_from, arrive_to, tail_number, pilot) VALUES ('0400', '0600', 'KORD', 'KIND', 12345, 1);
INSERT INTO flights (depart_time, arrive_time, depart_from, arrive_to, tail_number, pilot) VALUES ('1000', '1045', 'OKDI', 'OKAS', 44444, 3);

-- select flights.depart_time, flights.arrive_time, flights.depart_from, flights.arrive_to, flights.tail_number, users.name FROM flights INNER JOIN users on (users.id = flights.pilot);
