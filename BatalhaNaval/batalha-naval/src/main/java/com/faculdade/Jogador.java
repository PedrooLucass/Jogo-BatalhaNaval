package com.faculdade;

public class Jogador {
    private String nome;
    private ClientHandler clientHandler;
    int pontuacao;

    public Jogador() {
        pontuacao = 0;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public int getPontuacao(){
        return pontuacao;
    }

    public void adicionarPonto(){ pontuacao++; }

    public String getNome(){ return nome; }

    public void setNome(String nome) { this.nome = nome; }
}
