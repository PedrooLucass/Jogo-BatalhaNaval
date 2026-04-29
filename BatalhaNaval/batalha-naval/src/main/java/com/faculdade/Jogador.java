package com.faculdade;

public class Jogador {
    public String nome;
    private Mapa mapa;
    private ClientHandler clientHandler;

    public Jogador(String nome) {
        this.nome = nome;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }
}
