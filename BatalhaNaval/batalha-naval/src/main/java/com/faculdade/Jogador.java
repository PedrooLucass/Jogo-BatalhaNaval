package com.faculdade;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Jogador {
    @JsonIgnore
    private ClientHandler clientHandler;
    private String nome;
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
