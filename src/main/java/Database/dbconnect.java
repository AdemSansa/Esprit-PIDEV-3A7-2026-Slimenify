package Database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class dbconnect {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties props = new Properties();
        try (InputStream input = dbconnect.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("FATAL: config.properties not found in classpath");
                throw new RuntimeException("Unable to find config.properties");
            }
            props.load(input);
            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
            System.out.println("Database URL loaded: " + URL);
            System.out.println("Database USER loaded: " + USER);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load database configuration", ex);
        }
    }

    // Singleton instance
    private static dbconnect instance;

    // Single Connection object
    private Connection connection;

    // Private constructor
    private dbconnect() {
        try {
            if (URL == null || USER == null || PASSWORD == null) {
                 throw new RuntimeException("Database credentials are not properly loaded from config.properties");
            }
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
