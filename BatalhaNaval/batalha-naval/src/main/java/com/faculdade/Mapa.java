package com.faculdade;

public class Mapa {

    public static int TAMANHO_MAPA = 30;

    private char[][] visivel;
    private char[][] oculto;

    public Mapa() {
        visivel = new char[TAMANHO_MAPA][TAMANHO_MAPA];
        oculto = new char[TAMANHO_MAPA][TAMANHO_MAPA];
        inicializarMapa();
    }

    public int getTamanhoMapa() { return TAMANHO_MAPA; }
    public char getCoordenadaOculta(int x, int y) { return oculto[x][y]; }

    private void inicializarMapa(){
        for(int i = 0; i < TAMANHO_MAPA; i++) {
            for (int j = 0; j < TAMANHO_MAPA; j++) {
                visivel[i][j] = 'O';
                oculto[i][j] = 'V';
            }
        }
    }

    public boolean verificarCoordenadaParaEmbarcacao(Coordenada coordenada) {
        int x = coordenada.getX();
        int y = coordenada.getY();

        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                if (getCoordenadaOculta((x+i), (y+j)) != 'V') { return false; }
                if (getCoordenadaOculta((x-i), (y-j)) != 'V') { return false; }
                if (getCoordenadaOculta((x+i), (y-j)) != 'V') { return false; }
                if (getCoordenadaOculta((x-i), (y+j)) != 'V') { return false; }
            }
        }
        return true;
    }

    public void printarMapaVisivel(){
        for(int i = 0; i < TAMANHO_MAPA; i++) {
            for (int j = 0; j < TAMANHO_MAPA; j++) {
                System.out.print(visivel[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printarMapaOculto() {
        for (int i = 0; i < TAMANHO_MAPA; i++) {
            for (int j = 0; j < TAMANHO_MAPA; j++) {
                System.out.print(visivel[i][j] + " ");
            }
            System.out.println();
        }
    }

}
