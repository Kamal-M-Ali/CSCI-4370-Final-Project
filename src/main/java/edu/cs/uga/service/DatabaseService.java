package edu.cs.uga.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;

/**
 * A singleton class for accessing the MySql database.
 * Use the {@link DatabaseService#getInstance()} method to get the object.
 */
@Service
public class DatabaseService {
    // defining constants for the SQL statements
    private static final String KEY = "jdbc:mysql://localhost:33306/review_site?user=root&password=mysqlpass";
    private static DatabaseService instance;
    private final Connection connection;


    private DatabaseService() throws SQLException
    {
        this.connection = DriverManager.getConnection(KEY);
    }

    /**
     * Will create and return a DatabaseConnection object if one does not exist, otherwise it will return the existing
     * object.
     * @return the {@link DatabaseService} object
     */
    public static DatabaseService getInstance()
    {
        if (instance == null) {
            try {
                instance = new DatabaseService();
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public ResponseEntity<String> log(SQLException e) {
        System.out.println("SQLException: " + e.getMessage());
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());

        return ResponseEntity.internalServerError().body("Failed to execute query.");
    }
}
