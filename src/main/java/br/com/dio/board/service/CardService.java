package br.com.dio.board.service;

import br.com.dio.board.dao.CardDao;
import br.com.dio.board.dao.ColumnDao;
import br.db.ConnectionFactory;
import br.com.dio.board.domain.BoardColumn;
import br.com.dio.board.domain.Card;
import br.com.dio.board.domain.ColumnType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class CardService {
    private final CardDao cardDao = new CardDao();
    private final ColumnDao columnDao = new ColumnDao();

    public long createCard(long boardId, String title, String description) {
        BoardColumn initial = columnDao.getInitial(boardId);
        long cardId = cardDao.create(boardId, initial.id(), title, description);

        // Opcional 1: histórico
        insertHistory(cardId, null, initial.id());
        return cardId;
    }

    // Move apenas para a próxima coluna (sem pular)
    public void moveNext(long cardId) {
        Card card = cardDao.findById(cardId).orElseThrow(() -> new RuntimeException("Card não encontrado."));
        if (card.blocked()) throw new RuntimeException("Card bloqueado: desbloqueie antes de mover.");

        List<BoardColumn> cols = columnDao.findByBoard(card.boardId());
        BoardColumn current = cols.stream().filter(c -> c.id().equals(card.columnId())).findFirst()
                .orElseThrow(() -> new RuntimeException("Coluna atual inválida."));

        if (current.type() == ColumnType.CANCEL) throw new RuntimeException("Card já está cancelado.");
        if (current.type() == ColumnType.FINAL) throw new RuntimeException("Card já está finalizado.");

        BoardColumn next = cols.stream()
                .filter(c -> c.position() == current.position() + 1)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Não existe próxima coluna."));

        // CANCEL só via opção "cancelar"
        if (next.type() == ColumnType.CANCEL) {
            throw new RuntimeException("Use a opção CANCELAR para mover ao CANCEL.");
        }

        closeLastHistory(cardId);
        cardDao.updateColumn(cardId, next.id());
        insertHistory(cardId, current.id(), next.id());
    }

    // CANCEL pode receber diretamente de qualquer coluna exceto FINAL
    public void cancelCard(long cardId) {
        Card card = cardDao.findById(cardId).orElseThrow(() -> new RuntimeException("Card não encontrado."));
        if (card.blocked()) throw new RuntimeException("Card bloqueado: desbloqueie antes de cancelar.");

        List<BoardColumn> cols = columnDao.findByBoard(card.boardId());
        BoardColumn current = cols.stream().filter(c -> c.id().equals(card.columnId())).findFirst()
                .orElseThrow(() -> new RuntimeException("Coluna atual inválida."));

        if (current.type() == ColumnType.FINAL) {
            throw new RuntimeException("Não é permitido cancelar um card que já está na coluna FINAL.");
        }

        BoardColumn cancel = cols.stream()
                .filter(c -> c.type() == ColumnType.CANCEL)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Board sem coluna CANCEL."));

        closeLastHistory(cardId);
        cardDao.updateColumn(cardId, cancel.id());
        insertHistory(cardId, current.id(), cancel.id());
    }

    public void blockCard(long cardId, String reason) {
        if (reason == null || reason.isBlank()) throw new RuntimeException("Motivo do bloqueio é obrigatório.");
        Card card = cardDao.findById(cardId).orElseThrow(() -> new RuntimeException("Card não encontrado."));
        if (card.blocked()) throw new RuntimeException("Card já está bloqueado.");

        cardDao.setBlocked(cardId, true);
        insertBlockEvent(cardId, "BLOCK", reason);
    }

    public void unblockCard(long cardId, String reason) {
        if (reason == null || reason.isBlank()) throw new RuntimeException("Motivo do desbloqueio é obrigatório.");
        Card card = cardDao.findById(cardId).orElseThrow(() -> new RuntimeException("Card não encontrado."));
        if (!card.blocked()) throw new RuntimeException("Card não está bloqueado.");

        cardDao.setBlocked(cardId, false);
        insertBlockEvent(cardId, "UNBLOCK", reason);
    }

    public List<Card> listCards(long boardId) {
        return cardDao.findByBoard(boardId);
    }

    // ===== Persistência opcional =====
    private void insertHistory(long cardId, Long fromCol, long toCol) {
        String sql = "INSERT INTO card_column_history(card_id,from_column_id,to_column_id) VALUES (?,?,?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, cardId);
            if (fromCol == null) ps.setNull(2, java.sql.Types.BIGINT); else ps.setLong(2, fromCol);
            ps.setLong(3, toCol);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro no histórico: " + e.getMessage(), e);
        }
    }

    private void closeLastHistory(long cardId) {
        String sql = "UPDATE card_column_history SET left_at = CURRENT_TIMESTAMP WHERE card_id=? AND left_at IS NULL";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, cardId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro fechando histórico: " + e.getMessage(), e);
        }
    }

    private void insertBlockEvent(long cardId, String action, String reason) {
        String sql = "INSERT INTO card_block_events(card_id,action,reason) VALUES (?,?,?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, cardId);
            ps.setString(2, action);
            ps.setString(3, reason);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro no log de bloqueios: " + e.getMessage(), e);
        }
    }
}
