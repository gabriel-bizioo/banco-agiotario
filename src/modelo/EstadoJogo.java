package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class EstadoJogo implements Serializable {
    private ArrayList<Jogador> jogadores;
    private ArrayList<Casa> casas;

    // Construtor
    public EstadoJogo(ArrayList<Jogador> jogadores, ArrayList<Casa> casas) {
        this.jogadores = jogadores;
        this.casas = casas;
    }

    // Getters e Setters
    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public ArrayList<Casa> getCasas() {
        return casas;
    }

    public void setCasas(ArrayList<Casa> casas) {
        this.casas = casas;
    }
}
