package com.faculdade;

import java.util.List;
import java.util.ArrayList;

public class Embarcacao {
    protected String nome;
    protected List<Coordenada> coordenadas = new ArrayList<>();
    protected char simbolo;
    protected int tamanho;
    private boolean destruida;

    public int getTamanho() { return tamanho; }

    public String getNome() { return nome; }

    public List<Coordenada> getCoordenadas() { return coordenadas; }

    public char getSimbolo() { return simbolo; }

    public boolean isDestruida() { return destruida; }

    public void setDestruida(boolean destruida) { this.destruida = destruida; }

    public void setCoordenadas(List<Coordenada> coordenadas) { this.coordenadas = coordenadas; }

    public void addCoordenada(Coordenada coordenada) { this.coordenadas.add(coordenada); }
}
