package br.com.dio.board;

import br.com.dio.board.db.DbBootstrap;

public class Main {
    public static void main(String[] args) {
        DbBootstrap.ensureSchema();
        System.out.println("Schema OK!");
    }
}
