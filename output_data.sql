-- MySQL dump 10.13  Distrib 9.2.0, for macos15.2 (arm64)
--
-- Host: localhost    Database: UniversityDB
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `Admin`
--

LOCK TABLES `Admin` WRITE;
/*!40000 ALTER TABLE `Admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `Admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Course`
--

LOCK TABLES `Course` WRITE;
/*!40000 ALTER TABLE `Course` DISABLE KEYS */;
INSERT INTO `Course` VALUES (3,'Introduction to Computer Science','CS101',3,'Basic computer science concepts and programming',50,'2025-09-01','2025-12-15','Fall 2025','Mon, Wed, Fri','09:00:00','10:15:00','Room 101'),(4,'Data Structures and Algorithms','CS201',4,'Intermediate data structures and algorithm design',50,'2025-09-01','2025-12-15','Fall 2025','Tue, Thu','10:30:00','11:45:00','Room 102'),(5,'Web Development','CS301',3,'Introduction to web development with HTML, CSS, and JavaScript',50,'2025-09-01','2025-12-15','Fall 2025','Mon, Wed','13:00:00','14:15:00','Room 103'),(6,'Database Management','CS401',3,'Database design, SQL, and data modeling',50,'2025-09-01','2025-12-15','Fall 2025','Mon, Fri','14:30:00','15:45:00','Room 104'),(7,'Operating Systems','CS501',4,'Operating systems concepts and their implementation',50,'2025-09-01','2025-12-15','Fall 2025','Tue, Thu','16:00:00','17:15:00','Room 105'),(8,'Intro to Computer Science','COSC 101',3,'An introductory course covering the basics of computer science and programming.',120,'2025-09-02','2025-12-05','2025 Winter T1','Monday-Wednesday','09:30:00','11:00:00','LIB 305'),(9,'Calculus 1','MATH 101',3,'A foundational course in differential and integral calculus.',80,'2025-09-02','2025-12-05','2025 Winter T1','Wednesday-Friday','08:00:00','09:30:00','ART 103'),(10,'English Composition','ENG 101',3,'Develop writing skills through essays, research papers and critical analysis.',30,'2025-09-02','2025-12-05','2025 Winter T1','Monday-Wednesday','11:00:00','12:30:00','FIP 112'),(11,'Intro to Databases','COSC 304',3,'Intro to MySQL and relational databases.',120,'2025-09-02','2025-12-05','2025 Winter T1','Monday-Tuesday','09:30:00','11:00:00','ASC 140'),(12,'Analysis of Algorithms','COSC 320',3,'Analysis of algorithms, Big-O complexity and optimization.',120,'2025-09-02','2025-12-05','2025 Winter T1','Monday-Wednesday','10:00:00','11:30:00','SCI 330'),(13,'Web Programming','COSC 360',3,'Intro HTML, CSS and JavaScript, as well as back end integration.',120,'2025-09-02','2025-12-05','2025 Winter T1','Wednesday-Friday','08:00:00','09:30:00','EME 0500'),(14,'Intro to Data Structures','COSC 222',3,'Introduction to data structures, their implementations and algorithms.',120,'2025-09-02','2025-12-05','2025 Winter T1','Wednesday-Friday','09:30:00','11:00:00','COM 301'),(15,'Intro to Macroeconomics','ECON 102',3,'Introduction to Macroeconomics. Covers basic concepts and governmental policy.',120,'2025-09-02','2025-12-05','2025 Winter T1','Monday-Wednesday','17:00:00','18:30:00','Online - FIP 201'),(16,'Software Engineering','COSC 310',3,'Introduction to the software engineering processes and techniques.',120,'2025-09-02','2025-12-05','2025 Winter T1','Wednesday-Friday','13:30:00','15:00:00','ART 360');
/*!40000 ALTER TABLE `Course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Enrollments`
--

LOCK TABLES `Enrollments` WRITE;
/*!40000 ALTER TABLE `Enrollments` DISABLE KEYS */;
INSERT INTO `Enrollments` VALUES (23,3,'2000-01-01 08:00:00','Enrolled','A'),(23,4,'2000-01-01 08:00:00','Enrolled','B'),(23,5,'2000-01-01 08:00:00','Enrolled','B'),(23,6,'2000-01-01 08:00:00','Enrolled','B'),(25,3,'2000-01-01 08:00:00','Enrolled','B'),(25,4,'2000-01-01 08:00:00','Enrolled','D'),(25,5,'2000-01-01 08:00:00','Enrolled','C'),(25,6,'2000-01-01 08:00:00','Enrolled','A'),(25,16,'2000-01-01 08:00:00','Enrolled','F'),(26,7,'2000-01-01 08:00:00','Dropped','B'),(26,8,'2000-01-01 08:00:00','Enrolled','A'),(26,9,'2000-01-01 08:00:00','Enrolled','C'),(26,10,'2000-01-01 08:00:00','Completed','C');
/*!40000 ALTER TABLE `Enrollments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `LabEnrollment`
--

LOCK TABLES `LabEnrollment` WRITE;
/*!40000 ALTER TABLE `LabEnrollment` DISABLE KEYS */;
INSERT INTO `LabEnrollment` VALUES (23,16,'2025-09-01 16:00:00'),(24,17,'2025-09-01 17:30:00'),(25,18,'2025-09-01 20:00:00'),(26,19,'2025-09-01 21:30:00'),(27,20,'2025-09-02 16:30:00'),(28,21,'2025-09-02 15:00:00'),(29,22,'2025-09-02 18:00:00'),(30,23,'2025-09-02 16:30:00'),(31,24,'2025-09-02 17:00:00'),(32,25,'2025-09-02 15:00:00');
/*!40000 ALTER TABLE `LabEnrollment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Labs`
--

LOCK TABLES `Labs` WRITE;
/*!40000 ALTER TABLE `Labs` DISABLE KEYS */;
INSERT INTO `Labs` VALUES (2,3,'CS101 Lab 1'),(3,4,'CS201 Lab 1'),(4,5,'CS301 Lab 1'),(5,6,'CS401 Lab 1'),(6,7,'CS501 Lab 1'),(7,8,'COSC 101 Lab 1'),(8,9,'MATH 101 Lab 1'),(9,10,'ENG 101 Lab 1'),(10,11,'COSC 304 Lab 1'),(11,12,'COSC 320 Lab 1'),(12,13,'COSC 360 Lab 1'),(13,14,'COSC 222 Lab 1'),(14,15,'ECON 102 Lab 1'),(15,16,'COSC 310 Lab 1');
/*!40000 ALTER TABLE `Labs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `LabSection`
--

LOCK TABLES `LabSection` WRITE;
/*!40000 ALTER TABLE `LabSection` DISABLE KEYS */;
INSERT INTO `LabSection` VALUES (16,2,20,'Mon, Wed','09:00:00','10:15:00','Room 101'),(17,3,25,'Tue, Thu','10:30:00','11:45:00','Room 102'),(18,4,30,'Mon, Wed','13:00:00','14:15:00','Room 103'),(19,5,20,'Mon, Fri','14:30:00','15:45:00','Room 104'),(20,6,25,'Tue, Thu','16:00:00','17:15:00','Room 105'),(21,7,35,'Mon, Wed','09:30:00','11:00:00','LIB 305'),(22,8,40,'Wed, Fri','08:00:00','09:30:00','ART 103'),(23,9,20,'Mon, Wed','11:00:00','12:30:00','FIP 112'),(24,10,35,'Mon, Tue','09:30:00','11:00:00','ASC 140'),(25,11,30,'Mon, Wed','10:00:00','11:30:00','SCI 330'),(26,12,25,'Wed, Fri','08:00:00','09:30:00','EME 0500'),(27,13,20,'Tue, Thu','09:30:00','11:00:00','COM 301'),(28,14,20,'Mon, Wed','17:00:00','18:30:00','Online - FIP 201'),(29,15,30,'Wed, Fri','13:30:00','15:00:00','ART 360');
/*!40000 ALTER TABLE `LabSection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Reports`
--

LOCK TABLES `Reports` WRITE;
/*!40000 ALTER TABLE `Reports` DISABLE KEYS */;
INSERT INTO `Reports` VALUES (2,23,3,'A'),(3,24,4,'B+'),(4,25,5,'A-'),(5,26,6,'B'),(6,27,7,'B+'),(7,28,8,'C'),(8,29,9,'A'),(9,30,10,'B-'),(10,31,11,'C+'),(11,32,12,'A');
/*!40000 ALTER TABLE `Reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Student`
--

LOCK TABLES `Student` WRITE;
/*!40000 ALTER TABLE `Student` DISABLE KEYS */;
INSERT INTO `Student` VALUES (23,'2000-01-01','Active'),(24,'2000-01-01','Active'),(25,'2000-01-01','Graduated'),(26,'2001-01-01','Inactive'),(27,'2002-01-01','Active'),(28,'2002-01-01','Graduated'),(29,'2005-01-01','Graduated'),(30,'2006-01-01','Inactive'),(31,'2004-01-01','Active'),(32,'2007-01-01','Inactive');
/*!40000 ALTER TABLE `Student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (23,'John','Doe','john.doe@example.com','password123','https://example.com/profiles/john.jpg','Student','2025-03-13 00:17:16','2025-03-13 00:17:16'),(24,'Jane','Smith','jane.smith@example.com','password456','https://example.com/profiles/jane.jpg','Student','2025-03-13 00:17:16','2025-03-13 00:17:16'),(25,'Alice','Johnson','alice.johnson@example.com','password789','https://example.com/profiles/alice.jpg','Student','2025-03-13 00:17:16','2025-03-13 00:17:16'),(26,'Bob','Brown','bob.brown@example.com','password101','https://example.com/profiles/bob.jpg','Student','2025-03-13 00:17:16','2025-03-13 00:17:16'),(27,'Charlie','Davis','charlie.davis@example.com','password202','https://example.com/profiles/charlie.jpg','Student','2025-03-13 00:17:16','2025-03-13 00:17:16'),(28,'Emily','Clark','emily.clark@example.com','password606','https://example.com/profiles/emily.jpg','Student','2025-03-13 00:17:16','2025-03-13 00:17:16'),(29,'Michael','Lewis','michael.lewis@example.com','password707','https://example.com/profiles/michael.jpg','Student','2025-03-13 00:17:16','2025-03-13 00:17:16'),(30,'Sophia','Walker','sophia.walker@example.com','password808','https://example.com/profiles/sophia.jpg','Student','2025-03-13 00:17:16','2025-03-13 00:17:16'),(31,'Daniel','Hall','daniel.hall@example.com','password909','https://example.com/profiles/daniel.jpg','Student','2025-03-13 00:17:16','2025-03-13 00:17:16'),(32,'Olivia','Young','olivia.young@example.com','password1010','https://example.com/profiles/olivia.jpg','Student','2025-03-13 00:17:16','2025-03-13 00:17:16');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Waitlist`
--

LOCK TABLES `Waitlist` WRITE;
/*!40000 ALTER TABLE `Waitlist` DISABLE KEYS */;
INSERT INTO `Waitlist` VALUES (2,23,3,16,'Lab',1),(3,24,4,17,'Course',2),(4,25,5,18,'Lab',3),(5,26,6,19,'Course',1),(6,27,7,20,'Lab',4),(7,28,8,21,'Course',5),(8,29,9,22,'Lab',2),(9,30,10,23,'Course',6),(10,31,11,24,'Lab',3),(11,32,12,25,'Course',7);
/*!40000 ALTER TABLE `Waitlist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-19 16:38:21
