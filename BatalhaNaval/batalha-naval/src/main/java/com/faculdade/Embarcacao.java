package com.faculdade;

import java.util.List;

public class Embarcacao {
    protected List<Coordenada> coordenadas;
    protected char simbolo;
    protected int tamanho;

    public int getTamanho() {
        return tamanho;
    }
    public void setCoordenadas(List<Coordenada> coordenadas) {
        this.coordenadas = coordenadas;
    }
}
