package modelo;

import java.awt.Color;
import java.util.ArrayList;

public class Jogador {
    private String nome;
    private String cor;
    private int casaAtual;
    private int saldo;
    private boolean jogando;

    public Jogador() {

    }

    public Jogador(String nome, String cor) {
        this.nome = nome;
        this.casaAtual = 0; // Começa na primeira casa
        this.saldo = 1500; // Saldo inicial
        this.jogando = true; // Começa jogando
        this.cor = cor; // Define a cor do jogador
    }

    public String getNome() {
        return nome;
    }

    public int getCasaAtual() {
        return casaAtual;
    }

    public void setCasaAtual(int casaAtual) {
        this.casaAtual = casaAtual;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public boolean isJogando() {
        return jogando;
    }

    public void setJogando(boolean jogando) {
        this.jogando = jogando;
    }

    public String getCor() {
        return cor;
    }

    public String listarPropriedades(ArrayList<Casa> casas) {
        StringBuilder propriedades = new StringBuilder();
        for (Casa casa : casas) {
            if (this.equals(casa.getDono())) {
                propriedades.append(casa.getNome())
                            .append(" (Nível ")
                            .append(casa.getNivel())
                            .append(")\n");
            }
        }
        return propriedades.length() > 0 ? propriedades.toString() : "N/A";
    }
}    
