package com.faculdade;

public class App {

    public static void main(String[] args) {

        Jogo jogo = new Jogo();

        Jogador j1 = new Jogador("Joao");
        Jogador j2 = new Jogador("Pedro");

        jogo.jogadores.add(j1);
        jogo.jogadores.add(j2);

        jogo.iniciar();

        jogo.mapa.printarMapaOculto();
    }
}