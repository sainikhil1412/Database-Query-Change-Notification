package org.example;

import java.sql.*;

public class OracleJdbcExample {
    public static void main(String[] args) {
        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Oracle JDBC driver loaded successfully.");

            // Establish a connection
            Connection connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1522:orcl2", "sys as sysdba", "nikhil"
            );
            System.out.println("Connected to Oracle database.");

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute the SELECT query
            String selectQuery = "SELECT name, subject FROM student";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            System.out.println(resultSet.getFetchSize());

            // Process the result set
            while (resultSet.next()) {
                System.out.println("hello");
                String productId = resultSet.getString("name");
                String productName = resultSet.getString("subject");
                System.out.println("Product ID: " + productId + ", Product Name: " + productName);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
