package util;

import java.awt.Color;

public class CorUtil {

    // Retorna a cor associada ao nome do jogador
    public static Color getCorDoJogador(String corJogador) {
        switch (corJogador.toLowerCase()) {
            case "vermelho":
                return Color.RED;
            case "verde":
                return Color.GREEN;
            case "azul":
                return Color.BLUE;
            case "amarelo":
                return Color.YELLOW;
            default:
                return Color.GRAY; // Cor padrão para nomes desconhecidos
        }
    }
}
