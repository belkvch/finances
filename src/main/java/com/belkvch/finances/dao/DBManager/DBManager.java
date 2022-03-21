package com.belkvch.finances.dao.DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    public static String URL = "jdbc:postgresql://localhost:5432/finances_bd";
    public static String USER = "postgres";
    public static String PASS = "12345678";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);

    }
}
