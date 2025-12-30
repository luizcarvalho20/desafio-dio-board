package br.com.dio.board.ui;

import br.com.dio.board.domain.Board;
import br.com.dio.board.service.BoardService;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final BoardService boardService;
    private final Scanner scanner;

    public ConsoleUI() {
        this.boardService = new BoardService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int option;

        do {
            System.out.println("\n=== DESAFIO BOARD - DIO ===");
            System.out.println("1 - Criar board");
            System.out.println("2 - Listar boards");
            System.out.println("3 - Excluir board");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> createBoard();
                case 2 -> listBoards();
                case 3 -> deleteBoard();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida");
            }
        } while (option != 0);
    }

    private void createBoard() {
        System.out.print("Nome do board: ");
        String name = scanner.nextLine();

        Board board = boardService.createBoard(name);
        System.out.println("Board criado com ID: " + board.id());
    }

    private void listBoards() {
        List<Board> boards = boardService.listBoards();

        if (boards.isEmpty()) {
            System.out.println("Nenhum board cadastrado.");
            return;
        }

        boards.forEach(b ->
                System.out.println("ID: " + b.id() + " | Nome: " + b.name())
        );
    }

    private void deleteBoard() {
        System.out.print("Informe o ID do board: ");
        long id = Long.parseLong(scanner.nextLine());

        boardService.deleteBoard(id);
        System.out.println("Board removido (se existia).");
    }
}
