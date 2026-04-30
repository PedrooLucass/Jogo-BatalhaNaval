package com.faculdade;

public class Mapa {
    char[][] visivel;
    char[][] oculto;

    public Mapa() {
        visivel = new char[30][30];
        oculto = new char[30][30];
        inicializarMapa();
    }

    private void inicializarMapa(){
        for(int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                visivel[i][j] = 'O';
                oculto[i][j] = 'V';
            }
        }
    }

    public void printarMapaVisivel(){
        for(int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                System.out.print(visivel[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printarMapaOculto() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                System.out.print(visivel[i][j] + " ");
            }
            System.out.println();
        }
    }

}
