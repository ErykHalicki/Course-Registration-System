package recordrangers.models;

import java.sql.Timestamp;

public class User {
    private int userId; // Primary key, auto-incremented
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String profilePhoto; // URL or file path
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    public User() {
    
    }
    
    // Constructor for creating a new user (without userId and timestamps)
    public User(String firstName, String lastName, String email, String password, String profilePhoto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.profilePhoto = profilePhoto;
    }
    
    // Constructor for retrieving user from DB
    public User(int userId, String firstName, String lastName, String email, String password, String profilePhoto, Timestamp createdAt, Timestamp updatedAt) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getProfilePhoto() { return profilePhoto; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
