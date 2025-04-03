package recordrangers.models;

import java.time.LocalTime;

public class LabSession {
    private int sectionId;
    private int labId;
    private int capacity;
    private String days;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private String labName;  // Added labName based on the lab it is linked to

    public LabSession(int sectionId, int labId, int capacity, String days, LocalTime startTime, LocalTime endTime, String location, String labName) {
        this.sectionId = sectionId;
        this.labId = labId;
        this.capacity = capacity;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.labName = labName;
    }

    // Getters and Setters
    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }
}