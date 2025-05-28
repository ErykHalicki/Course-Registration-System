# Course Registration System 

COSC 310 project

# Course Enrollment and Management System

## Overview

This project is a Course Enrollment and Management System designed to cater to two primary user roles: **students** and **admins**. The system supports secure account creation and login, profile management, course viewing, enrollment, and administration functionalities, with additional features planned for future development.

## Steps to Build and Run the Project

```sh
git clone https://github.com/ErykHalicki/RecordRangers.git
cd RecordRangers/vaadin_project
docker compose up --build -d
```

You can now access the project at localhost:8080! please email our group if it isnt working, i tested the compilation on my laptop but im not 100% sure if itll work on someone elses computer

### demo login info

**Student:**\
[john.doe@example.com](mailto\:john.doe@example.com)\
password123

**Admin:**\
[olivia.young@example.com](mailto\:olivia.young@example.com)\
password1010

## Current Features

### General Features (Completed)

- **User Account Creation:** Users can create an account using their email address.
- **User Profile:** Every user has a profile that includes:
  - Student / Admin status
  - Year level
  - Major
  - Email
  - Profile photo
- **Authentication:** Secure login and logout using the registered email.
- **Course Viewing:** Ability to view courses along with enrollment numbers and capacity.
- **Course Search:** Users can search for courses by name or course number.

### Student Features (Completed)

- **Course Enrollment:** Eligible student users can enroll in courses.
- **Course History:** Students can view their past and current courses, including completion status.
- **Waitlist:** Students can add themselves to a waitlist if a course is full.

### Admin Features (Completed)

- **Student Search:** Admins can search, sort, and filter students by attributes such as name, course, or grade range.
- **Course Management:** Admins have the ability to insert and remove courses.

## Pending Features

### General Features

- **Profile Updates:** Users will be able to update certain personal profile information (email and profile photo).

### Student Features

- **Conflict Notifications:** Students will be notified if an enrolled course conflicts with another course they are taking.
- **Enrollment Notifications:** Students will receive email notifications when they are enrolled in or removed from courses.

### Admin Features

- **Student Profile Management:** Admins will be able to:
  - Add, edit, delete, and view student profiles (including details such as name, ID, and contact information).
- **Course Assignment and Grading:** Admins will be able to assign students to courses and record their grades.
- **Reporting:** Admins will be able to generate detailed student reports, including:
  - Enrolled courses and grades.
  - Overall performance ranking.
  - Options for exportable formats (e.g., CSV).
- **Waitlist Management:** Admins will be able to add students to course waitlists when enrollment is full.




