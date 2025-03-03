package recordrangers.models;
import java.sql.*;

public class Admin extends User{
    private String role;
    public Admin(){
        super();
    }
    public Admin(int userId, String firstName, String lastName, String email, String password, UserType userType, byte[] profilePicture, Timestamp timeCreated, Timestamp timeUpdated, String role){
        super(userId, firstName, lastName, email, password, User.UserType.ADMIN, profilePicture, timeCreated, timeUpdated);
        this.role = role;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return "Admin{" +
                "role='" + role + '\'' +
                '}';
    }
}