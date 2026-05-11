package com.faculdade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private Scanner scanner = new Scanner(System.in);

    public Cliente(Socket socket) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            closeEverything(socket, reader, writer, scanner);
        }
    }

    public void receberMensagem() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mensagem;

                while (socket.isConnected()) {
                    try {
                        mensagem = reader.readLine();
                        System.out.println(mensagem);
                    } catch (Exception e) {
                        closeEverything(socket, reader, writer, scanner);
                    }
                }
            }
        }).start();
    }

    public void enviarMensagem() {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    String mensagem = scanner.nextLine();

                    writer.write(mensagem);
                    writer.newLine();
                    writer.flush();
                } catch (Exception e) {
                    closeEverything(socket, reader, writer, scanner);
                    break;
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader reader, BufferedWriter writer, Scanner scanner) {
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
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 4080);
            Cliente client = new Cliente(socket);
            client.receberMensagem();
            client.enviarMensagem();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
