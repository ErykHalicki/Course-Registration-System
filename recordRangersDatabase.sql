-- Create Database
CREATE DATABASE UniversityDB;
USE UniversityDB;

-- Create User Table
CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    profile_photo TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_last TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create Student Table
CREATE TABLE Student (
    student_id INT PRIMARY KEY,
    enrollment_date DATE,
    status ENUM('Active', 'Inactive', 'Graduated') NOT NULL,
    FOREIGN KEY (student_id) REFERENCES User(user_id)
);

-- Create Admin Table
CREATE TABLE Admin (
    admin_id INT PRIMARY KEY,
    role VARCHAR(50) NOT NULL,
    FOREIGN KEY (admin_id) REFERENCES User(user_id)
);

-- Create Course Table
CREATE TABLE Course (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    course_code VARCHAR(20) UNIQUE NOT NULL,
    num_credits INT,
    description TEXT,
    capacity INT NOT NULL,
    start_date DATE,
    end_date DATE,
    term_label VARCHAR(50),
    days VARCHAR(20),
    start_time TIME,
    end_time TIME,
    location VARCHAR(100)
);

-- Create Enrollments Table
CREATE TABLE Enrollments (
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Enrolled', 'Dropped', 'Completed') NOT NULL,
    grade CHAR(2),
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (course_id) REFERENCES Course(course_id)
);

-- Create Labs Table
CREATE TABLE Labs (
    lab_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    lab_name VARCHAR(100),
    FOREIGN KEY (course_id) REFERENCES Course(course_id)
);

-- Create Lab Section Table
CREATE TABLE LabSection (
    section_id INT AUTO_INCREMENT PRIMARY KEY,
    lab_id INT,
    capacity INT,
    days VARCHAR(20),
    start_time TIME,
    end_time TIME,
    location VARCHAR(100),
    FOREIGN KEY (lab_id) REFERENCES Labs(lab_id)
);

-- Create Waitlist Table
CREATE TABLE Waitlist (
    waitlist_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_id INT,
    section_id INT NULL,
    type ENUM('Lab', 'Course') NOT NULL,
    position INT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (course_id) REFERENCES Course(course_id),
    FOREIGN KEY (section_id) REFERENCES LabSection(section_id)
);

-- Create Lab Enrollment Table
CREATE TABLE LabEnrollment (
    student_id INT NOT NULL,
    section_id INT NOT NULL,
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (student_id, section_id),
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (section_id) REFERENCES LabSection(section_id)
);

-- Create Reports Table
CREATE TABLE Reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_id INT,
    grade CHAR(2),
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (course_id) REFERENCES Course(course_id)
);

-- Insert Dummy Data into User Table
INSERT INTO User (first_name, last_name, email, password) VALUES
('John', 'Doe', 'john.doe@example.com', 'hashed_password1'),
('Jane', 'Smith', 'jane.smith@example.com', 'hashed_password2');

-- Insert Dummy Data into Student Table
INSERT INTO Student (student_id, enrollment_date, status) VALUES
(1, '2023-09-01', 'Active');

-- Insert Dummy Data into Admin Table
INSERT INTO Admin (admin_id, role) VALUES
(2, 'Registrar');

-- Insert Dummy Data into Course Table
INSERT INTO Course (course_name, course_code, num_credits, capacity, term_label) VALUES
('Data Structures', 'CS200', 3, 50, '2024 Winter T1'),
('Database Systems', 'CS300', 3, 40, '2024 Winter T1');

-- Insert Dummy Data into Enrollments Table
INSERT INTO Enrollments (student_id, course_id, status) VALUES
(1, 1, 'Enrolled');

-- Insert Dummy Data into Labs Table
INSERT INTO Labs (course_id, lab_name) VALUES
(1, 'Data Structures Lab');

-- Insert Dummy Data into Lab Section Table
INSERT INTO LabSection (lab_id, capacity, days, start_time, end_time, location) VALUES
(1, 25, 'Monday', '10:00:00', '12:00:00', 'Room 101');

-- Insert Dummy Data into Waitlist Table
INSERT INTO Waitlist (student_id, course_id, type, position) VALUES
(1, 2, 'Course', 1);

-- Insert Dummy Data into Lab Enrollment Table
INSERT INTO LabEnrollment (student_id, section_id) VALUES
(1, 1);

-- Insert Dummy Data into Reports Table
INSERT INTO Reports (student_id, course_id, grade) VALUES
(1, 1, 'A');