package br.com.dio.board.dao;

import br.com.dio.board.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDao {

    public void printTimePerColumn(long boardId) {
        String sql = """
        SELECT
          c.id AS card_id,
          bc.name AS coluna,
          TIMESTAMPDIFF(SECOND, h.entered_at, COALESCE(h.left_at, NOW())) AS segundos_na_coluna
        FROM card_column_history h
        JOIN cards c ON c.id = h.card_id
        JOIN board_columns bc ON bc.id = h.to_column_id
        WHERE c.board_id = ?
        ORDER BY c.id, bc.position
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, boardId);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n=== RELATÓRIO: Tempo por Coluna (segundos) ===");
                long lastCard = -1;
                while (rs.next()) {
                    long cardId = rs.getLong("card_id");
                    if (cardId != lastCard) {
                        System.out.println("\nCard #" + cardId);
                        lastCard = cardId;
                    }
                    System.out.printf(" - %s: %d s%n", rs.getString("coluna"), rs.getLong("segundos_na_coluna"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro no relatório: " + e.getMessage(), e);
        }
    }

    public void printBlockedIntervals(long boardId) {
        String sql = """
        SELECT
          c.id AS card_id,
          e1.reason AS motivo_bloqueio,
          e2.reason AS motivo_desbloqueio,
          TIMESTAMPDIFF(SECOND, e1.event_at, e2.event_at) AS segundos_bloqueado
        FROM cards c
        JOIN card_block_events e1 ON e1.card_id = c.id AND e1.action = 'BLOCK'
        JOIN card_block_events e2 ON e2.card_id = c.id AND e2.action = 'UNBLOCK' AND e2.event_at > e1.event_at
        WHERE c.board_id = ?
        ORDER BY c.id, e1.event_at
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, boardId);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n=== RELATÓRIO: Bloqueios (segundos + motivos) ===");
                while (rs.next()) {
                    System.out.printf("Card #%d | bloqueado: %ds | motivo: %s | desbloqueio: %s%n",
                            rs.getLong("card_id"),
                            rs.getLong("segundos_bloqueado"),
                            rs.getString("motivo_bloqueio"),
                            rs.getString("motivo_desbloqueio")
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro no relatório: " + e.getMessage(), e);
        }
    }
}
