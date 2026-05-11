package com.faculdade;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Servidor {
    Jogo jogo;
    ServerSocket serverSocket;
    List<Jogador> jogadores;
    List<ClientHandler> clientHandlers;
    Scanner scanner = new Scanner(System.in);

    public Servidor(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.jogadores = new ArrayList<>();
        this.clientHandlers = new ArrayList<>();
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }
    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public void startServidor() {
        try {
            while (clientHandlers.size() != 2) {
                Socket socket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(socket, this);

                clientHandlers.add(clientHandler);
                jogadores.add(clientHandler.getJogador());

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception e) {
            closeServidor();
        }
    }

    public void mensagemParaTodos(Mensagem mensagem) {
        System.out.println(mensagem.getMensagem());
        try {
            for(ClientHandler clientHandler : clientHandlers) {
                clientHandler.enviarMensagem(mensagem.getMensagem());
            }
        } catch (Exception e) {
            closeServidor();
        }
    }

    public void enviarMensagemPrivada(Jogador jogador, Mensagem mensagem) {
        jogador.getClientHandler().enviarMensagem(mensagem.getMensagem());
    }

    public void processarMensagemClient(Mensagem mensagem) {
        if (jogo != null) {
            jogo.adicionarMensagem(mensagem);
        }
    }

    public void closeServidor() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4080);
            Servidor server = new Servidor(serverSocket);

            server.startServidor();

            Jogo jogo = new Jogo(server);
            server.setJogo(jogo);
            jogo.iniciar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
