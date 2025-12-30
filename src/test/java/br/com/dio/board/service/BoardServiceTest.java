package br.com.dio.board.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardServiceTest {

    @Test
    void shouldInstantiateBoardService() {
        BoardService boardService = new BoardService();

        assertNotNull(boardService);
    }
}
