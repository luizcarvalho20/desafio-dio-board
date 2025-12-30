package br.com.dio.board.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColumnTypeTest {

    @Test
    void shouldContainTodo() {
        assertEquals("TODO", ColumnType.TODO.name());
    }

    @Test
    void shouldContainDoing() {
        assertEquals("DOING", ColumnType.DOING.name());
    }

    @Test
    void shouldContainDone() {
        assertEquals("DONE", ColumnType.DONE.name());
    }
}
