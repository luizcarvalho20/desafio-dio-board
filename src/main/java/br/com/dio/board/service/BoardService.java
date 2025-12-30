package br.com.dio.board.service;

import br.com.dio.board.dao.BoardDao;
import br.com.dio.board.domain.Board;

import java.util.List;

public class BoardService {

    private final BoardDao boardDao;

    public BoardService() {
        this.boardDao = new BoardDao();
    }

    public Board createBoard(String name) {
        return boardDao.create(name);
    }

    public List<Board> listBoards() {
        return boardDao.findAll();
    }

    public void deleteBoard(long id) {
        boardDao.delete(id);
    }
}
