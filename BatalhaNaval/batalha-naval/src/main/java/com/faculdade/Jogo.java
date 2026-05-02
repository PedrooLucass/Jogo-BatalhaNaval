package com.faculdade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogo {
    List<Jogador> jogadores;
    Mapa mapa;

    Servidor servidor;

    public Jogo(Servidor servidor) {
        jogadores = new ArrayList<>();
        this.servidor = servidor;
    }
    public void setJogadores() {
        jogadores = servidor.getJogadores();
    }

    public void inicializarEmbarcacoes() {
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
            // Ainda não temos função para entrada do cliente

            // Posicionar aleatoriamente eh de fuder hein d=(^o^)=b
            // É mt embarcação pra ficar colocando manualmente, fica mt chato colocar um a um ent melhor a gnt deixar essa opcao, ainda mais pra quando formos debugar
            int opcaoEmbarcacao = 1;
            
            List<Embarcacao> embarcacoes = (opcaoEmbarcacao == 1) ? criarListaEmbarcacoes(jogador, false) : criarListaEmbarcacoes(jogador, true);
        }
    }

    private List<Embarcacao> criarListaEmbarcacoes(Jogador jogador, boolean aleatoriamente) {
        List<Embarcacao> embarcacoes = new ArrayList<>();
        for (int i = 0; i < (2+3+4+5+6); i++) {
            String tipoEmbarcacao = "";
            switch (i) {
                case 0:
                    tipoEmbarcacao = "PortaAvioes";
                    break;
                case 2:
                    tipoEmbarcacao = "Destroyer";
                    break;
                case (2+3):
                    tipoEmbarcacao = "Submarino";
                    break;
                case (2+3+4):
                    tipoEmbarcacao = "Fragata";
                    break;
                case (2+3+4+5):
                    tipoEmbarcacao = "Bote";
                    break;
            }
            embarcacoes.add(criarEmbarcacao(jogador, tipoEmbarcacao, aleatoriamente));
        }
        return embarcacoes;
    }

    private Embarcacao criarEmbarcacao(Jogador jogador, String tipoEmbarcacao, boolean aleatoriamente) {
        Embarcacao embarcacaoAux;
        switch (tipoEmbarcacao) {
            case "PortaAvioes":
                embarcacaoAux = new PortaAvioes();
                break;
            case "Destroyer":
                embarcacaoAux = new Destroyer();
                break;
            case "Submarino":
                embarcacaoAux = new Submarino();
                break;
            case "Fragata":
                embarcacaoAux = new Fragata();
                break;
            default:
                embarcacaoAux = new Bote();
                break;
        }

        while (true) {
            int x, y;
            if (aleatoriamente) {
                Random random = new Random();
                x = random.nextInt(mapa.getTamanhoMapa() + 1);
                y = random.nextInt(mapa.getTamanhoMapa() + 1);
            } else {
                servidor.enviarMensagemPrivada(jogador, new Mensagem(embarcacaoAux.getNome() + ": Qual a coordenada X: "));
                x = 8; // Entrada do cliente provisória
                servidor.enviarMensagemPrivada(jogador, new Mensagem(embarcacaoAux.getNome() + ": Qual a coordenada Y: "));
                y = 8;
            }
            Coordenada coordenadaEscolhida = new Coordenada(x, y);

            if (mapa.verificarCoordenadaParaEmbarcacao(coordenadaEscolhida)) {
                int seguimentoX, seguimentoY;

                if (aleatoriamente) {
                    Random random = new Random();

                    int numXaux = random.nextInt(2); // com o random.nextInt(2) só pode retornar 0 ou 1;
                    int numYaux = random.nextInt(2);

                    if (numXaux == 0 && numYaux == 0) { // Se ambos cairem 0, não tem direção definida, assim, dá pau
                        if (random.nextInt(2) == 0) {
                            numXaux++;
                        } else {
                            numYaux++;
                        }
                    }

                    seguimentoX = (random.nextInt(2) == 1) ? numXaux : -numXaux; // definir operador do número
                    seguimentoY = (random.nextInt(2) == 1) ? numYaux : -numYaux; // se 0, fica -, se 1, fica +

                } else {
                    servidor.enviarMensagemPrivada(jogador, new Mensagem("E para qual direção X gostaria de seguir? "));
                    servidor.enviarMensagemPrivada(jogador, new Mensagem("1 - " + (coordenadaEscolhida.getX() - 1) + "\n2 - " + coordenadaEscolhida.getX() + "\n3 - " + (coordenadaEscolhida.getX() + 1)));
                    seguimentoX = 9 - x; // 3 seria a entrada do suposto player, então, 9 (x+1)

                    servidor.enviarMensagemPrivada(jogador, new Mensagem("E para qual direção Y gostaria de seguir? "));
                    servidor.enviarMensagemPrivada(jogador, new Mensagem("1 - " + (coordenadaEscolhida.getY() - 1) + "\n2 - " + coordenadaEscolhida.getY() + "\n3 - " + (coordenadaEscolhida.getY() + 1)));

                    seguimentoY = 8 - y; // Seguimento só pode ser -1, 0 ou 1

                    if (seguimentoX == 0 && seguimentoY == 0) {
                        servidor.enviarMensagemPrivada(jogador, new Mensagem("Sem direção definida pelo usuário, será escolhido aleatóriamente!"));
                        Random random = new Random();
                        if (random.nextInt(2) == 0) {
                            seguimentoX = (random.nextInt(2) == 1) ? seguimentoX + 1 : seguimentoX - 1; // Definindo se é positivo ou negativo
                        } else {
                            seguimentoY = (random.nextInt(2) == 1) ? seguimentoY + 1 : seguimentoY - 1;
                            ;
                        }
                    }
                }

                List<Coordenada> coordenadasEmbarcacao = new ArrayList<>();
                coordenadasEmbarcacao.add(coordenadaEscolhida);

                List<Boolean> listaBooleana = new ArrayList<>();
                Coordenada verificandoCoordenada = new Coordenada(coordenadaEscolhida.getX(), coordenadaEscolhida.getY());

                for (int j = 0; j < embarcacaoAux.getTamanho(); j++) {
                    verificandoCoordenada = new Coordenada((verificandoCoordenada.getX() + seguimentoX), (verificandoCoordenada.getY() + seguimentoY));

                    listaBooleana.add(mapa.verificarCoordenadaParaEmbarcacao(verificandoCoordenada));
                    coordenadasEmbarcacao.add(verificandoCoordenada);
                }
                if (listaBooleana.contains(false)) {
                    if (!aleatoriamente) { servidor.enviarMensagemPrivada(jogador, new Mensagem("Seguimento proíbido, há outra embarcação impedindo!")); }
                } else {
                    embarcacaoAux.setCoordenadas(coordenadasEmbarcacao);
                    return embarcacaoAux;
                }
            }
        }
    }
    public void iniciar() {
        setJogadores();
        inicializarEmbarcacoes();

    }
}
