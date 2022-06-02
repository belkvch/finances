package com.belkvch.finances.financesApp.dao.DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    public static String URL = "jdbc:postgresql://localhost:5433/finances_bd";
    public static String USER = "postgres";
    public static String PASS = "12345678";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);

    }
}
