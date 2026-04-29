package com.faculdade;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Servidor {
    private ServerSocket serverSocket;
    private List<ClientHandler> clientHandlers;
    private List<Jogador> jogadores;

    public Servidor(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void startServidor() {
        try {
            while (clientHandlers.size() != 2) {
                Socket socket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandlers.add(clientHandler);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  void transmitirMensagem(Mensagem mensagem) {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.enviarMensagem(mensagem);
        }
    }
    public void enviarMensagemPrivada(Jogador jogador, Mensagem mensagem) {
        jogador.getClientHandler().enviarMensagem(mensagem);
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4080);
            Servidor servidor = new Servidor(serverSocket);

            servidor.startServidor();

            Jogo jogo = new Jogo(servidor);
            jogo.iniciar();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
