-- Mock Courses (2025 Winter T1)
-- Note: make course code not unique if you want to have the same course in both terms
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

-- Mock Courses (2025 Winter T2)
INSERT INTO Course (course_name, course_code, num_credits, description, capacity, start_date, end_date, term_label, days, start_time, end_time, location) VALUES
('Intro to Computer Science', 'COSC 101', 3, 'An introductory course covering the basics of computer science and programming.', 120, '2025-01-06', '2025-04-10', '2025 Winter T2', 'Monday-Wednesday', '09:30:00', '11:00:00', 'LIB 305'),
('Calculus 1', 'MATH 101', 3, 'A foundational course in differential and integral calculus.', 80, '2025-01-06', '2025-04-10', '2025 Winter T2', 'Wednesday-Friday', '08:00:00', '09:30:00', 'ART 103'),
('English Composition', 'ENG 101', 3, 'Develop writing skills through essays, research papers and critical analysis.', 30, '2025-01-06', '2025-04-10', '2025 Winter T2', 'Monday-Wednesday', '11:00:00', '12:30:00', 'FIP 112'),
('Intro to Databases', 'COSC 304', 3, 'Intro to MySQL and relational databases.', 120, '2025-01-06', '2025-04-10', '2025 Winter T2', 'Monday-Tuesday', '09:30:00', '11:00:00', 'ASC 140'),
('Analysis of Algorithms', 'COSC 320', 3, 'Analysis of algorithms, Big-O complexity and optimization.', 120, '2025-01-06', '2025-04-10', '2025 Winter T2', 'Monday-Wednesday', '10:00:00', '11:30:00', 'SCI 330'),
('Web Programming', 'COSC 360', 3, 'Intro HTML, CSS and JavaScript, as well as back end integration.', 120, '2025-01-06', '2025-04-10', '2025 Winter T2', 'Wednesday-Friday', '08:00:00', '09:30:00', 'EME 0500');

-- Labs
INSERT INTO Labs(course_id, lab_name) VALUES (1, 'COSC 101 Lab LO1');

INSERT INTO LabSection(lab_id, capacity, days, start_time, end_time, location) VALUES
(1, 60, 'Monday', '08:00:00', '10:00:00', "FIP 104");



