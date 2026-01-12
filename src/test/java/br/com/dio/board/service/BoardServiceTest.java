package br.com.dio.board.service;

import br.com.dio.board.persistence.dao.BoardDAO; // Ajuste o import conforme seu projeto
import br.com.dio.board.persistence.entity.BoardEntity; // Ajuste o import
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoardServiceTest {

    @Mock
    private BoardDAO boardDAO; // Aqui simulamos a camada de acesso a dados

    @InjectMocks
    private BoardService boardService; // O sistema injeta o Mock do DAO dentro do Service

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
    }

    @Test
    void deveCriarUmBoardComSucesso() {
        // GIVEN: Preparamos os dados
        BoardEntity entity = new BoardEntity();
        entity.setName("Projeto Home Office");

        // WHEN: Quando chamarmos o método de salvar
        boardService.insert(entity); // Ajuste para o nome do método no seu service (insert, save, etc)

        // THEN: Verificamos se o DAO foi chamado exatamente 1 vez
        verify(boardDAO, times(1)).insert(entity);
        assertNotNull(entity.getName());
    }
}