package com.faculdade;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    @JsonIgnore
    private Jogador jogador;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private Servidor server;

    public ClientHandler(Socket socket, Servidor server) {
        try {
            this.server = server;
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            writer.write("Digite seu nome:");
            writer.newLine();
            writer.flush();

            String nome = reader.readLine();

            this.jogador = new Jogador();
            this.jogador.setNome(nome);
            this.jogador.setClientHandler(this);
        } catch (Exception e) {
            closeEverything(socket, reader, writer);
        }
    }
    public Jogador getJogador() {
        return this.jogador;
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                Mensagem mensagem = new Mensagem(reader.readLine());
                if (mensagem.getMensagem() == null) break;

                mensagem.setJogador(jogador);
                server.processarMensagemClient(mensagem);

            } catch (Exception e) {
                closeEverything(socket, reader, writer);
                break;
            }
        }
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public void enviarMensagem(String mensagem) {
        try {
            writer.write(mensagem); // Aqui ele manda a mensagem e o newLine e o flush só servem pra limpar o writer
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            closeEverything(socket, reader, writer);
        }
    }

    public void closeEverything(Socket socket, BufferedReader reader, BufferedWriter writer) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
