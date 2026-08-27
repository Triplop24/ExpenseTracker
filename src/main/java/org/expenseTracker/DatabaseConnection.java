package org.expenseTracker;

import java.sql.*;

public class DatabaseConnection {
    static String userName = "postgres";
    static String url = "jdbc:postgresql://localhost:5432/expense_tracker";
    static String password = "Triplop24";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, userName, password);
    }

}
