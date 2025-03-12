package recordrangers.services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/UniversityDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}