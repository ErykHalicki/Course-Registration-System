package recordrangers.models;

import java.sql.*;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserType userType;
    private byte[] profilePicture;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    public User(){
        super();
    }
    
    public User(int userId, String firstName, String lastName, String email, String password, UserType userType, byte[] profilePicture, Timestamp timeCreated, Timestamp timeUpdated) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.profilePicture = profilePicture;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;

    }

    public static enum UserType{
        STUDENT, ADMIN
    }
    public UserType getUserType() {
        return userType;
    }
    public static UserType getUserTypeFromResultSet(ResultSet rs) throws SQLException {
        String typeStr = rs.getString("user_type"); // Read from column
        return (typeStr != null) ? UserType.valueOf(typeStr) : null;
    }
    
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public byte[] getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
    public Timestamp getTimeCreated() {
        return timeCreated;
    }
    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }
    public Timestamp getTimeUpdated() {
        return timeUpdated;
    }
    public void setTimeUpdated(Timestamp timeUpdated) {
        this.timeUpdated = timeUpdated;
    }
    public String toString() {
        return "User ID: " + userId + "\n" +
               "First Name: " + firstName + "\n" +
               "Last Name: " + lastName + "\n" +
               "Email: " + email + "\n" +
               "Password: " + password + "\n" +
                "User Type: " + userType + "\n" +
               "Profile Picture: " + profilePicture + "\n" +
               "Time Created: " + timeCreated + "\n" +
               "Time Updated: " + timeUpdated;
    }

}
