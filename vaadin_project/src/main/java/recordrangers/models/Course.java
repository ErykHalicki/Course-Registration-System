package recordrangers.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Course {
    private int courseId;
    private String courseName;
    private String courseCode;
    private int numCredits;
    private String description;
    private int maxCapacity;
    private String schedule;
    private String location;
    private int enrollment;
    private LocalDate startDate;
    private LocalDate endDate;
    private String termLabel;
    private String days;
    private LocalTime startTime;
    private LocalTime endTime;

    // No-argument constructor
    public Course() {
    }

    // Full constructor with all fields
    public Course(int courseId, String courseName, String courseCode, int numCredits, String description,
                  int maxCapacity, String schedule, String location, int enrollment,
                  LocalDate startDate, LocalDate endDate, String termLabel, String days,
                  LocalTime startTime, LocalTime endTime) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.numCredits = numCredits;
        this.description = description;
        this.maxCapacity = maxCapacity;
        this.schedule = schedule;
        this.location = location;
        this.enrollment = enrollment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termLabel = termLabel;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Overloaded constructors
    public Course(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public Course(String courseCode, int maxCapacity, int enrollment) {
        this.courseCode = courseCode;
        this.maxCapacity = maxCapacity;
        this.enrollment = enrollment;
    }
    
    public Course(int courseId, String courseCode, String courseName, int maxCapacity, String schedule) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.maxCapacity = maxCapacity;
        this.schedule = schedule;
    }

    // Getters and Setters for all fields

    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getNumCredits() {
        return numCredits;
    }
    public void setNumCredits(int numCredits) {
        this.numCredits = numCredits;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getSchedule() {
        return schedule;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public int getEnrollment() {
        return enrollment;
    }
    public void setEnrollment(int enrollment) {
        this.enrollment = enrollment;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTermLabel() {
        return termLabel;
    }
    public void setTermLabel(String termLabel) {
        this.termLabel = termLabel;
    }

    public String getDays() {
        return days;
    }
    public void setDays(String days) {
        this.days = days;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Course ID: " + courseId + "\n" +
               "Course Name: " + courseName + "\n" +
               "Course Code: " + courseCode + "\n" +
               "Number of Credits: " + numCredits + "\n" +
               "Description: " + description + "\n" +
               "Max Capacity: " + maxCapacity + "\n" +
               "Schedule: " + schedule + "\n" +
               "Location: " + location + "\n" +
               "Enrollment: " + enrollment + "\n" +
               "Start Date: " + startDate + "\n" +
               "End Date: " + endDate + "\n" +
               "Term Label: " + termLabel + "\n" +
               "Days: " + days + "\n" +
               "Start Time: " + startTime + "\n" +
               "End Time: " + endTime;
    }
}
