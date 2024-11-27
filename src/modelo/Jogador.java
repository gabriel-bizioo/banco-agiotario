package modelo;

import java.awt.Color;
import java.util.ArrayList;

public class Jogador {
    private String nome;
    private int casaAtual;
    private int saldo;
    private boolean jogando;
    private Color cor; // Nova propriedade

    public Jogador(String nome, Color cor) {
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

    public Color getCor() {
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
