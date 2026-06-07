package com.Diego.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/hamburgueseria_crm?serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "Admin1234";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(" Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.out.println(" ERROR: No se encontró el driver de MySQL");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}