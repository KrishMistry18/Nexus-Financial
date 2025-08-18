package bank.management.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final String CONFIG_FILE = "database.properties";
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/bankSystem";
    private static final String DEFAULT_USER = "krish";
    private static final String DEFAULT_PASSWORD = "krish@0412";
    
    private static Properties properties;
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Could not load database.properties, using default values");
            properties.setProperty("db.url", DEFAULT_URL);
            properties.setProperty("db.user", DEFAULT_USER);
            properties.setProperty("db.password", DEFAULT_PASSWORD);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url", DEFAULT_URL);
        String user = properties.getProperty("db.user", DEFAULT_USER);
        String password = properties.getProperty("db.password", DEFAULT_PASSWORD);
        
        return DriverManager.getConnection(url, user, password);
    }
    
    public static String getUrl() {
        return properties.getProperty("db.url", DEFAULT_URL);
    }
    
    public static String getUser() {
        return properties.getProperty("db.user", DEFAULT_USER);
    }
    
    public static String getPassword() {
        return properties.getProperty("db.password", DEFAULT_PASSWORD);
    }
}
