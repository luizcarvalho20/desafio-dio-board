package br.com.dio.board.dao;

import br.db.ConnectionFactory;
import br.com.dio.board.domain.BoardColumn;
import br.com.dio.board.domain.ColumnType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ColumnDao {

    public void insert(BoardColumn col) {
        String sql = "INSERT INTO board_columns(board_id,name,position,type) VALUES (?,?,?,?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, col.boardId());
            ps.setString(2, col.name());
            ps.setInt(3, col.position());
            ps.setString(4, col.type().name());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<BoardColumn> findByBoard(long boardId) {
        String sql = "SELECT id, board_id, name, position, type FROM board_columns WHERE board_id=? ORDER BY position";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, boardId);
            try (ResultSet rs = ps.executeQuery()) {
                List<BoardColumn> cols = new ArrayList<>();
                while (rs.next()) {
                    cols.add(new BoardColumn(
                            rs.getLong("id"),
                            rs.getLong("board_id"),
                            rs.getString("name"),
                            rs.getInt("position"),
                            ColumnType.valueOf(rs.getString("type"))
                    ));
                }
                return cols;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Optional<BoardColumn> findById(long columnId) {
        String sql = "SELECT id, board_id, name, position, type FROM board_columns WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, columnId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new BoardColumn(
                        rs.getLong("id"),
                        rs.getLong("board_id"),
                        rs.getString("name"),
                        rs.getInt("position"),
                        ColumnType.valueOf(rs.getString("type"))
                ));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public BoardColumn getInitial(long boardId) {
        return findByBoard(boardId).stream()
                .filter(c -> c.type() == ColumnType.INITIAL)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Board sem coluna INITIAL."));
    }

    public BoardColumn getCancel(long boardId) {
        return findByBoard(boardId).stream()
                .filter(c -> c.type() == ColumnType.CANCEL)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Board sem coluna CANCEL."));
    }
}
