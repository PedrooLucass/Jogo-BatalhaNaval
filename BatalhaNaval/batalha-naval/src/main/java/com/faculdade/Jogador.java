package com.faculdade;

public class Jogador {
    String nome;
    Mapa mapa;
    ClientHandler clientHandler;

    public Jogador(String nome) {
        this.nome = nome;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }
}
