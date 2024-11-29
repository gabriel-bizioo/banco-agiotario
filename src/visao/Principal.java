package visao;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.ArrayList;

import controle.JogoController;
import controle.SomController;
import controle.TabuleiroController;
import modelo.Casa;
import modelo.Jogador;
import modelo.Tabuleiro;

public class Principal {

    private static JFrame criarFramePrincipal() {
        JFrame frame = new JFrame("Banco Agiotário");
        frame.setSize(1600, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    public static void main(String[] args) {
        JFrame frame = criarFramePrincipal();
        PainelMenu display = new PainelMenu(frame);

        frame.add(display);
        display.exibirMenuPrincipal();
        frame.setVisible(true);
    }
}
