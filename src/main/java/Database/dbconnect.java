package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnect {

    private static final String URL = "jdbc:mysql://mysql-215c945b-ademsansa7-4324.j.aivencloud.com:28053/slouma?useSSL=false&serverTimezone=UTC";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_HLKPMcODDZfHGeEF1Kl";


    // Singleton instance
    private static dbconnect instance;

    // Single Connection object
    private Connection connection;

    /// /dthdhtdht
    // Private constructor
    private dbconnect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    // Public access point
    public static synchronized dbconnect getInstance() {
        if (instance == null) {
            instance = new dbconnect();
        }
        return instance;
    }

    // Get the single connection
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Re-establishing database connection...");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Failed to reconnect to database: " + e.getMessage());
        }
        return connection;
    }
}
