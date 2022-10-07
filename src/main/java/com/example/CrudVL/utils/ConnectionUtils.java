package com.example.CrudVL.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null){
            try {
                String usuario = "postgres";
                String senha = "vertrigo";
                Class.forName("org.postgresql.Driver");// Para quem for usar Postgres
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/locadora",usuario, senha);// Para quem for usar Postgres

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);

            }
        }
        return connection;
    }
}
