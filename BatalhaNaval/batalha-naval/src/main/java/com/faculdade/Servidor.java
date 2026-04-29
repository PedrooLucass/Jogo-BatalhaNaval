package com.faculdade;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Servidor {
    private ServerSocket serverSocket;
    private List<ClientHandler> clientHandlers;

    public Servidor(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServidor() {
        try {
            while (clientHandlers.size() != 2) {
                Socket socket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
