package modelo;

import java.io.Serializable;
import java.util.ArrayList; // Importação do ArrayList para resolver o erro

public class Tabuleiro implements Serializable {
    private Casa[] casas; // Array de casas no tabuleiro
    private ArrayList<CartaSorteAzar> cartas; // Baralho de cartas de sorte/azar

    public Tabuleiro() {
        this.casas = new Casa[40]; // Inicializa o array de casas com 40 posições
        this.cartas = new ArrayList<>(); // Inicializa o baralho de cartas
    }

    public Casa[] getCasas() {
        return casas;
    }

    public void setCasas(Casa[] casas) {
        this.casas = casas;
    }

    public ArrayList<CartaSorteAzar> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<CartaSorteAzar> cartas) {
        this.cartas = cartas;
    }
}
