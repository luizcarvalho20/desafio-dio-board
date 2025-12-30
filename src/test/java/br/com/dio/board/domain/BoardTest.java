package br.com.dio.board.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void shouldCreateBoardWithName() {
        Board board = new Board(1L, "Meu Board");

        assertNotNull(board);
        assertEquals(1L, board.id());
        assertEquals("Meu Board", board.name());
    }
}
