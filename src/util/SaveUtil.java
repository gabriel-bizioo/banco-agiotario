package util;

import com.fasterxml.jackson.databind.ObjectMapper;

import modelo.Tabuleiro;
import modelo.Jogador;

import controle.JogoController;
import controle.TabuleiroController;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

public class SaveUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private int iJogadorAtual;
    private ArrayList<Jogador> jogadores;

    public void setIJogadorAtual(int iJogadorAtual) {
        this.iJogadorAtual = iJogadorAtual;
    }

    public int getIJogadorAtual() {
        return this.iJogadorAtual;
    }

    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public ArrayList<Jogador> getJogadores() {
        return this.jogadores;
    }

    public static void salvarEstado(JogoController jogo, File arquivo) throws IOException {
        SaveUtil saveObj = new SaveUtil();

        saveObj.setIJogadorAtual(jogo.getIJogadorAtual());
        saveObj.setJogadores(jogo.getTabuleiroController().getJogadores());
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, saveObj);

        System.out.println("jogo salvo com sucesso em " + arquivo.getPath());
    }

    public static JogoController carregarEstado(File arquivo) throws IOException {
        SaveUtil saveObj = new SaveUtil();
        Tabuleiro tabuleiro = new Tabuleiro();
        TabuleiroController tabuleiroController; 

        saveObj = objectMapper.readValue(arquivo, SaveUtil.class);
        tabuleiroController = new TabuleiroController(tabuleiro, saveObj.getJogadores());

        JogoController jogo = new JogoController(tabuleiroController);
        jogo.setIJogadorAtual(saveObj.getIJogadorAtual());
        return jogo;
    }
}
