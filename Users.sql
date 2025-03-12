-- Mock data for Users
INSERT INTO User (first_name, last_name, email, password, profile_photo) VALUES
('John', 'Doe', 'john.doe@example.com', 'password123', 'https://example.com/profiles/john.jpg'), -- Student
('Jane', 'Smith', 'jane.smith@example.com', 'password456', 'https://example.com/profiles/jane.jpg'), -- Admin
('Alice', 'Johnson', 'alice.johnson@example.com', 'password789', 'https://example.com/profiles/alice.jpg'), -- Student
('Bob', 'Brown', 'bob.brown@example.com', 'password101', 'https://example.com/profiles/bob.jpg'), -- Student
('Charlie', 'Davis', 'charlie.davis@example.com', 'password202', 'https://example.com/profiles/charlie.jpg'), -- Admin
('Emily', 'Clark', 'emily.clark@example.com', 'password606', 'https://example.com/profiles/emily.jpg'), -- Student
('Michael', 'Lewis', 'michael.lewis@example.com', 'password707', 'https://example.com/profiles/michael.jpg'), -- Student
('Sophia', 'Walker', 'sophia.walker@example.com', 'password808', 'https://example.com/profiles/sophia.jpg'), -- Student
('Daniel', 'Hall', 'daniel.hall@example.com', 'password909', 'https://example.com/profiles/daniel.jpg'), -- Admin
('Olivia', 'Young', 'olivia.young@example.com', 'password1010', 'https://example.com/profiles/olivia.jpg'); -- Admin

-- Mock data for Students
INSERT INTO Student (student_id, enrollment_date, status) VALUES
(1, '2020-09-2', 'Graduated'), -- 1
(3, '2022-09-2', 'Active'), -- 3
(4, '2019-09-2', 'Inactive'), -- 4
(6, '2019-09-2', 'Active'), -- 6
(7, '2018-09-2', 'Graduated'), -- 7
(8, '2022-09-2', 'Active'); -- 8

-- Mock data for Admins
INSERT INTO Admin (admin_id, role) VALUES
(2, 'Director of Sciences'), -- 2
(5, 'Student Admin'), -- 5
(9, 'Student Admin'), -- 9
(10, 'Director of Humanities'); -- 10
