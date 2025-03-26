package recordrangers.services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://database:3306/UniversityDB";
	//private static final String URL = "jdbc:mysql://localhost:3306/UniversityDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static volatile DatabaseConnection instance;
    // Private constructor to prevent instantiation
    private Connection connection;

    private DatabaseConnection() {
    	try {
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
