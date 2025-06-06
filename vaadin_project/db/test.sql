CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    profile_photo TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_last TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_type ENUM('Student', 'Admin') NOT NULL
);

CREATE TABLE Student (
    student_id INT PRIMARY KEY,
    enrollment_date DATE,
    status ENUM('Active', 'Inactive', 'Graduated') NOT NULL,
    FOREIGN KEY (student_id) REFERENCES User(user_id)
);

CREATE TABLE Admin (
    admin_id INT PRIMARY KEY,
    role VARCHAR(50) NOT NULL,
    FOREIGN KEY (admin_id) REFERENCES User(user_id)
);

CREATE TABLE Course (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    course_code VARCHAR(20) NOT NULL,
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

CREATE TABLE Labs (
    lab_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    lab_name VARCHAR(100),
    FOREIGN KEY (course_id) REFERENCES Course(course_id)
);

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

CREATE TABLE LabEnrollment (
    student_id INT NOT NULL,
    section_id INT NOT NULL,
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (student_id, section_id),
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (section_id) REFERENCES LabSection(section_id)
);

CREATE TABLE Reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_id INT,
    grade CHAR(2),
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (course_id) REFERENCES Course(course_id)
);

INSERT INTO Course (course_name, course_code, num_credits, description, capacity, start_date, end_date, term_label, days, start_time, end_time, location) VALUES
('Intro to Computer Science', 'COSC 101', 3, 'An introductory course covering the basics of computer science and programming.', 120, '2025-09-02', '2025-12-05', '2025 Winter T1', 'Monday-Wednesday', '09:30:00', '11:00:00', 'LIB 305'),
('Calculus 1', 'MATH 101', 3, 'A foundational course in differential and integral calculus.', 80, '2025-09-02', '2025-12-05', '2025 Winter T1', 'Wednesday-Friday', '08:00:00', '09:30:00', 'ART 103'),
('English Composition', 'ENG 101', 3, 'Develop writing skills through essays, research papers and critical analysis.', 30, '2025-09-02', '2025-12-05', '2025 Winter T1', 'Monday-Wednesday', '11:00:00', '12:30:00', 'FIP 112'),
('Intro to Databases', 'COSC 304', 3, 'Intro to MySQL and relational databases.', 120, '2025-09-02', '2025-12-05', '2025 Winter T1', 'Monday-Tuesday', '09:30:00', '11:00:00', 'ASC 140'),
('Analysis of Algorithms', 'COSC 320', 3, 'Analysis of algorithms, Big-O complexity and optimization.', 120, '2025-09-02', '2025-12-05', '2025 Winter T1', 'Monday-Wednesday', '10:00:00', '11:30:00', 'SCI 330'),
('Web Programming', 'COSC 360', 3, 'Intro HTML, CSS and JavaScript, as well as back end integration.', 120, '2025-09-02', '2025-12-05', '2025 Winter T1', 'Wednesday-Friday', '08:00:00', '09:30:00', 'EME 0500'),
('Intro to Data Structures', 'COSC 222', 3, 'Introduction to data structures, their implementations and algorithms.', 120, '2025-09-02', '2025-12-05', '2025 Winter T1', 'Wednesday-Friday', '09:30:00', '11:00:00', 'COM 301'),
('Intro to Macroeconomics', 'ECON 102', 3, 'Introduction to Macroeconomics. Covers basic concepts and governmental policy.', 120, '2025-09-02', '2025-12-05', '2025 Winter T1', 'Monday-Wednesday', '17:00:00', '18:30:00', 'Online - FIP 201'),
('Software Engineering', 'COSC 310', 3, 'Introduction to the software engineering processes and techniques.', 120, '2025-09-02', '2025-12-05', '2025 Winter T1', 'Wednesday-Friday', '13:30:00', '15:00:00', 'ART 360');

INSERT INTO Course (course_name, course_code, num_credits, description, capacity, start_date, end_date, term_label, days, start_time, end_time, location) VALUES
('Intro to Computer Science', 'COSC 101', 3, 'An introductory course covering the basics of computer science and programming.', 120, '2025-01-06', '2025-04-10', '2025 Winter T2', 'Monday-Wednesday', '09:30:00', '11:00:00', 'LIB 305'),
('Calculus 1', 'MATH 101', 3, 'A foundational course in differential and integral calculus.', 80, '2025-01-06', '2025-04-10', '2025 Winter T2', 'Wednesday-Friday', '08:00:00', '09:30:00', 'ART 103'),
('English Composition', 'ENG 101', 3, 'Develop writing skills through essays, research papers and critical analysis.', 30, '2025-01-06', '2025-04-10', '2025 Winter T2', 'Monday-Wednesday', '11:00:00', '12:30:00', 'FIP 112'),
('Intro to Databases', 'COSC 304', 3, 'Intro to MySQL and relational databases.', 120, '2025-01-06', '2025-04-10', '2025 Winter T2', 'Monday-Tuesday', '09:30:00', '11:00:00', 'ASC 140'),
('Analysis of Algorithms', 'COSC 320', 3, 'Analysis of algorithms, Big-O complexity and optimization.', 120, '2025-01-06', '2025-04-10', '2025 Winter T2', 'Monday-Wednesday', '10:00:00', '11:30:00', 'SCI 330'),
('Web Programming', 'COSC 360', 3, 'Intro HTML, CSS and JavaScript, as well as back end integration.', 120, '2025-01-06', '2025-04-10', '2025 Winter T2', 'Wednesday-Friday', '08:00:00', '09:30:00', 'EME 0500');

INSERT INTO User (first_name, last_name, email, password, profile_photo, user_type) VALUES
('John', 'Doe', 'john.doe@example.com', 'password123', 'https://example.com/profiles/john.jpg', 'Student'),
('Jane', 'Smith', 'jane.smith@example.com', 'password456', 'https://example.com/profiles/jane.jpg', 'Admin'),
('Alice', 'Johnson', 'alice.johnson@example.com', 'password789', 'https://example.com/profiles/alice.jpg', 'Student'),
('Bob', 'Brown', 'bob.brown@example.com', 'password101', 'https://example.com/profiles/bob.jpg', 'Student'),
('Charlie', 'Davis', 'charlie.davis@example.com', 'password202', 'https://example.com/profiles/charlie.jpg', 'Admin'),
('Emily', 'Clark', 'emily.clark@example.com', 'password606', 'https://example.com/profiles/emily.jpg', 'Student'),
('Michael', 'Lewis', 'michael.lewis@example.com', 'password707', 'https://example.com/profiles/michael.jpg', 'Student'),
('Sophia', 'Walker', 'sophia.walker@example.com', 'password808', 'https://example.com/profiles/sophia.jpg', 'Student'),
('Daniel', 'Hall', 'daniel.hall@example.com', 'password909', 'https://example.com/profiles/daniel.jpg', 'Admin'),
('Olivia', 'Young', 'olivia.young@example.com', 'password1010', 'https://example.com/profiles/olivia.jpg', 'Admin');

INSERT INTO Student (student_id, enrollment_date, status) VALUES
(1, '2020-09-2', 'Graduated'), -- 1
(3, '2022-09-2', 'Active'), -- 3
(4, '2019-09-2', 'Inactive'), -- 4
(6, '2019-09-2', 'Active'), -- 6
(7, '2018-09-2', 'Graduated'), -- 7
(8, '2022-09-2', 'Active'); -- 8

INSERT INTO Admin (admin_id, role) VALUES
(2, 'Director of Sciences'), -- 2
(5, 'Student Admin'), -- 5
(9, 'Student Admin'), -- 9
(10, 'Director of Humanities'); -- 10
