package com.faculdade;

import java.util.ArrayList;
import java.util.List;
//import com.faculdade.*;

public class Jogador {
    public String nome;
    private Mapa mapa; //O mapa nao deveria ta sendo criado na classe Jogo?
    private ClientHandler clientHandler;
    List<Embarcacao> embarcacoes = new ArrayList<>();

    public Jogador(String nome) {
        this.nome = nome;
        inicializarInventario();
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    private void inicializarInventario(){
        for (int i = 0; i < 2; i++){
            embarcacoes.add(new PortaAvioes());
        }

        for (int i = 0; i < 3; i++){
            embarcacoes.add(new Destroyer());
        }

        for (int i = 0; i < 4; i++){
            embarcacoes.add(new Submarino());
        }

        for (int i = 0; i < 5; i++){
            embarcacoes.add(new Fragata());
        }

        for (int i = 0; i < 6; i++){
            embarcacoes.add(new Bote());
        }
    }
}
