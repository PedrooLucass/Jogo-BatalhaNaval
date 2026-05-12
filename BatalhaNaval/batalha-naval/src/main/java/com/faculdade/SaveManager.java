package com.faculdade;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SaveManager {
    private static String SAVE_PATH = ".\\save\\save-batalha-naval.json";

    @JsonIgnore
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Embarcacao> embarcacoesList;
    public List<Jogador> jogadoresList;
    public Mapa mapa;

    public SaveManager() {
    }
    public void salvar(List<Embarcacao> embarcacoesList, List<Jogador> jogadoresList, Mapa mapa) throws IOException {
        File saveFile = new File(SAVE_PATH);

        // Garante que o diretório existe
        saveFile.getParentFile().mkdirs();

        SaveManager readerSaveManager;

        // Só tenta ler se o arquivo existir e não estiver vazio
        if (saveFile.exists() && saveFile.length() > 0) {
            readerSaveManager = objectMapper.readValue(saveFile, SaveManager.class);
        } else {
            readerSaveManager = new SaveManager();
        }

        readerSaveManager.jogadoresList = jogadoresList;
        readerSaveManager.embarcacoesList = embarcacoesList;
        readerSaveManager.mapa = mapa;

        BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
        writer.write(objectMapper.writeValueAsString(readerSaveManager));
        writer.close();
    }
    public SaveManager carregar() throws IOException {
        File saveFile = new File(SAVE_PATH);

        // Garante que o diretório existe
        saveFile.getParentFile().mkdirs();

        SaveManager readerSaveManager;

        // Só tenta ler se o arquivo existir e não estiver vazio
        if (saveFile.exists() && saveFile.length() > 0) {
            readerSaveManager = objectMapper.readValue(saveFile, SaveManager.class);
        } else {
            readerSaveManager = new SaveManager();
        }
        return readerSaveManager;
    }
    public void clear() throws IOException {
        embarcacoesList.clear();
        jogadoresList.clear();
        mapa = null;

        BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_PATH));
        writer.write("");
        writer.close();
    }
}
