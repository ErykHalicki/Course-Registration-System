package recordrangers.services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	 private static final String[] POSSIBLE_URLS = {
		        "jdbc:mysql://127.0.0.1:3306/UniversityDB",
		        "jdbc:mysql://localhost:3306/UniversityDB",
		        "jdbc:mysql://database:3306/UniversityDB",
		        "jdbc:mysql://mysql_db:3306/UniversityDB"
		    };
		    private static final String USER = "root";
		    private static final String PASSWORD = "";

		    private static volatile DatabaseConnection instance;
		    private Connection connection;

		    private DatabaseConnection() {
		        connection = tryConnections();
		    }

		    private Connection tryConnections() {
		        for (String url : POSSIBLE_URLS) {
		            try {
		                System.out.println("Attempting to connect to: " + url);
		                Connection conn = DriverManager.getConnection(url, USER, PASSWORD);
		                System.out.println("Successfully connected to: " + url);
		                return conn;
		            } catch (SQLException e) {
		                System.out.println("Connection failed for " + url + ": " + e.getMessage());
		            }
		        }
		        throw new RuntimeException("Could not connect to any of the specified URLs");
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
        // If connection is closed or invalid, reopen
        if (connection == null || connection.isClosed() || !connection.isValid(5)) {
            synchronized (this) {
                if (connection == null || connection.isClosed() || !connection.isValid(5)) {
                    connection = tryConnections();
                }
            }
        }
        return connection;
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
