package util;

import com.fasterxml.jackson.databind.ObjectMapper;

import modelo.Tabuleiro;
import modelo.Jogador;
import modelo.Casa;

import controle.JogoController;
import controle.TabuleiroController;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

public class SaveUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private int iJogadorAtual;
    private ArrayList<Jogador> jogadores;
    private ArrayList<Casa> casas;

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

    public ArrayList<Casa> getCasas() {
        return this.casas;
    }

    public void setCasas(ArrayList<Casa> casas) {
        this.casas = casas;
    }

    public static void salvarEstado(JogoController jogo, File arquivo) throws IOException {
        SaveUtil saveObj = new SaveUtil();

        saveObj.setIJogadorAtual(jogo.getIJogadorAtual());
        saveObj.setJogadores(jogo.getTabuleiroController().getJogadores());
        saveObj.setCasas(jogo.getTabuleiroController().getCasas());
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, saveObj);

        System.out.println("jogo salvo com sucesso em " + arquivo.getPath());
    }

    public static JogoController carregarEstado(File arquivo) throws IOException {
        Tabuleiro tabuleiro;
        TabuleiroController tabuleiroController;
        JogoController jogo;
        SaveUtil loadObj = new SaveUtil();

        loadObj = objectMapper.readValue(arquivo, SaveUtil.class);

        for(Casa propriedade : loadObj.getCasas()) {
            if(propriedade.getCorDono() != null) {
                for(Jogador jogador : loadObj.getJogadores()) {
                    if(propriedade.getCorDono().equals(jogador.getCor())) {
                        System.out.println(jogador.getCor());
                        System.out.println(propriedade.getNome());
                        propriedade.setDono(jogador);
                    }
                }
            }
       }

        tabuleiro = new Tabuleiro(loadObj.getCasas());
        tabuleiroController = new TabuleiroController(tabuleiro, loadObj.getJogadores());
        jogo = new JogoController(tabuleiroController);
        jogo.setIJogadorAtual(loadObj.getIJogadorAtual());
        return jogo;
    }
}
