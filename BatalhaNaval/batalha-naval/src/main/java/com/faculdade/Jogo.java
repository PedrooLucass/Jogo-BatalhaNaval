package com.faculdade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Jogo {

    private BlockingQueue<Mensagem> filaMensagens = new LinkedBlockingQueue<>();
    List<Jogador> jogadores;
    Mapa mapa;
    private List<Embarcacao> embarcacoes;

    Servidor servidor;

    public Jogo(Servidor servidor) {
        jogadores = new ArrayList<>();
        this.servidor = servidor;
        mapa = new Mapa();
        embarcacoes = new ArrayList<>();
    }

    public void setJogadores() {
        jogadores = servidor.getJogadores();
    }

    private void criarEmbarcacoes() {

        for (int i = 0; i < 2; i++) {
            embarcacoes.add(new PortaAvioes());
        }

        for (int i = 0; i < 3; i++) {
            embarcacoes.add(new Destroyer());
        }

        for (int i = 0; i < 4; i++) {
            embarcacoes.add(new Submarino());
        }

        for (int i = 0; i < 5; i++) {
            embarcacoes.add(new Fragata());
        }

        for (int i = 0; i < 6; i++) {
            embarcacoes.add(new Bote());
        }
    }

    public void inicializarEmbarcacoes() {
        criarEmbarcacoes();

        for (Embarcacao embarcacao : embarcacoes) {
            posicionarEmbarcacao(null, embarcacao, true);
        }
    }

    private void posicionarEmbarcacao(Jogador jogador, Embarcacao embarcacaoAux, boolean aleatoriamente) {

        Random random = new Random();

        while (true) {

            System.out.println("Tentando posicionar " + embarcacaoAux.getNome());

            int x;
            int y;

            if (aleatoriamente) {

                x = random.nextInt(mapa.getTamanhoMapa());

                y = random.nextInt(mapa.getTamanhoMapa());

            } else {
                servidor.enviarMensagemPrivada(jogador, new Mensagem(embarcacaoAux.getNome() + ": Qual a coordenada X:"));
                x = esperarJogadaInt(jogador, 0, mapa.getTamanhoMapa()-1);

                servidor.enviarMensagemPrivada(jogador, new Mensagem(embarcacaoAux.getNome() + ": Qual a coordenada Y:"));
                y = esperarJogadaInt(jogador, 0, mapa.getTamanhoMapa()-1);
            }

            Coordenada coordenadaEscolhida = new Coordenada(x, y);

            if (!mapa.verificarAreaLivre(x, y)) {
                continue;
            }

            int seguimentoX;
            int seguimentoY;

            if (aleatoriamente) {

                /*
                    Possibilidades:

                    (-1,-1) diagonal
                    (-1, 0) horizontal
                    (-1, 1) diagonal
                    ( 0,-1) vertical
                    ( 0, 1) vertical
                    ( 1,-1) diagonal
                    ( 1, 0) horizontal
                    ( 1, 1) diagonal
                 */

                do {
                    seguimentoX = random.nextInt(3) - 1;
                    seguimentoY = random.nextInt(3) - 1;

                } while (seguimentoX == 0 && seguimentoY == 0);

            } else {

                servidor.enviarMensagemPrivada(jogador, new Mensagem("Direção X:"));

                seguimentoX = 1;

                servidor.enviarMensagemPrivada(jogador, new Mensagem("Direção Y:"));

                seguimentoY = 0;
            }

            List<Coordenada> coordenadasEmbarcacao = new ArrayList<>();

            boolean valido = true;

            for (int j = 0; j < embarcacaoAux.getTamanho(); j++) {

                int novoX = coordenadaEscolhida.getX() + (j * seguimentoX);

                int novoY = coordenadaEscolhida.getY() + (j * seguimentoY);

                if (!mapa.coordenadaValida(novoX, novoY)) {

                    valido = false;

                    break;
                }

                /*
                    Verifica:
                    - colisão
                    - adjacência
                    - diagonais
                 */

                if (!mapa.verificarAreaLivre(novoX, novoY)) {

                    valido = false;

                    break;
                }

                coordenadasEmbarcacao.add(new Coordenada(novoX, novoY));
            }

            if (!valido) {
                if (!aleatoriamente) {
                    servidor.enviarMensagemPrivada(jogador, new Mensagem("Posição inválida!"));
                }

                continue;
            }

            embarcacaoAux.setCoordenadas(coordenadasEmbarcacao);

            for (Coordenada c : coordenadasEmbarcacao) {
                mapa.setCoordenadaOculta(c.getX(), c.getY(), embarcacaoAux.getSimbolo());
            }

            System.out.println(embarcacaoAux.getNome() + " posicionada!");

            return;
        }
    }

    public void adicionarMensagem(Mensagem mensagem) {
        filaMensagens.add(mensagem);
    }

    public int esperarJogadaInt(Jogador jogador, int numeroMIN, int numeroMAX) {
        while(true) {
            try {
                while (true) {
                    Mensagem mensagem = filaMensagens.take();

                    if (mensagem.getJogador().equals(jogador)) {
                        if (Integer.parseInt(mensagem.getMensagem()) >= numeroMIN && Integer.parseInt(mensagem.getMensagem()) <= numeroMAX) {
                            return Integer.parseInt(mensagem.getMensagem());
                        } else {
                            servidor.enviarMensagemPrivada(jogador, new Mensagem("Número fora do escopo permitido! "));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String esperarJogadaStr(Jogador jogador) {
        while(true) {
            try {
                Mensagem mensagem = filaMensagens.take();

                if (mensagem.getJogador().equals(jogador)) {
                    return mensagem.getMensagem();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void iniciarRodada() {
        for (Jogador jogador : jogadores) {
            servidor.enviarMensagemPrivada(jogador, new Mensagem("X:"));

            int x = esperarJogadaInt(jogador, 0, mapa.getTamanhoMapa() - 1);

            servidor.enviarMensagemPrivada(jogador, new Mensagem("Y:"));

            int y = esperarJogadaInt(jogador, 0, mapa.getTamanhoMapa() - 1);

            boolean acertou = atacarCoordenada(jogador, x, y);

            if (acertou) {
                servidor.enviarMensagemPrivada(jogador, new Mensagem("Embarcação destruída!"));
                jogador.adicionarPonto();

            } else {
                servidor.enviarMensagemPrivada(jogador, new Mensagem("Água!"));
            }

            mapa.printarMapaVisivel();
            mapa.printarMapaOculto();
        }
    }

    private void destruirEmbarcacao(Embarcacao embarcacao) {

        embarcacao.setDestruida(true);

        for (Coordenada coordenada : embarcacao.getCoordenadas()) {

            mapa.setCoordenadaOculta(coordenada.getX(), coordenada.getY(), 'X');

            mapa.setCoordenadaVisivel(coordenada.getX(), coordenada.getY(), 'X');
        }
        embarcacoes.remove(embarcacao);

        System.out.println(embarcacao.getNome() + " destruída!");
    }

    public boolean atacarCoordenada(Jogador jogador, int x, int y) {

        for (Embarcacao embarcacao : embarcacoes) {
            if (embarcacao.isDestruida()) {
                continue;
            }

            for (Coordenada coordenada : embarcacao.getCoordenadas()) {
                if (coordenada.getX() == x && coordenada.getY() == y) {
                    destruirEmbarcacao(embarcacao);

                    jogador.adicionarPonto();

                    return true;
                }
            }
        }

        mapa.setCoordenadaVisivel(x, y, 'A');

        return false;
    }

    public void iniciar() {
        setJogadores();

        inicializarEmbarcacoes();

        while (!embarcacoes.isEmpty()){
            iniciarRodada();
        }

        if (jogadores.get(0).getPontuacao() == jogadores.get(1).getPontuacao()){
            servidor.mensagemParaTodos(new Mensagem("Empate!"));
        }
        else if (jogadores.get(0).getPontuacao() > jogadores.get(1).getPontuacao()){
            servidor.mensagemParaTodos(new Mensagem(jogadores.get(0).getNome() + " Venceu!"));
        }
        else{
            servidor.mensagemParaTodos(new Mensagem(jogadores.get(1).getNome() + " Venceu!"));
        }

        servidor.mensagemParaTodos(new Mensagem(jogadores.get(0).getNome() + ": " + jogadores.get(0).getPontuacao()));

        servidor.mensagemParaTodos(new Mensagem(jogadores.get(1).getNome() + ": " + jogadores.get(1).getPontuacao()));
    }
}