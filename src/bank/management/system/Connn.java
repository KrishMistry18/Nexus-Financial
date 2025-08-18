package bank.management.system;

import java.sql.*;

public class Connn {
    private Connection connection;
    public Statement statement;
    private static boolean demoMode = false; // Ensure real DB connections for the app
    
    public Connn() {
        try {
            if (demoMode) {
                System.out.println("DEMO MODE: Running without database connection for testing");
                return;
            }

            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Centralized config
            connection = DatabaseConfig.getConnection();
            if (connection != null) {
                statement = connection.createStatement();
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public Statement getStatement() {
        return statement;
    }
    
    public void closeConnection() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // AutoCloseable implementation
    public void close() {
        closeConnection();
    }
    
    // Demo mode methods
    public static void setDemoMode(boolean mode) {
        demoMode = mode;
    }
    
    public static boolean isDemoMode() {
        return demoMode;
    }
}
