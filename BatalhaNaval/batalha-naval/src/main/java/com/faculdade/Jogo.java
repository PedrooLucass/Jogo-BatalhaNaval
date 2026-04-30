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
        jogadores = servidor.getJogadores();
    }
    public void setEmbarcacoes() {
        /*
        2 Porta Aviões
        3 Destroyers
        4 Submarinos
        5 Fragatas
        6 Botes
        */
        for (Jogador jogador : jogadores) {
            servidor.enviarMensagemPrivada(jogador, new Mensagem("Gostaria de posicionar sua embarcação ou prefere deixar-lá posicionar aleatóriamente?"));
            servidor.enviarMensagemPrivada(jogador, new Mensagem("1 - Prefiro eu mesmo posicionar\n2 - Prefiro aleatóriamente"));
            // Ainda não temos função para entrada do cliente ainda

            // Posicionar aleatoriamente eh de fuder hein d=(^o^)=b
            int opcaoEmbarcacao = 1;
            
            if (opcaoEmbarcacao == 1){

            } else if (opcaoEmbarcacao == 2) {
                // Em desenvolvimento
            }

        }
    }
    public void iniciar() {
        setJogadores();
        setEmbarcacoes();

    }
}
