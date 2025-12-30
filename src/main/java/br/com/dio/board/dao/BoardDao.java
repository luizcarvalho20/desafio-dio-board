package br.com.dio.board.dao;

import br.db.ConnectionFactory;
import br.com.dio.board.domain.Board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {

    public Board create(String name) {
        String sql = "INSERT INTO boards(name) VALUES (?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return new Board(rs.getLong(1), name);
            }
            throw new RuntimeException("Não foi possível obter o ID do board.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Board> findAll() {
        String sql = "SELECT id, name FROM boards ORDER BY id DESC";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Board> list = new ArrayList<>();
            while (rs.next()) list.add(new Board(rs.getLong("id"), rs.getString("name")));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean exists(long id) {
        String sql = "SELECT 1 FROM boards WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void delete(long boardId) {
        String sql = "DELETE FROM boards WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, boardId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
