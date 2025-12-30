package br.db;

import java.sql.Connection;
import java.sql.DriverManager;

public final class ConnectionFactory {
    private ConnectionFactory() {}

    private static final String DB_NAME = "Desafio-Dio-MySql";
    private static final String URL =
            "jdbc:mysql://localhost:3306/" + DB_NAME +
                    "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER = "LuizCarvalho";
    private static final String PASS = "123456";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao conectar no MySQL: " + e.getMessage(), e);
        }
    }
}
