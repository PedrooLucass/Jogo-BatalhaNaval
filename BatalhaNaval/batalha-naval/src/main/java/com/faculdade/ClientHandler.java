package com.faculdade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    Jogador jogador;
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;
    Scanner scanner;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            writer.write("Digite o seu nome: ");
            writer.newLine();
            writer.flush();

            jogador = new Jogador(reader.readLine());
            jogador.setClientHandler(this);

        } catch (Exception e) {
            closeClientHandler(socket, reader, writer, scanner);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                String mensagem = reader.readLine();

                if (mensagem == null) break;

                /*
                tem que fazer um servidor.processarMensagem() para que o Servidor mande ao Jogo e o Jogo faça o comando do jogador
                eu PARTICULARMENTE não gostaria de repetir o mesmo jeito que fiz para as mensagens do truco (com o BlockingQueue),
                gostaria de fazer de uma forma diferente, mas caso queira reutilizar a mesma lógica do truco também vale,
                afinal estamos com pouco tempo.
                */
            }
        } catch (Exception e) {
            closeClientHandler(socket, reader, writer, scanner);
            throw new RuntimeException(e);
        }
    }

    public void closeClientHandler(Socket socket, BufferedReader reader, BufferedWriter writer, Scanner scanner) {
        try {
            if (socket != null) {
                socket.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (scanner != null) {
                scanner.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
