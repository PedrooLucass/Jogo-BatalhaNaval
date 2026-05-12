package com.faculdade;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Mapa {

    public static final int TAMANHO_MAPA = 30;

    private char[][] visivel;
    private char[][] oculto;

    public Mapa(){
    }

    public int getTamanhoMapa() {
        return TAMANHO_MAPA;
    }

    public char getCoordenadaOculta(int x, int y) {
        return oculto[x][y];
    }

    public char getCoordenadaVisivel(int x, int y) {
        return visivel[x][y];
    }

    public void setCoordenadaOculta(int x, int y, char simbolo) {
        if (coordenadaValida(x, y)) {
            oculto[x][y] = simbolo;
        }
    }

    public void setCoordenadaVisivel(int x, int y, char simbolo) {
        if (coordenadaValida(x, y)) {
            visivel[x][y] = simbolo;
        }
    }

    public void inicializarMapa() {
        visivel = new char[TAMANHO_MAPA][TAMANHO_MAPA];
        oculto = new char[TAMANHO_MAPA][TAMANHO_MAPA];

        for (int i = 0; i < TAMANHO_MAPA; i++) {
            for (int j = 0; j < TAMANHO_MAPA; j++) {
                visivel[i][j] = 'O';
                oculto[i][j] = 'V';
            }
        }
    }

    public boolean coordenadaValida(int x, int y) {
        return x >= 0 && x < TAMANHO_MAPA && y >= 0 && y < TAMANHO_MAPA;
    }

    public boolean coordenadaLivre(int x, int y) {
        if (!coordenadaValida(x, y)) {
            return false;
        }

        return oculto[x][y] == 'V';
    }

    public boolean verificarAreaLivre(int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int novoX = x + i;
                int novoY = y + j;

                if (!coordenadaValida(novoX, novoY)) {
                    continue;
                }

                if (!coordenadaLivre(novoX, novoY)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean atacarCoordenada(int x, int y) {

        if (!coordenadaValida(x, y)) {
            return false; // inválido
        }

        char alvo = oculto[x][y];

        if (alvo == 'V') { //acertou a agua
            return false;
        } else if (alvo == 'X'){ //acertou um barco ja destruido
            return false;
        }
        else{ // acertou um barco nao destruido
            return true;
        }
    }

    public void printarMapaVisivel() {

        System.out.println("\n===== MAPA VISÍVEL =====");

        for (int i = 0; i < TAMANHO_MAPA; i++) {
            for (int j = 0; j < TAMANHO_MAPA; j++) {
                System.out.print(visivel[i][j] + " ");
            }

            System.out.println();
        }
    }

    public void printarMapaOculto() {

        System.out.println("\n===== MAPA OCULTO =====");

        System.out.print("    ");
        for (int z = 0; z < TAMANHO_MAPA; z++) {
            if (z < 9) {
                System.out.print("0");
            }
            System.out.print((z+1) + "  ");
        }
        System.out.println();
        for (int i = 0; i < TAMANHO_MAPA; i++) {
            if (i < 9) {
                System.out.print("0");
            }
            System.out.print((i+1) + "   ");
            for (int j = 0; j < TAMANHO_MAPA; j++) {
                System.out.print(oculto[i][j] + "   ");
            }

            System.out.println();
        }
    }
}