package com.faculdade;

public class Mensagem {
    private Jogador jogador;
    private String mensagem;

    public Mensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public String getMensagem() {
        return mensagem;
    }
}
