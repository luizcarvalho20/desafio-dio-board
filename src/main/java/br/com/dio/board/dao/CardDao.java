package br.com.dio.board.dao;

import br.com.dio.board.db.ConnectionFactory;
import br.com.dio.board.domain.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardDao {

    public long create(long boardId, long columnId, String title, String description) {
        String sql = "INSERT INTO cards(board_id,column_id,title,description) VALUES (?,?,?,?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, boardId);
            ps.setLong(2, columnId);
            ps.setString(3, title);
            ps.setString(4, description);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new RuntimeException("Não foi possível obter o ID do card.");
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Optional<Card> findById(long cardId) {
        String sql = "SELECT * FROM cards WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, cardId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new Card(
                        rs.getLong("id"),
                        rs.getLong("board_id"),
                        rs.getLong("column_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("blocked"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<Card> findByBoard(long boardId) {
        String sql = "SELECT * FROM cards WHERE board_id=? ORDER BY id DESC";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, boardId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Card> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new Card(
                            rs.getLong("id"),
                            rs.getLong("board_id"),
                            rs.getLong("column_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getBoolean("blocked"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ));
                }
                return list;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void updateColumn(long cardId, long newColumnId) {
        String sql = "UPDATE cards SET column_id=? WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, newColumnId);
            ps.setLong(2, cardId);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void setBlocked(long cardId, boolean blocked) {
        String sql = "UPDATE cards SET blocked=? WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, blocked);
            ps.setLong(2, cardId);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}
