package com.faculdade;

import java.util.ArrayList;
import java.util.List;

public class Jogo {
    List<Jogador> jogadores;

    Servidor servidor;

    public Jogo(Servidor servidor) {
        jogadores = new ArrayList<>();
        this.servidor = servidor;
    }
    public void setJogadores() {
        //servidor.enviarMensagem
    }
    public void iniciar() {
        setJogadores();
    }
}
