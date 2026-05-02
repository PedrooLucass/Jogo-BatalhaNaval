package com.faculdade;

import java.util.List;

public class Embarcacao {
    protected String nome;
    protected List<Coordenada> coordenadas;
    protected char simbolo;
    protected int tamanho;

    public int getTamanho() { return tamanho; }
    public String getNome() { return nome; }

    public void setCoordenadas(List<Coordenada> coordenadas) { this.coordenadas = coordenadas; }
    public void addCoordenada(Coordenada coordenada) { this.coordenadas.add(coordenada); }
}
