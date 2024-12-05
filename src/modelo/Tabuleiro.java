package modelo;

import java.io.Serializable;
import java.util.ArrayList; // Importação do ArrayList para resolver o erro

public class Tabuleiro implements Serializable {
    private ArrayList<Casa> casas; // Array de casas no tabuleiro
    private ArrayList<CartaSorteAzar> cartas; // Baralho de cartas de sorte/azar

    public Tabuleiro() {
        this.casas = new ArrayList<Casa>(40); // Inicializa o array de casas com 40 posições
        this.cartas = new ArrayList<>(); // Inicializa o baralho de cartas
    }

    public Tabuleiro(ArrayList<Casa> casas) {
        this.casas = casas;
        this.cartas = new ArrayList<>();
    }

    public ArrayList<Casa> getCasas() {
        return casas;
    }

    public void setCasas(ArrayList<Casa> casas) {
        this.casas = casas;
    }

    public ArrayList<CartaSorteAzar> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<CartaSorteAzar> cartas) {
        this.cartas = cartas;
    }
}
