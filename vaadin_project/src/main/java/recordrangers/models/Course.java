package recordrangers.models;

public class Course {
    private String name;
    private int capacity;
    private int enrollment;

    public Course(String name, int capacity, int enrollment) {
        this.name = name;
        this.capacity = capacity;
        this.enrollment = enrollment;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrollment() {
        return enrollment;
    }
}