package recordrangers.services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static volatile DatabaseConnection instance;
    // Private constructor to prevent instantiation
    private Connection connection;

    private DatabaseConnection() {
    	try {
            String url;
            String user = "root";
            String password = "";
            // Use MySQL for production
            //jdbc:mysql://database:3306/UniversityDB
            url = "jdbc:mysql://localhost:3306/UniversityDB";
            user = "root";
            password = "";
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return this.connection;
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
            }
        }
    }
}
