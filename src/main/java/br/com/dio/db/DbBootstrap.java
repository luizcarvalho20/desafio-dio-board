package br.com.dio.board.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public final class DbBootstrap {
    private DbBootstrap() {}

    public static void ensureSchema() {
        String sql = readResource("/schema.sql");

        for (String stmt : sql.split(";")) {
            String s = stmt.trim();
            if (s.isBlank()) continue;

            try (Connection con = ConnectionFactory.getConnection();
                 Statement st = con.createStatement()) {
                st.execute(s);
            } catch (Exception e) {
                throw new RuntimeException("Erro criando schema: " + e.getMessage(), e);
            }
        }
    }

    private static String readResource(String path) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(DbBootstrap.class.getResourceAsStream(path)))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível ler resource: " + path, e);
        }
    }
}
