package br.com.dio.board.service;

import br.com.dio.board.dao.BoardDao;
import br.com.dio.board.dao.ColumnDao;
import br.com.dio.board.domain.Board;
import br.com.dio.board.domain.BoardColumn;
import br.com.dio.board.domain.ColumnType;

import java.util.List;

public class BoardService {
    private final BoardDao boardDao = new BoardDao();
    private final ColumnDao columnDao = new ColumnDao();

    public Board createBoard(String name,
                             String initialName,
                             List<String> pendingNames,
                             String finalName,
                             String cancelName) {

        if (pendingNames == null || pendingNames.isEmpty()) {
            throw new RuntimeException("O board precisa ter pelo menos 1 coluna PENDING (mínimo 3 colunas no total).");
        }

        Board board = boardDao.create(name);

        // INITIAL na posição 1
        columnDao.insert(new BoardColumn(null, board.id(), initialName, 1, ColumnType.INITIAL));

        // PENDINGs no meio
        int pos = 2;
        for (String p : pendingNames) {
            columnDao.insert(new BoardColumn(null, board.id(), p, pos++, ColumnType.PENDING));
        }

        // FINAL penúltima
        columnDao.insert(new BoardColumn(null, board.id(), finalName, pos++, ColumnType.FINAL));

        // CANCEL última
        columnDao.insert(new BoardColumn(null, board.id(), cancelName, pos, ColumnType.CANCEL));

        return board;
    }

    public List<Board> listBoards() { return boardDao.findAll(); }

    public void deleteBoard(long boardId) { boardDao.delete(boardId); }

    public boolean boardExists(long boardId) { return boardDao.exists(boardId); }
}
